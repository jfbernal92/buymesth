package com.buymesth.app.utils.converters;

import com.buymesth.app.dtos.OperationDto;
import com.buymesth.app.models.Operation;
import com.buymesth.app.models.OperationStatus;
import com.buymesth.app.models.OperationType;
import com.buymesth.app.models.User;
import com.buymesth.app.utils.enums.OperationStatusName;
import com.buymesth.app.utils.enums.OperationTypeName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-16T17:33:09+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
@Component
public class OperationConverterImpl implements OperationConverter {

    @Autowired
    private ProductConverter productConverter;

    @Override
    public Operation fromDtoToEntity(OperationDto dto) {
        if ( dto == null ) {
            return null;
        }

        Operation operation = new Operation();

        operation.setType( operationDtoToOperationType( dto ) );
        operation.setStatus( operationDtoToOperationStatus( dto ) );
        operation.setIdOperation( dto.getIdOperation() );
        operation.setDate( dto.getDate() );
        operation.setAmount( dto.getAmount() );
        operation.setProduct( productConverter.fromDtoToEntity( dto.getProduct() ) );
        operation.setVisible( dto.isVisible() );

        return operation;
    }

    @Override
    public List<Operation> fromDtosToEntities(Collection<OperationDto> operationDtos) {
        if ( operationDtos == null ) {
            return new ArrayList<Operation>();
        }

        List<Operation> list = new ArrayList<Operation>( operationDtos.size() );
        for ( OperationDto operationDto : operationDtos ) {
            list.add( fromDtoToEntity( operationDto ) );
        }

        return list;
    }

    @Override
    public OperationDto fromEntityToDto(Operation operation) {
        if ( operation == null ) {
            return null;
        }

        OperationDto operationDto = new OperationDto();

        Long id = operationUserId( operation );
        if ( id != null ) {
            operationDto.setIdUser( id );
        }
        OperationTypeName name = operationTypeName( operation );
        if ( name != null ) {
            operationDto.setType( name.name() );
        }
        OperationStatusName name1 = operationStatusName( operation );
        if ( name1 != null ) {
            operationDto.setStatus( name1.name() );
        }
        operationDto.setIdOperation( operation.getIdOperation() );
        operationDto.setDate( operation.getDate() );
        operationDto.setAmount( operation.getAmount() );
        operationDto.setProduct( productConverter.fromEntityToDto( operation.getProduct() ) );
        operationDto.setVisible( operation.isVisible() );

        return operationDto;
    }

    @Override
    public List<OperationDto> fromEntitiesToDtos(Collection<Operation> operations) {
        if ( operations == null ) {
            return new ArrayList<OperationDto>();
        }

        List<OperationDto> list = new ArrayList<OperationDto>( operations.size() );
        for ( Operation operation : operations ) {
            list.add( fromEntityToDto( operation ) );
        }

        return list;
    }

    protected OperationType operationDtoToOperationType(OperationDto operationDto) {
        if ( operationDto == null ) {
            return null;
        }

        OperationType operationType = new OperationType();

        if ( operationDto.getType() != null ) {
            operationType.setName( Enum.valueOf( OperationTypeName.class, operationDto.getType() ) );
        }

        return operationType;
    }

    protected OperationStatus operationDtoToOperationStatus(OperationDto operationDto) {
        if ( operationDto == null ) {
            return null;
        }

        OperationStatus operationStatus = new OperationStatus();

        if ( operationDto.getStatus() != null ) {
            operationStatus.setName( Enum.valueOf( OperationStatusName.class, operationDto.getStatus() ) );
        }

        return operationStatus;
    }

    private Long operationUserId(Operation operation) {
        if ( operation == null ) {
            return null;
        }
        User user = operation.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private OperationTypeName operationTypeName(Operation operation) {
        if ( operation == null ) {
            return null;
        }
        OperationType type = operation.getType();
        if ( type == null ) {
            return null;
        }
        OperationTypeName name = type.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private OperationStatusName operationStatusName(Operation operation) {
        if ( operation == null ) {
            return null;
        }
        OperationStatus status = operation.getStatus();
        if ( status == null ) {
            return null;
        }
        OperationStatusName name = status.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
