package com.buymesth.app.utils.converters;

import com.buymesth.app.dtos.RoleDto;
import com.buymesth.app.models.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleConverter extends BasicConverter<Role, RoleDto> {
}
