package com.yet.project.web.exception.admin.item;

public class BrandMismatchException extends IllegalArgumentException{
    public BrandMismatchException() {
        super();
    }

    public BrandMismatchException(String s) {
        super(s);
    }

    public BrandMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrandMismatchException(Throwable cause) {
        super(cause);
    }
}
