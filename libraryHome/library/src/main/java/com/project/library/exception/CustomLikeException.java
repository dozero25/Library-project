package com.project.library.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class CustomLikeException extends RuntimeException{
    private Map<String, String> errorMap;
}
