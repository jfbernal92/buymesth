package com.buymesth.app.controllers;

import com.buymesth.app.anotations.ValidateTokenID;
import com.buymesth.app.dtos.BuyForm;
import com.buymesth.app.dtos.OperationDto;
import com.buymesth.app.dtos.OperationStatusForm;
import com.buymesth.app.dtos.OperationTypeCount;
import com.buymesth.app.services.OperationService;
import com.buymesth.app.utils.PageDto;
import com.buymesth.app.utils.exceptions.CustomException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

import static com.buymesth.app.utils.routes.RestRoutes.Operation.*;

@Validated
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping(URL)
public class OperationController {


    private OperationService operationService;
    private ResourceGenerator resGen;

    @Autowired
    public OperationController(@Lazy OperationService operationService, @Lazy ResourceGenerator resGen) {
        this.operationService = operationService;
        this.resGen = resGen;
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property,[asc|desc]. " +
                            "Default sort order is ascending."),
            @ApiImplicitParam(name = "type", dataType = "string", paramType = "query", allowableValues = "BUY, DEPOSIT"),
            @ApiImplicitParam(name = "status", dataType = "string", paramType = "query", allowableValues = "PENDING, COMPLETED, TRANSIT")
    })
    public Resource<PageDto<OperationDto>> getOperations(Pageable page, @RequestParam(required = false) String type, @RequestParam(required = false) String status) throws CustomException {
        Resource<PageDto<OperationDto>> resource = new Resource<>(PageDto.of(operationService.getOperations(page, type, status)));
        Link link = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getOperations(page, type, status)).withRel("all").expand();
        resource.add(link);
        return resource;
    }

    @PostMapping(DEPOSIT)
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public ResponseEntity<OperationDto> deposit(@ValidateTokenID @PathVariable(name = "id") Long id, @RequestBody @Min(10) @DecimalMax("999.99") @Digits(integer = 3, fraction = 2) Double amount) throws CustomException {
        return operationService.deposit(id, amount).map(ResponseEntity::ok).orElse(ResponseEntity.unprocessableEntity().build());
    }

    @GetMapping(OPERATION)
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public Resource getOperation(@ValidateTokenID @PathVariable(name = "id") Long id, @RequestParam(value = "idOperation", required = true) Long idOperation) {
        return operationService.getOperation(idOperation).map(resGen::getOperationResource).orElse(new Resource(ResponseEntity.notFound().build()));
    }

    @PutMapping
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public ResponseEntity<OperationDto> editOperationStatus(@RequestBody OperationStatusForm operationStatusForm) throws CustomException {
        return operationService.editOperationStatus(operationStatusForm).map(ResponseEntity::ok).orElse(ResponseEntity.unprocessableEntity().build());
    }

    @PostMapping(BUY)
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public ResponseEntity<OperationDto> buy(@ValidateTokenID @PathVariable(name = "id") Long id, @Valid @RequestBody BuyForm buyForm) throws CustomException {
        return operationService.buy(buyForm).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping(USER)
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property,[asc|desc]. " +
                            "Default sort order is ascending."),
            @ApiImplicitParam(name = "type", dataType = "string", paramType = "query", allowableValues = "BUY, DEPOSIT"),
            @ApiImplicitParam(name = "status", dataType = "string", paramType = "query", allowableValues = "PENDING, COMPLETED, TRANSIT")
    })
    public Resource<PageDto<Resource<OperationDto>>> getUserOperations(@ValidateTokenID @PathVariable(name = "id") Long id, @RequestParam(required = false) String type, @RequestParam(required = false) String status, Pageable page) throws CustomException {
        Page<OperationDto> operations = operationService.getUserOperation(id, type, status, page);
        List<Resource<OperationDto>> list = operations.stream().map(resGen::getOperationResource).collect(Collectors.toList());
        Resource<PageDto<Resource<OperationDto>>> res = new Resource<>(PageDto.of(list, operations.getTotalElements(), operations.getNumberOfElements()));
        Link link = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getUserOperations(id, type, status, page)).withRel("all").expand();
        res.add(link);
        return res;
    }

    @GetMapping(OPERATION_BY_TYPE)
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<OperationTypeCount>> countOperationsByType() {
        return ResponseEntity.ok(operationService.countOperationsByType());
    }

}
