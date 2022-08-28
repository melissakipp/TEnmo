package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.jboss.logging.BasicLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private AccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, UserDao userDao, AccountDao accountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {

        String sqlCreateTransfer = "INSERT INTO transfer(transfer_type_id, " +
                "transfer_status_id, " +
                "account_from," +
                "account_to, " +
                "amount) " +
                "VALUES (?,?,?,?,?) RETURNING transfer_id;";
        Transfer newTransfer = null;
        Long transferId = 0L;
        try {
            transferId = jdbcTemplate.queryForObject(
                    sqlCreateTransfer,
                    Long.class,
                    transfer.getTransferTypeId(),
                    transfer.getTransferStatusId(),
                    transfer.getAccountFrom(),
                    transfer.getAccountTo(),
                    transfer.getAmount());
            newTransfer = getTransfer(transferId);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
        return newTransfer;
    }

    @Override
    public List<Transfer> getAllTransfers(String username) {
        return null;
    }

    @Override
    public Transfer getTransfer(Long transferId) {
        Transfer newTransfer = null;
        String sql = "SELECT transfer_id, transfer.transfer_type_id, transfer_type_desc, transfer.transfer_status_id, transfer_status_desc, account_from, account_to, amount " +
                "FROM transfer " +
                "JOIN transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id " +
                "JOIN transfer_type ON transfer_type.transfer_type_id = transfer.transfer_type_id " +
                "WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            newTransfer = mapRowToTransfer(results);
        }
        return newTransfer;
    }

    @Override
    public Transfer processTransfer(Transfer transfer) {
        String sql = "BEGIN TRANSACTION;\n" +
                "\n" +
                "UPDATE account SET balance = balance - 100 WHERE user_id = 1002;\n" +
                "\n" +
                "UPDATE account SET balance = balance + 100 WHERE user_id = 1001;\n" +
                "\n" +
                "ROLLBACK;";
        BigDecimal accountToBalance = accountDao.getAccountBalanceByAccountId(transfer.getAccountTo());
        BigDecimal accountFromBalance = accountDao.getAccountBalanceByAccountId(transfer.getAccountFrom());
        BigDecimal amountToTransfer = transfer.getAmount();
        // TODO: Find out to compare two BigDecimals
//        if (accountToBalance.compareTo(amountToTransfer) == 1 && accountFromBalance.compareTo(amountToTransfer) == 1 && amountToTransfer.compareTo(BigDecimal(0)) == 1) {
//            jdbcTemplate.update(sql);
//        }
        return null;
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
    Transfer transfers = new Transfer();
        transfers.setTransferId(rs.getLong("transfer_id"));
        transfers.setTransferTypeDescription(rs.getString("transfer_type_desc"));
        transfers.setTransferTypeId(rs.getLong("transfer_type_id"));
        transfers.setTransferStatusId(rs.getLong("transfer_status_id"));
        transfers.setTransferStatusDescription(rs.getString("transfer_status_desc"));
        transfers.setAccountFrom(rs.getLong("account_from"));
        transfers.setAccountTo(rs.getLong("account_to"));
        transfers.setAmount(rs.getBigDecimal("amount"));
        return transfers;
    }
}
