package com.example.demo.controller.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.math.BigDecimal;

@Data
@ToString
@Builder
public class MoneyRequest {
    private Long accountNumber;
    private BigDecimal balance;
    @Tolerate
    MoneyRequest (){}
}
