package com.yet.project.web.exception.admin.item;

public class NoCategoryListException extends RuntimeException{
    public NoCategoryListException() {
    }

    public NoCategoryListException(String message) {
        super(message);
    }

    public NoCategoryListException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCategoryListException(Throwable cause) {
        super(cause);
    }

    public NoCategoryListException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
