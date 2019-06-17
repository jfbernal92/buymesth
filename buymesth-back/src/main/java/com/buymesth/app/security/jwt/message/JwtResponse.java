package com.buymesth.app.security.jwt.message;

import com.buymesth.app.models.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private String email;
    private Long id;
    private String type = "Bearer";
    private List<String> roles;

    public JwtResponse(String accessToken, User user) {
        this.token = accessToken;
        this.email = user.getEmail();
        this.id = user.getId();
        this.roles = user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList());

    }

}