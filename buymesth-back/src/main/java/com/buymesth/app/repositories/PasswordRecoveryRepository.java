package com.buymesth.app.repositories;

import com.buymesth.app.models.PasswordRecovery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, Long> {

    boolean existsPasswordRecoveryByTokenAndId(String token, Long id);

    boolean existsById(Long id);

    Optional<PasswordRecovery> findByTokenAndId(String token, Long id);
}
