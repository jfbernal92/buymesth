package com.buymesth.app.utils.converters;

import com.buymesth.app.dtos.UserDetailDto;
import com.buymesth.app.models.UserDetail;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-16T17:33:09+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
@Component
public class UserDetailConverterImpl implements UserDetailConverter {

    @Override
    public UserDetail fromDtoToEntity(UserDetailDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserDetail userDetail = new UserDetail();

        userDetail.setName( dto.getName() );
        userDetail.setFirstSurname( dto.getFirstSurname() );
        userDetail.setSecondSurname( dto.getSecondSurname() );
        userDetail.setCountry( dto.getCountry() );
        userDetail.setActivateDate( dto.getActivateDate() );
        userDetail.setState( dto.getState() );
        userDetail.setRegion( dto.getRegion() );
        userDetail.setProvince( dto.getProvince() );
        userDetail.setPostalCode( dto.getPostalCode() );
        userDetail.setStreet( dto.getStreet() );
        userDetail.setNumber( dto.getNumber() );
        userDetail.setDoor( dto.getDoor() );
        userDetail.setLastLogin( dto.getLastLogin() );
        userDetail.setBank( dto.getBank() );

        return userDetail;
    }

    @Override
    public List<UserDetail> fromDtosToEntities(Collection<UserDetailDto> dtos) {
        if ( dtos == null ) {
            return new ArrayList<UserDetail>();
        }

        List<UserDetail> list = new ArrayList<UserDetail>( dtos.size() );
        for ( UserDetailDto userDetailDto : dtos ) {
            list.add( fromDtoToEntity( userDetailDto ) );
        }

        return list;
    }

    @Override
    public UserDetailDto fromEntityToDto(UserDetail entity) {
        if ( entity == null ) {
            return null;
        }

        UserDetailDto userDetailDto = new UserDetailDto();

        userDetailDto.setName( entity.getName() );
        userDetailDto.setFirstSurname( entity.getFirstSurname() );
        userDetailDto.setSecondSurname( entity.getSecondSurname() );
        userDetailDto.setCountry( entity.getCountry() );
        userDetailDto.setActivateDate( entity.getActivateDate() );
        userDetailDto.setState( entity.getState() );
        userDetailDto.setRegion( entity.getRegion() );
        userDetailDto.setProvince( entity.getProvince() );
        userDetailDto.setPostalCode( entity.getPostalCode() );
        userDetailDto.setStreet( entity.getStreet() );
        userDetailDto.setNumber( entity.getNumber() );
        userDetailDto.setDoor( entity.getDoor() );
        userDetailDto.setLastLogin( entity.getLastLogin() );
        userDetailDto.setBank( entity.getBank() );

        return userDetailDto;
    }

    @Override
    public List<UserDetailDto> fromEntitiesToDtos(Collection<UserDetail> entities) {
        if ( entities == null ) {
            return new ArrayList<UserDetailDto>();
        }

        List<UserDetailDto> list = new ArrayList<UserDetailDto>( entities.size() );
        for ( UserDetail userDetail : entities ) {
            list.add( fromEntityToDto( userDetail ) );
        }

        return list;
    }
}
