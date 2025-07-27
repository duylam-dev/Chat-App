package com.hust.chatappbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiResponseCustom<T> {
    private int code;
    private String message;
    private T data;
    private Map<String, Object> errors;
}
