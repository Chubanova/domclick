package com.example.demo.dao;

import com.example.demo.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDAO extends CrudRepository<Transaction, Long> {

}
