package com.example.customerservice.exception;

public class InvalidRegistrationDataException extends RuntimeException {
    public InvalidRegistrationDataException() {
    }

    public InvalidRegistrationDataException(String message) {
        super(message);
    }

    public InvalidRegistrationDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRegistrationDataException(Throwable cause) {
        super(cause);
    }

    public InvalidRegistrationDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
