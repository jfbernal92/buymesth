package com.buymesth.app.security.jwt.message;

import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
public class ResponseMessage {
    private String message;
}
