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
@Entity(name = "PasswordRecovery")
@Table(name = "password_recovery")
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecovery {

    @Id
    private Long id;

    @Column(name = "token")
    @NotNull
    public String token;

    @Column(name = "created_date", updatable = false)
    @NotNull
    @CreationTimestamp
    private Date createdDate;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public PasswordRecovery(String token, Date createdDate) {
        this.token = token;
        this.createdDate = createdDate;
    }

    public PasswordRecovery(User user) {

        this.user = user;
        this.token = UUID.randomUUID().toString();
        this.createdDate = new Date();
    }

    public boolean verify() {
        return (this.createdDate.getTime() + TimeUnit.HOURS.toMillis(1)) > new Date().getTime();
    }

    public long remainingTime() {
        return this.createdDate.getTime() + TimeUnit.HOURS.toMillis(1) - new Date().getTime();
    }
}
