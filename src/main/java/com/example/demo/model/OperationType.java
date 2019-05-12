package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "otype")
@Data
public class OperationType {

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "operation_type", nullable = false)
    private String operationType;

}
