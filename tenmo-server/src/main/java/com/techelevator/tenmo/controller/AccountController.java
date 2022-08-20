package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/account")
public class AccountController {

    private AccountDao dao;

    public AccountController(AccountDao dao) {
        this.dao = dao;
    }

    @GetMapping("/{id}")
    public BigDecimal getAccount(@Valid @PathVariable Long id) {
        return dao.getBalance(id);
    }


}
