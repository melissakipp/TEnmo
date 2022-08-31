package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Controller for transfer functionality.
 */
@RestController
@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;
    private AccountDao accountDao;
    private UserDao userDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public String createTransfer(@Valid @RequestBody Transfer transfer) {
        transfer.setAccountTo(accountDao.getAccountIdByUserId(transfer.getAccountTo()));
        transfer.setAccountFrom(accountDao.getAccountIdByUserId(transfer.getAccountFrom()));
        transfer = transferDao.createTransfer(transfer);
        return transferDao.processTransfer(transfer);
    }

    @GetMapping("/transfer/history")
    // using Principal object to maintain security and return username in methods
    public Transfer[] getAllTransfers(Principal principal) {
        return transferDao.getAllTransfers(principal.getName());
    }

    @GetMapping("/transfer/{id}")
    public Transfer getTransfer(@PathVariable Long id) {
        return transferDao.getTransfer(id);
    }
}
