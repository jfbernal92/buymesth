package com.buymesth.app.utils.converters;

import com.buymesth.app.dtos.RoleDto;
import com.buymesth.app.dtos.UserDto;
import com.buymesth.app.models.Role;
import com.buymesth.app.models.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-16T17:33:09+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
@Component
public class UserConverterImpl implements UserConverter {

    @Autowired
    private UserDetailConverter userDetailConverter;

    @Override
    public User fromDtoToEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setEmail( dto.getEmail() );
        user.setSignupDate( dto.getSignupDate() );
        user.setRoles( roleDtoSetToRoleSet( dto.getRoles() ) );
        user.setEnabled( dto.isEnabled() );
        user.setLocked( dto.isLocked() );
        user.setUserDetail( userDetailConverter.fromDtoToEntity( dto.getUserDetail() ) );

        return user;
    }

    @Override
    public List<User> fromDtosToEntities(Collection<UserDto> dtos) {
        if ( dtos == null ) {
            return new ArrayList<User>();
        }

        List<User> list = new ArrayList<User>( dtos.size() );
        for ( UserDto userDto : dtos ) {
            list.add( fromDtoToEntity( userDto ) );
        }

        return list;
    }

    @Override
    public UserDto fromEntityToDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( entity.getId() );
        userDto.setEmail( entity.getEmail() );
        userDto.setSignupDate( entity.getSignupDate() );
        userDto.setRoles( roleSetToRoleDtoSet( entity.getRoles() ) );
        userDto.setEnabled( entity.isEnabled() );
        userDto.setLocked( entity.isLocked() );
        userDto.setUserDetail( userDetailConverter.fromEntityToDto( entity.getUserDetail() ) );

        return userDto;
    }

    @Override
    public List<UserDto> fromEntitiesToDtos(Collection<User> entities) {
        if ( entities == null ) {
            return new ArrayList<UserDto>();
        }

        List<UserDto> list = new ArrayList<UserDto>( entities.size() );
        for ( User user : entities ) {
            list.add( fromEntityToDto( user ) );
        }

        return list;
    }

    protected Role roleDtoToRole(RoleDto roleDto) {
        if ( roleDto == null ) {
            return null;
        }

        Role role = new Role();

        role.setIdRole( roleDto.getIdRole() );
        role.setName( roleDto.getName() );

        return role;
    }

    protected Set<Role> roleDtoSetToRoleSet(Set<RoleDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new HashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleDto roleDto : set ) {
            set1.add( roleDtoToRole( roleDto ) );
        }

        return set1;
    }

    protected RoleDto roleToRoleDto(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setIdRole( role.getIdRole() );
        roleDto.setName( role.getName() );

        return roleDto;
    }

    protected Set<RoleDto> roleSetToRoleDtoSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleDto> set1 = new HashSet<RoleDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleDto( role ) );
        }

        return set1;
    }
}
