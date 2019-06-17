package com.buymesth.app.utils.enums;

import com.buymesth.app.utils.exceptions.CustomException;
import org.springframework.http.HttpStatus;

public enum OperationStatusName {
    PENDING,
    COMPLETED,
    TRANSIT;

    public static OperationStatusName enumValueOf(String v) throws CustomException {
        if (v == null || v.trim().isEmpty())
            return null;

        for (OperationStatusName n : OperationStatusName.values()) {
            if (v.trim().equalsIgnoreCase(n.name())) {
                return n;
            }
        }
        throw new CustomException("This operation status does not exist", HttpStatus.BAD_REQUEST);
    }
}
