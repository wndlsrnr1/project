package com.yet.project.web.exception.admin.item;

public class CategoryMismatchException extends IllegalArgumentException{
    public CategoryMismatchException() {
    }

    public CategoryMismatchException(String s) {
        super(s);
    }

    public CategoryMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryMismatchException(Throwable cause) {
        super(cause);
    }
}
