package com.buymesth.app.services;

import com.buymesth.app.dtos.BuyForm;
import com.buymesth.app.dtos.OperationDto;
import com.buymesth.app.dtos.OperationStatusForm;
import com.buymesth.app.dtos.OperationTypeCount;
import com.buymesth.app.models.*;
import com.buymesth.app.projections.OperationStatusProjection;
import com.buymesth.app.repositories.*;
import com.buymesth.app.utils.converters.OperationConverter;
import com.buymesth.app.utils.enums.OperationStatusName;
import com.buymesth.app.utils.enums.OperationTypeName;
import com.buymesth.app.utils.exceptions.AssertUtil;
import com.buymesth.app.utils.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.buymesth.app.utils.enums.OperationStatusName.*;
import static com.buymesth.app.utils.enums.OperationTypeName.BUY;
import static com.buymesth.app.utils.enums.OperationTypeName.DEPOSIT;

@Service
public class OperationService {

    private UserRepository userRepository;
    private OperationConverter operationConverter;
    private OperationTypeRepository operationTypeRepository;
    private OperationStatusRepository operationStatusRepository;
    private OperationRepository operationRepository;
    private ProductRepository productRepository;

    @Autowired
    public OperationService(@Lazy UserRepository userRepository, @Lazy ProductRepository productRepository,
                            @Lazy OperationConverter operationConverter, @Lazy OperationTypeRepository operationTypeRepository,
                            @Lazy OperationStatusRepository operationStatusRepository, @Lazy OperationRepository operationRepository) {

        this.userRepository = userRepository;
        this.operationConverter = operationConverter;
        this.operationStatusRepository = operationStatusRepository;
        this.operationTypeRepository = operationTypeRepository;
        this.operationRepository = operationRepository;
        this.productRepository = productRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public Optional<OperationDto> deposit(Long id, Double amount) throws CustomException {
        Operation operation = Operation.builder()
                .amount(amount)
                .type(getType(OperationTypeName.DEPOSIT))
                .status(getStatus(PENDING))
                .visible(true)
                .build();
        return userRepository.findById(id).map(user -> {
            user.addOperation(operation);
            return userRepository.save(user);
        }).map(user -> operationConverter.fromEntityToDto(user.getLatestOperation()));
    }

    public Optional<OperationDto> getOperation(Long idOperation) {
        return operationRepository.findById(idOperation).map(operationConverter::fromEntityToDto);
    }

    public Page<OperationDto> getOperations(Pageable page, String type, String status) throws CustomException {
        Page<Operation> operations = operationRepository.findAllByTypeAndStatus(OperationTypeName.enumValueOf(type), OperationStatusName.enumValueOf(status), page);
        return new PageImpl<>(operations.getContent().stream().map(operationConverter::fromEntityToDto).collect(Collectors.toList()), page, operations.getTotalElements());
    }

    @Transactional(rollbackOn = CustomException.class)
    public Optional<OperationDto> editOperationStatus(OperationStatusForm operationStatusForm) throws CustomException {

        Optional<User> optUser = userRepository.findByIdWithOperations(operationStatusForm.getIdUser());
        AssertUtil.isTrue(optUser.isPresent(), () -> new CustomException("User not found or has no associated operations", HttpStatus.NOT_FOUND));

        User user = optUser.get();


        AssertUtil.checkNotNull(operationStatusForm.getStatus(), () -> new CustomException("Status can not be null", HttpStatus.BAD_REQUEST));
        Optional<OperationStatusProjection> optStatus = operationStatusRepository.findByStringName(operationStatusForm.getStatus());
        AssertUtil.isTrue(optStatus.isPresent(), () -> new CustomException("Status not found", HttpStatus.NOT_FOUND));

        List<Operation> list = user.getOperations().stream().filter(operation1 -> operation1.getIdOperation().equals(operationStatusForm.getIdOperation())).collect(Collectors.toList());
        AssertUtil.isTrue(list.size() == 1 && list.get(0).getIdOperation().equals(operationStatusForm.getIdOperation()), () -> new CustomException("Invalid data, the iduser hasn't got this operation associated", HttpStatus.UNPROCESSABLE_ENTITY));

        Operation operation = list.get(0);
        user.removeOperation(operation);

        if (operation.getStatus().getName().equals(COMPLETED)) {
            throw new CustomException("This operation has been already completed", HttpStatus.BAD_REQUEST);
        }

        operation.setStatus(OperationStatus.builder().idOpStatus(optStatus.get().getIdOpStatus()).name(OperationStatusName.valueOf(optStatus.get().getName())).build());

        if ((operation.getType().getName() == DEPOSIT) && optStatus.get().getName().equalsIgnoreCase(TRANSIT.toString())) {
            throw new CustomException("A deposit cant have 'TRANSIT' status", HttpStatus.BAD_REQUEST);
        }

        if ((operation.getType().getName() == BUY) && optStatus.get().getName().equalsIgnoreCase(COMPLETED.toString())) {
            operation.setVisible(true);
        }
        if ((operation.getType().getName() == DEPOSIT) && optStatus.get().getName().equalsIgnoreCase(COMPLETED.toString())) {
            user.getUserDetail().setBank(user.getUserDetail().getBank() + operation.getAmount());
        }

        user.addOperation(operation);
        return operationConverter.fromEntityToOptionalDto(userRepository.save(user).getLatestOperation());

    }


    @Transactional
    public Optional<OperationDto> buy(BuyForm buyForm) throws CustomException {
        Optional<Product> optProduct = productRepository.findById(buyForm.getProductId());

        if (!optProduct.isPresent())
            return Optional.empty();

        Operation operation = Operation.builder()
                .amount(optProduct.get().getPrice())
                .type(getType(BUY))
                .status(getStatus(PENDING))
                .product(optProduct.get())
                .visible(buyForm.isVisible())
                .build();

        return userRepository.findById(buyForm.getUserId()).map(u -> {
            u.addOperation(operation);
            u.getUserDetail().setBank(u.getUserDetail().getBank() - operation.getAmount());
            return userRepository.save(u);
        }).map(user -> operationConverter.fromEntityToDto(user.getLatestOperation()));
    }


    public Page<OperationDto> getUserOperation(Long id, String type, String status, Pageable page) throws CustomException {
        Page<Operation> result = operationRepository.findAllUserOperationsByTypeAndStatus(id, OperationTypeName.enumValueOf(type), OperationStatusName.enumValueOf(status), page);
        return new PageImpl<>(result.getContent().stream().map(operationConverter::fromEntityToDto).collect(Collectors.toList()), page, result.getTotalElements());
    }


    public List<OperationTypeCount> countOperationsByType() {
        List<Object[]> list = operationRepository.countOperationsByType();
        List<OperationTypeCount> list1 = new ArrayList<>();
        list.forEach(o -> list1.add(new OperationTypeCount(o[0].toString().trim(), BUY, Integer.parseInt(o[1].toString()))));
        list.forEach(o -> list1.add(new OperationTypeCount(o[0].toString().trim(), DEPOSIT, Integer.parseInt(o[2].toString()))));
        return list1;
    }

    private OperationStatus getStatus(OperationStatusName name) throws CustomException {
        return operationStatusRepository.findByName(name).orElseThrow(() -> new CustomException("Pending status does not exist in database", HttpStatus.INTERNAL_SERVER_ERROR));

    }

    private OperationType getType(OperationTypeName name) throws CustomException {
        return operationTypeRepository.findByName(name).orElseThrow(() -> new CustomException("Deposit type does not exist in database", HttpStatus.INTERNAL_SERVER_ERROR));

    }

}
