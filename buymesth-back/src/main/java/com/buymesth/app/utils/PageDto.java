package com.buymesth.app.utils;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageDto<T> {

    private Long total;
    private List<T> result;
    private Integer rows;


    public static <T> PageDto<T> of(List<T> list, Long total, Integer numberOfElements) {
        return new PageDto<>(total == null ? 0 : total, list == null ? new ArrayList<>() : list, numberOfElements == null ? 0 : numberOfElements);
    }

    public static <T> PageDto<T> of(Page<T> page) {
        return new PageDto<>(page.getTotalElements(), page.getContent(), page.getNumberOfElements());
    }

    public static <T> PageDto<T> of(List<T> list) {
        return list == null ? new PageDto<>() : new PageDto<>((long) list.size(), list, list.size());
    }
}
