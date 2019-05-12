package com.example.demo.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Status")
@Data
public class Status {

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "status", nullable = false)
    private String status;

}
