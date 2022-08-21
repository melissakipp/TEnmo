package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.AccountDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/account")
public class AccountController {

    private AccountDao dao;

    public AccountController(AccountDao dao) {
        this.dao = dao;
    }
/* Previous implementation
    @GetMapping("/{id}")
    public BigDecimal getAccount(@Valid @PathVariable Long id, Principal principal) {
        return dao.getBalance(id, principal);
    }
*/

    @GetMapping("")
    public AccountDTO getAccount(Principal principal) {
        return dao.getAccount(principal.getName());
    }

    @GetMapping("/balance")
    public BigDecimal getAccountBalance(Principal principal) {
        return dao.getAccountBalance(principal.getName());
    }

}
