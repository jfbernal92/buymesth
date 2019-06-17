package com.buymesth.app.utils.enums;

import com.buymesth.app.utils.exceptions.CustomException;
import org.springframework.http.HttpStatus;

public enum OperationTypeName {

    DEPOSIT,
    BUY;

    public static OperationTypeName enumValueOf(String v) throws CustomException {
        if (v == null || v.trim().isEmpty())
            return null;

        for (OperationTypeName n : OperationTypeName.values()) {
            if (v.trim().equalsIgnoreCase(n.name())) {
                return n;
            }
        }
        throw new CustomException("This operation type does not exist", HttpStatus.BAD_REQUEST);
    }
}
