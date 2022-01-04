package com.devcamp.Project.error;

import lombok.*;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private String message;
}
