package com.example.demo.dao;

import com.example.demo.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO extends CrudRepository<Account, Long> {

    public Account findByAccountNumber(Long accountNumber);
}
