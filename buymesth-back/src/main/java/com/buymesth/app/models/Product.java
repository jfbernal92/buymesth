package com.buymesth.app.models;

import com.buymesth.app.utils.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity(name = "product")
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @Column(name = "description")
    @NotBlank
    @Size(max = 80)
    private String description;

    @Column(name = "price")
    @NotNull
    @DecimalMin("0.1")
    @DecimalMax("999.99")
    @Digits(integer = 3, fraction = 2)
    private double price;

    @Column(name = "units")
    @NotNull
    @Min(0)
    private Integer units;

    @Enumerated(EnumType.STRING)
    private Category category;

}
