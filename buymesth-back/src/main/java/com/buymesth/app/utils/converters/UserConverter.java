package com.buymesth.app.utils.converters;

import com.buymesth.app.dtos.UserDto;
import com.buymesth.app.models.User;
import org.mapstruct.Mapper;

@Mapper(uses = {UserDetailConverter.class})
public interface UserConverter extends BasicConverter<User, UserDto> {
}
