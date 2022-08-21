package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.Principal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // TODO: Check return
    @Override
    public AccountDTO getAccount(String accountName) {
        AccountDTO account = null;
        String sql = "SELECT account.account_id, account.user_id, account.balance " +
                "FROM account JOIN tenmo_user " +
                "ON tenmo_user.user_id = account.user_id " +
                "WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountName);
        if (results.next()) {
            account = mapRowToSale(results);
        }
        return account;
    }

    @Override
    public BigDecimal getAccountBalance(String username) {
        return getAccount(username).getBalance();
    }

    private AccountDTO mapRowToSale(SqlRowSet rowSet) {
        AccountDTO account = new AccountDTO();
        account.setAccountId(rowSet.getLong("account_id"));
        account.setUserId(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

}
