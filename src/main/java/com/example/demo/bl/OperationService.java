package com.example.demo.bl;

import com.example.demo.controller.model.MoneyRequest;
import com.example.demo.controller.model.TransferRequest;
import com.example.demo.dao.AccountDAO;
import com.example.demo.dao.TransactionDAO;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.TransferMoneyException;
import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.model.enums.OperationTypeValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;

import static com.example.demo.exception.ErrorCode.ERROR_WRONG_ACCOUNT;
import static com.example.demo.exception.ErrorCode.ERROR_WRONG_AMOUNT;
import static com.example.demo.model.enums.OperationTypeValue.GET;
import static com.example.demo.model.enums.OperationTypeValue.SET;
import static com.example.demo.model.enums.OperationTypeValue.TRANSFER;
import static com.example.demo.model.enums.StatusValue.FAIL;
import static com.example.demo.model.enums.StatusValue.SUCCESS;

@Service
@Slf4j
public class OperationService {

    @Autowired
    AccountDAO accountDAO;
    @Autowired
     TransactionDAO transactionDAO;

    @Transactional(noRollbackFor = TransferMoneyException.class)
    public void getMoney(MoneyRequest request) throws TransferMoneyException {
        Account account = accountDAO.findByAccountNumber(request.getAccountNumber());

        if (account == null) {
            failTransaction(request, null, null, ERROR_WRONG_ACCOUNT, GET);
        } else if (request.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            failTransaction(request, account, null, ERROR_WRONG_AMOUNT, GET);
        } else if (account.getBalance().compareTo(request.getBalance()) < 0) {
            failTransaction(request, account, null, ERROR_WRONG_AMOUNT, GET);
        } else {
            account.setBalance(account.getBalance().subtract(request.getBalance()));
            accountDAO.save(account);
            Transaction transaction = Transaction.builder()
                    .mainAccountId(account.getId())
                    .amount(request.getBalance())
                    .operationTypeId(GET.getCode())
                    .statusId(SUCCESS.getCode())
                    .operationTime(Calendar.getInstance().getTime()).build();
            transactionDAO.save(transaction);
            log.info("Update account " + account.toString());
        }
    }

    @Transactional(noRollbackFor = TransferMoneyException.class)
    public void setMoney(MoneyRequest request) throws TransferMoneyException {
        Account account = accountDAO.findByAccountNumber(request.getAccountNumber());
        if (account == null) {
            failTransaction(request, null, null, ERROR_WRONG_ACCOUNT, SET);
        } else if (request.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            failTransaction(request, account, null, ERROR_WRONG_AMOUNT, SET);
        } else {
            account.setBalance(account.getBalance().add(request.getBalance()));
            accountDAO.save(account);
            Transaction transaction = Transaction.builder()
                    .mainAccountId(account.getId())
                    .amount(request.getBalance())
                    .operationTypeId(SET.getCode())
                    .statusId(SUCCESS.getCode())
                    .operationTime(Calendar.getInstance().getTime()).build();
            transactionDAO.save(transaction);
            log.info("Update account " + account.toString());
        }
    }

    @Transactional(noRollbackFor = TransferMoneyException.class)
    public void transferMoney(TransferRequest request) throws TransferMoneyException {
        Account mainAccount = accountDAO.findByAccountNumber(request.getAccountNumber());
        Account additionalAccount = accountDAO.findByAccountNumber(request.getAccountNumberAdditional());

        if (mainAccount == null || additionalAccount == null) {
            failTransaction(request, mainAccount, additionalAccount, ERROR_WRONG_ACCOUNT, TRANSFER);
        } else if (mainAccount.equals(additionalAccount)) {
            failTransaction(request, mainAccount, additionalAccount, ERROR_WRONG_ACCOUNT, TRANSFER);
        } else if (request.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            failTransaction(request, mainAccount, additionalAccount, ERROR_WRONG_AMOUNT, TRANSFER);
        } else if (mainAccount.getBalance().compareTo(request.getBalance()) < 0) {
            failTransaction(request, mainAccount, additionalAccount, ERROR_WRONG_AMOUNT, TRANSFER);
        } else {
            mainAccount.setBalance(mainAccount.getBalance().subtract(request.getBalance()));
            additionalAccount.setBalance(additionalAccount.getBalance().add(request.getBalance()));
            accountDAO.save(mainAccount);
            accountDAO.save(additionalAccount);
            Transaction transaction = Transaction.builder()
                    .mainAccountId(mainAccount.getId())
                    .additionalAccountId(additionalAccount.getId())
                    .amount(request.getBalance())
                    .statusId(SUCCESS.getCode())
                    .operationTypeId(TRANSFER.getCode())
                    .operationTime(Calendar.getInstance().getTime())
                    .build();
            transactionDAO.save(transaction);
        }
    }

    void failTransaction(MoneyRequest request, Account main, Account additional, ErrorCode code, OperationTypeValue operation) {
        Transaction transaction = Transaction.builder()
                .mainAccountId(main != null ? main.getId() : 0)
                .additionalAccountId(additional != null ? additional.getId() : 0)
                .amount(request.getBalance())
                .statusId(FAIL.getCode())
                .operationTypeId(operation.getCode())
                .operationTime(Calendar.getInstance().getTime())
                .build();
        transactionDAO.save(transaction);
        log.error(code.getDescription());
        throw new TransferMoneyException(code);
    }
}

