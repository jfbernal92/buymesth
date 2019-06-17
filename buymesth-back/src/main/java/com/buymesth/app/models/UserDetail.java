package com.buymesth.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_detail")
@Table(name="user_detail")
public class UserDetail {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "firstSurname")
    private String firstSurname;

    @Column(name = "secondSurname")
    private String secondSurname;

    @Column(name = "country")
    @NotNull
    private String country;

    @CreationTimestamp
    @Column(name = "activateDate", updatable = false)
    private Date activateDate;

    @Column(name = "state")
    private String state;

    @Column(name = "region")
    @NotNull
    private String region;

    @Column(name = "province")
    private String province;

    @Column(name = "postalCode")
    @NotNull
    private Integer postalCode;

    @Column(name = "street")
    @NotNull
    private String street;

    @Column(name = "number")
    private int number;

    @Column(name = "door")
    @NotNull
    private String door;

    @Column(name = "lastLogin")
    private Date lastLogin;

    @Column(name = "bank")
    @Min(0)
    private Double bank;

    @OneToOne
    @MapsId
    private User user;
}
