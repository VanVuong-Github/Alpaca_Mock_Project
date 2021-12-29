package com.devcamp.Project.exception;

public class CMyFileNotFoundException extends RuntimeException{
    public CMyFileNotFoundException(String message) {
        super(message);
    }

    public CMyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
