package com.buymesth.app.security.jwt;

import com.buymesth.app.models.Role;
import com.buymesth.app.models.User;
import com.buymesth.app.utils.enums.RoleName;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${buymesth.app.jwtSecret}")
    private String jwtSecret;

    @Value("${buymesth.app.jwtExpirationInHours}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {

        User userPrincipal = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setId(userPrincipal.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(jwtExpiration)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public String getUserNameFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthenticated = (User) authentication.getPrincipal();
        return userAuthenticated.getUsername();
    }

    public Long getIdFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthenticated = (User) authentication.getPrincipal();
        return userAuthenticated.getId();
    }

    public List<RoleName> getAuthoritiesFromSecurityContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthenticated = (User) authentication.getPrincipal();
        return userAuthenticated.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }
}