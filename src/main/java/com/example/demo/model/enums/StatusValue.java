package com.example.demo.model.enums;

public enum StatusValue {
    SUCCESS(1L, "success"),
    FAIL(2L, "fail");

    private final Long code;

    private final String description;



    StatusValue(Long code, String description) {
        this.code = code;
        this.description = description;
    }

    public Long getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
