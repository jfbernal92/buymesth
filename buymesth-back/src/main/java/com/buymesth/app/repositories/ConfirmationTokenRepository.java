package com.buymesth.app.repositories;

import com.buymesth.app.models.ConfirmationToken;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Query(value = "SELECT new ConfirmationToken (ct.token, ct.createdDate) FROM ConfirmationToken ct where ct.token=:token and ct.createdDate =:d and ct.user.id=:id")
    Optional<ConfirmationToken> findByTokenAndCreatedDateAndId(@Param(value = "token") String token, @Param(value = "d") Date d, @Param(value = "id") Long id);

    @Query("SELECT CASE WHEN COUNT(ct) > 0 THEN true ELSE false END FROM ConfirmationToken ct WHERE ct.token = :token and ct.id =:id")
    Boolean existsByIdAndToken(@Param("token") String token, @Param("id") Long id);
}
