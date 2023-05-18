package com.yet.project.web.exception.admin.item;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class SubcategoryMismatchException extends IllegalArgumentException {

    public SubcategoryMismatchException() {
    }

    public SubcategoryMismatchException(String s) {
        super(s);
    }

    public SubcategoryMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubcategoryMismatchException(Throwable cause) {
        super(cause);
    }
}
