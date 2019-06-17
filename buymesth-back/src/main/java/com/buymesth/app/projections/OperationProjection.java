package com.buymesth.app.projections;

import java.util.Date;

public interface OperationProjection {

    Long getIdUser();

    Long getIdOperation();
    Date getDate();
    double getAmount();
    String getType();
    String getStatus();

    boolean getVisible();
}
