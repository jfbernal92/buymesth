package com.buymesth.app.models;

import com.buymesth.app.utils.enums.OperationStatusName;
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
@Entity(name = "operation_status")
@Table(name="operation_status")
public class OperationStatus {
    @Id
    private Long idOpStatus;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @NotNull
    private OperationStatusName name;

    @OneToMany(mappedBy = "status",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Operation> operations = new ArrayList<>();
}
