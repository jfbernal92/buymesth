package com.buymesth.app.utils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomExceptionMessage {
    private Date date;
    private String message;
    private String uri;
}

