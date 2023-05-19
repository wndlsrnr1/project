package com.yet.project.web.exception.admin.item;

public class NoSubCategoryListException extends RuntimeException{
    public NoSubCategoryListException() {
    }

    public NoSubCategoryListException(String message) {
        super(message);
    }

    public NoSubCategoryListException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSubCategoryListException(Throwable cause) {
        super(cause);
    }

    public NoSubCategoryListException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
