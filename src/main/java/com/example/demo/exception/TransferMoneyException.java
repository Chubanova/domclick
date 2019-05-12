package com.example.demo.exception;

import lombok.Getter;


public class TransferMoneyException extends RuntimeException{
    @Getter
    private ErrorCode errorCode;
    
    public TransferMoneyException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public TransferMoneyException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public TransferMoneyException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}
