package com.yet.project.web.exception.admin.item;


public class ItemMisMatchException extends IllegalArgumentException{
    public ItemMisMatchException() {
    }

    public ItemMisMatchException(String s) {
        super(s);
    }

    public ItemMisMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemMisMatchException(Throwable cause) {
        super(cause);
    }
}
