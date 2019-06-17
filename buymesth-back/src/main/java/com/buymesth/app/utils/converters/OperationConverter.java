package com.buymesth.app.utils.converters;

import com.buymesth.app.dtos.OperationDto;
import com.buymesth.app.models.Operation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mapper(uses = {ProductConverter.class})
public interface OperationConverter extends BasicConverter<Operation, OperationDto> {

    @Mapping(source = "status", target = "status.name")
    @Mapping(source = "type", target = "type.name")
    Operation fromDtoToEntity(final OperationDto dto);

    default Optional<Operation> fromDtoToOptionalEntity(OperationDto operationDto) {
        return Optional.ofNullable(this.fromDtoToEntity(operationDto));
    }

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<Operation> fromDtosToEntities(Collection<OperationDto> operationDtos);


    @Mapping(source = "status.name", target = "status")
    @Mapping(source = "type.name", target = "type")
    @Mapping(source = "user.id", target = "idUser")
    OperationDto fromEntityToDto(Operation operation);


    default Optional<OperationDto> fromEntityToOptionalDto(Operation operation) {
        return Optional.ofNullable(this.fromEntityToDto(operation));
    }

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<OperationDto> fromEntitiesToDtos(Collection<Operation> operations);

}
