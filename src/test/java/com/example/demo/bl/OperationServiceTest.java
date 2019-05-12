package com.example.demo.bl;

import com.example.demo.bl.OperationService;
import com.example.demo.controller.model.MoneyRequest;
import com.example.demo.controller.model.TransferRequest;
import com.example.demo.dao.AccountDAO;
import com.example.demo.dao.TransactionDAO;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.TransferMoneyException;
import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static com.example.demo.model.enums.OperationTypeValue.TRANSFER;
import static com.example.demo.model.enums.StatusValue.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OperationServiceTest {
    @Autowired
    AccountDAO accountDAO;
    @Autowired
    TransactionDAO transactionDAO;
    Account account;

    MoneyRequest moneyRequest;
    TransferRequest transferRequest;
    OperationService operationService;
    MoneyRequest moneyRequestEmptyAccount;
    TransferRequest transferRequestEmptyAccount;
    MoneyRequest moneyRequestZeroBalance;
    TransferRequest transferRequestZeroBalance;
    MoneyRequest moneyBigRequest;
    TransferRequest transferBigRequest;

    @Before
    public void setUp() {
        moneyRequest = MoneyRequest.builder()
                .accountNumber(1001L)
                .balance(BigDecimal.ONE).build();
        transferRequest = new TransferRequest(moneyRequest);
        transferRequest.setAccountNumberAdditional(1002L);

        moneyRequestEmptyAccount = MoneyRequest.builder()
                .accountNumber(null)
                .balance(BigDecimal.ONE).build();
        transferRequestEmptyAccount = new TransferRequest(moneyRequestEmptyAccount);
        transferRequestEmptyAccount.setAccountNumberAdditional(null);

        moneyRequestZeroBalance = MoneyRequest.builder()
                .accountNumber(1001L)
                .balance(BigDecimal.ZERO).build();
        transferRequestZeroBalance = new TransferRequest(moneyRequestZeroBalance);
        transferRequestZeroBalance.setAccountNumberAdditional(1002L);

        moneyBigRequest = MoneyRequest.builder()
                .accountNumber(1001L)
                .balance(new BigDecimal(1000000))
                .build();
        transferBigRequest =new TransferRequest(moneyBigRequest);
        transferBigRequest.setAccountNumberAdditional(1002L);

        operationService = new OperationService();
        operationService.accountDAO = accountDAO;
        operationService.transactionDAO = transactionDAO;
    }

    @After
    public void tearDown() {

    }

    @Test
    public void addMoney() {
        operationService.setMoney(moneyRequest);

        assertThat(new BigDecimal(1001), Matchers.comparesEqualTo(accountDAO.findByAccountNumber(1001l).getBalance()));
    }


    @Test
    public void getMoney() {
        operationService.getMoney(moneyRequest);

        assertThat(new BigDecimal(999), Matchers.comparesEqualTo(accountDAO.findByAccountNumber(1001l).getBalance()));
    }

    @Test(expected = TransferMoneyException.class)
    public void setMoney_accountNull_TransferException() {
        operationService.setMoney(moneyRequestEmptyAccount);

    }

    @Test(expected = TransferMoneyException.class)
    public void setMoney_ZeroBalance_TransferException() {
        operationService.getMoney(moneyRequestZeroBalance);

    }

    @Test(expected = TransferMoneyException.class)
    public void getMoney_accountNull_TransferException() {
        operationService.getMoney(moneyRequestEmptyAccount);

    }

    @Test(expected = TransferMoneyException.class)
    public void getMoney_ZeroBalance_TransferException() {
        operationService.getMoney(moneyRequestZeroBalance);

    }

    @Test(expected = TransferMoneyException.class)
    public void getMoney_accountBalanceLessThenReqestBalance_TransferException() {
        operationService.getMoney(moneyBigRequest);

    }

    @Test
    public void transferMoney() {
        operationService.transferMoney(transferRequest);

        assertThat(new BigDecimal(999), Matchers.comparesEqualTo(accountDAO.findByAccountNumber(1001l).getBalance()));
        assertThat(new BigDecimal(1001), Matchers.comparesEqualTo(accountDAO.findByAccountNumber(1002l).getBalance()));
    }

    @Test(expected = TransferMoneyException.class)
    public void transferMoney_accountNull_TransferException() {
        operationService.transferMoney(transferRequestEmptyAccount);

    }

    @Test(expected = TransferMoneyException.class)
    public void transferMoney_ZeroBalance_TransferException() {
        operationService.transferMoney(transferRequestZeroBalance);

    }
    @Test(expected = TransferMoneyException.class)
    public void transferMoney_accountBalanceLessThenReqestBalance_TransferException() {
        operationService.transferMoney(transferBigRequest);

    }

}
