package com.example.demo.model;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Entity
@Table(name = "Transaction")
@Data
public class Transaction {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "main_account_id", nullable = false)
    private Long mainAccountId;
    @Column(name = "operation_type_id", nullable = false)
    private Long operationTypeId;
    @Column(name = "additional_account_id", nullable = true)
    private Long additionalAccountId;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "status_id", nullable = false)
    private Long statusId;
    @Column(name = "operation_time", nullable = false)
    private Date operationTime;
}
