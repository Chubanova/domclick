package com.example.demo.controller.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;


@ToString
@Data
public class TransferRequest extends MoneyRequest {
    private Long accountNumberAdditional;

    @Tolerate
    TransferRequest() {
    }

    public TransferRequest(MoneyRequest moneyRequest) {
        super(moneyRequest.getAccountNumber(), moneyRequest.getBalance());
    }
}
