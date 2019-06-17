package com.buymesth.app.utils.converters;

import com.buymesth.app.dtos.UserDetailDto;
import org.mapstruct.Mapper;

import com.buymesth.app.models.UserDetail;

@Mapper
public interface UserDetailConverter extends BasicConverter<UserDetail, UserDetailDto> {
}
