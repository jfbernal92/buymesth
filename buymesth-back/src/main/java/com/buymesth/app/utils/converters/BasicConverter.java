package com.buymesth.app.utils.converters;

import org.mapstruct.IterableMapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BasicConverter<Entity, Dto> {

    Entity fromDtoToEntity(final Dto dto);

    default Optional<Entity> fromDtoToOptionalEntity(Dto dto) {
        return Optional.ofNullable(this.fromDtoToEntity(dto));
    }

    @IterableMapping(nullValueMappingStrategy= NullValueMappingStrategy.RETURN_DEFAULT)
    List<Entity> fromDtosToEntities(Collection<Dto> dtos);

    Dto fromEntityToDto(Entity entity);

    default Optional<Dto> fromEntityToOptionalDto(Entity entity) {
        return Optional.ofNullable(this.fromEntityToDto(entity));
    }

    @IterableMapping(nullValueMappingStrategy=NullValueMappingStrategy.RETURN_DEFAULT)
    List<Dto> fromEntitiesToDtos(Collection<Entity> entities);
}
