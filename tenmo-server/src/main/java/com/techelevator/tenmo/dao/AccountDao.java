package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountDTO;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountDao {

    BigDecimal getAccountBalance(String username);

    AccountDTO getAccount(String username);
}
