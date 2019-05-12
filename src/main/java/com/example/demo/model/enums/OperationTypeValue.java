package com.example.demo.model.enums;

public enum OperationTypeValue {
    GET(1L, "get" ),
    SET(2L, "set"),
    TRANSFER(3L, "transfer");

    private final Long code;

    private final String description;



    OperationTypeValue(Long code, String description) {
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
