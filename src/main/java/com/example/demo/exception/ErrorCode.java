package com.example.demo.exception;

public enum ErrorCode {
    ERROR_WRONG_ACCOUNT(1, "Error: wrong account"),
    ERROR_WRONG_AMOUNT (2, "Error: wrong amount");

    private final int code;

    private final String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
