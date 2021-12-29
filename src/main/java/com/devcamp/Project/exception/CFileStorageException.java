package com.devcamp.Project.exception;

public class CFileStorageException extends RuntimeException{
    public CFileStorageException(String message) {
        super(message);
    }

    public CFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
