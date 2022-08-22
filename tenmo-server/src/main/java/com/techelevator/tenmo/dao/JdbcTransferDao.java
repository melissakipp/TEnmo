package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public Transfer sendMoney(Long accountFrom, Long accountTo, BigDecimal amount) {
        return null;
    }

    @Override
    public List<Transfer> getAllTransfers(String username) {
        return null;
    }

    @Override
    public Transfer getTransfer(Long transferId) {
        return null;
    }
}
