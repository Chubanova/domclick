package com.example.demo.model;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "Account")
@Data
@ToString
@Builder
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "account_number", nullable = false)
    private Long accountNumber;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
    @Tolerate
    Account(){}
}
