package com.kit.promokhapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO<T> {
    private int status;
    private String message;
    private T data;
}
