package com.buymesth.app.dtos;

import com.buymesth.app.utils.enums.OperationTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationTypeCount {

    private String month;
    private OperationTypeName type;
    private Integer total;
}
