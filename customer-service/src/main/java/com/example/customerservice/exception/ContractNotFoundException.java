package com.example.customerservice.exception;


public class ContractNotFoundException extends RuntimeException {
    public ContractNotFoundException(String message) {
        super(message);
    }

    public ContractNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
