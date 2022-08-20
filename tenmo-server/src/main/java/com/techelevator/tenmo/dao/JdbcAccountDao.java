package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // TODO: Check return
    @Override
    public BigDecimal getBalance(long id) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, id);
    }

}

//class JdbcAccountDaoTestDrive {
//    public static void main(String[] args) {
//        DataSource dataSource
//        JdbcAccountDao test = new JdbcAccountDao(dataSource);
//
//    }
//}
