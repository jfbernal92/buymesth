package com.buymesth.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "user_account")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @NotBlank
    @NotNull
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "password")
    @NotNull
    private String password;

    @CreationTimestamp
    @Column(name = "signupDate", updatable = false)
    private Date signupDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @NotNull
    private Set<Role> roles;

    @NotNull
    @Column(name = "enabled")
    private boolean enabled;

    @NotNull
    @Column(name = "locked")
    private boolean locked;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private UserDetail userDetail;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Operation> operations = new ArrayList<>();

    public void addOperation(Operation operation) {
        this.operations.add(operation);
        operation.setUser(this);
    }

    public void removeOperation(Operation operation) {
        this.operations.remove(operation);
        operation.setUser(null);
    }

    public Operation getLatestOperation() {
        Date aux = null;
        Operation op = new Operation();
        for (Operation o : this.operations) {
            if (aux == null || o.getDate().getTime() > aux.getTime()) {
                aux = o.getDate();
                op = o;
            }
        }
        return op;
    }

    public User(Long id, String email, String password, Date signupDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.signupDate = signupDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (null == this.getRoles())
            return new HashSet<>();
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName().toString()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean checkUser() {
        return this.isEnabled() && this.isLocked();
    }
}
