package com.yet.project.web.exception.admin.item;

public class NoBrandListException extends RuntimeException{
    public NoBrandListException() {
    }

    public NoBrandListException(String message) {
        super(message);
    }

    public NoBrandListException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoBrandListException(Throwable cause) {
        super(cause);
    }

    public NoBrandListException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
