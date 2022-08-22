package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<User> getAllUsers();
    Transfer sendMoney(Long accountFrom, Long accountTo, BigDecimal amount);
    List<Transfer> getAllTransfers(String username);
    Transfer getTransfer(Long transferId);

}
