package com.buymesth.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "operation")
@Table(name="operation")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperation;

    @CreationTimestamp
    @Column(name = "date", updatable = false)
    private Date date;

    @Column(name = "amount")
    @NotNull
    @Digits(integer = 3, fraction = 2)
    private double amount;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "op_type_id")
    private OperationType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "op_status_id")
    @NotNull
    private OperationStatus status;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "visible")
    @NotNull
    private boolean visible;
}
