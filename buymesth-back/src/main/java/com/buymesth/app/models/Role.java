package com.buymesth.app.models;

import com.buymesth.app.utils.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "role")
@Table(name = "role")
public class Role {

    @Id
    private Long idRole;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @NotNull
    private RoleName name;

    public String getNameString() {
        return this.name.name();
    }

}
