package com.yet.project.web.exception.admin.item;


public class ItemMisMatchException extends RuntimeException{
    public ItemMisMatchException() {
        super();
    }

    public ItemMisMatchException(String message) {
        super(message);
    }

    public ItemMisMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemMisMatchException(Throwable cause) {
        super(cause);
    }

    protected ItemMisMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
