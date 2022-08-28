package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for transfer functionality.
 */
@RestController
@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @PostMapping("/transfer")
    public String createTransfer(@RequestBody Transfer transfer) {
        transfer.setAccountTo(accountDao.getAccountIdByUserId(transfer.getAccountTo()));
        transfer.setAccountFrom(accountDao.getAccountIdByUserId(transfer.getAccountFrom()));
        transfer = transferDao.createTransfer(transfer);
        return transferDao.processTransfer(transfer);
    }
}
