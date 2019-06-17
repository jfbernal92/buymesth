package com.buymesth.app.repositories;

import com.buymesth.app.models.OperationType;
import com.buymesth.app.projections.OperationTypeProjection;
import com.buymesth.app.utils.enums.OperationTypeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {

    Optional<OperationType> findByName(OperationTypeName name);

    @Query(value = "SELECT o.id_op_type as \"idOpType\", o.name from operation_type o where o.name=:name", nativeQuery = true)
    Optional<OperationTypeProjection> findByStringName(@Param(value = "name") String name);

}
