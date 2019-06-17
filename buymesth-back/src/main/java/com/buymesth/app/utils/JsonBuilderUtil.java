package com.buymesth.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBuilderUtil {

    public static String toJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return "Could not parse response to JSON format";
        }
    }


}
