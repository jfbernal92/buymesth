package com.buymesth.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Data
@Entity(name = "ConfirmationToken")
@Table(name = "confirmation_token")
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    private Long id;

    @Column(name = "token")
    @NotNull
    public String token;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    private Date createdDate;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public ConfirmationToken(String token, Date createdDate) {
        this.token = token;
        this.createdDate = createdDate;
    }

    public ConfirmationToken(User user) {

        this.user = user;
        this.token = UUID.randomUUID().toString();
    }

    public boolean verify(){
        return (this.createdDate.getTime() + TimeUnit.HOURS.toMillis(24)) > new Date().getTime();
    }
}
