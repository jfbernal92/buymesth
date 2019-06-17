package com.buymesth.app.repositories;

import com.buymesth.app.models.Operation;
import com.buymesth.app.utils.enums.OperationStatusName;
import com.buymesth.app.utils.enums.OperationTypeName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("SELECT o FROM operation o left join o.product WHERE o.user.id = :id AND (:type IS NULL or (o.type.name = :type)) AND (:status IS NULL or (o.status.name = :status))")
    Page<Operation> findAllUserOperationsByTypeAndStatus(Long id, @Param(value = "type") OperationTypeName type, @Param(value = "status") OperationStatusName status, Pageable page);

    @Query("SELECT o FROM operation o left join o.product WHERE (:type IS NULL or (o.type.name = :type)) AND (:status IS NULL or (o.status.name = :status))")
    Page<Operation> findAllByTypeAndStatus(@Param(value = "type") OperationTypeName type, @Param(value = "status") OperationStatusName status, Pageable page);

    @Query(value = "select to_char(date_trunc('month', o.\"date\"), 'Month') as \"Month\", " +
            "SUM(CASE WHEN ot.\"name\" = 'BUY' THEN 1 ELSE 0 END) AS Buys, " +
            "SUM(CASE WHEN ot.\"name\" = 'DEPOSIT' THEN 1 ELSE 0 END) AS Deposits " +
            "from operation o join operation_type ot on o.op_type_id=ot.id_op_type " +
            "where to_char(o.\"date\",'YYYY') > to_char(date('now') - interval '1 year','YYYY') " +
            "group by date_trunc('month', o.\"date\")", nativeQuery = true)
    List<Object[]> countOperationsByType();

}
