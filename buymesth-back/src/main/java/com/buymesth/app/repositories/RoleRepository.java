package com.buymesth.app.repositories;

import com.buymesth.app.models.Role;
import com.buymesth.app.utils.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);

    @Query(value = "SELECT r.id_role, r.name FROM user_role ur join role r on ur.role_id=r.id_role WHERE ur.user_id=:id", nativeQuery = true)
    List<Role> findByUserId(Long id);
}
