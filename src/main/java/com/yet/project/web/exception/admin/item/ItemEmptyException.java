package com.yet.project.web.exception.admin.item;

public class ItemEmptyException extends RuntimeException{
    public ItemEmptyException() {
        super();
    }

    public ItemEmptyException(String message) {
        super(message);
    }

    public ItemEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemEmptyException(Throwable cause) {
        super(cause);
    }

    protected ItemEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
