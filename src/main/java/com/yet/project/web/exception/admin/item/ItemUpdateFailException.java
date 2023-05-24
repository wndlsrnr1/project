package com.yet.project.web.exception.admin.item;

public class ItemUpdateFailException extends IllegalArgumentException{
    public ItemUpdateFailException() {
    }

    public ItemUpdateFailException(String s) {
        super(s);
    }

    public ItemUpdateFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemUpdateFailException(Throwable cause) {
        super(cause);
    }
}
