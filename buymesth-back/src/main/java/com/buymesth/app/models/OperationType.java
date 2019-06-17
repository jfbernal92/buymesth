package com.buymesth.app.models;

import com.buymesth.app.utils.enums.OperationTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "operation_type")
@Table(name="operation_type")
public class OperationType {

    @Id
    private Long idOpType;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @NotNull
    private OperationTypeName name;

    @OneToMany(mappedBy = "type",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Operation> operations = new ArrayList<>();
}
