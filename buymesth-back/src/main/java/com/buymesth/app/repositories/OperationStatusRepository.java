package com.buymesth.app.repositories;

import com.buymesth.app.models.OperationStatus;
import com.buymesth.app.projections.OperationStatusProjection;
import com.buymesth.app.utils.enums.OperationStatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OperationStatusRepository extends JpaRepository<OperationStatus, Long> {

    Optional<OperationStatus> findByName(OperationStatusName name);

    @Query(value = "SELECT o.id_op_status as \"idOpStatus\", o.name from operation_status o where o.name=:name", nativeQuery = true)
    Optional<OperationStatusProjection> findByStringName(@Param(value = "name") String name);

}
