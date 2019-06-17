package com.buymesth.app.utils.converters;

import com.buymesth.app.dtos.RoleDto;
import com.buymesth.app.models.Role;
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
public class RoleConverterImpl implements RoleConverter {

    @Override
    public Role fromDtoToEntity(RoleDto dto) {
        if ( dto == null ) {
            return null;
        }

        Role role = new Role();

        role.setIdRole( dto.getIdRole() );
        role.setName( dto.getName() );

        return role;
    }

    @Override
    public List<Role> fromDtosToEntities(Collection<RoleDto> dtos) {
        if ( dtos == null ) {
            return new ArrayList<Role>();
        }

        List<Role> list = new ArrayList<Role>( dtos.size() );
        for ( RoleDto roleDto : dtos ) {
            list.add( fromDtoToEntity( roleDto ) );
        }

        return list;
    }

    @Override
    public RoleDto fromEntityToDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setIdRole( entity.getIdRole() );
        roleDto.setName( entity.getName() );

        return roleDto;
    }

    @Override
    public List<RoleDto> fromEntitiesToDtos(Collection<Role> entities) {
        if ( entities == null ) {
            return new ArrayList<RoleDto>();
        }

        List<RoleDto> list = new ArrayList<RoleDto>( entities.size() );
        for ( Role role : entities ) {
            list.add( fromEntityToDto( role ) );
        }

        return list;
    }
}
