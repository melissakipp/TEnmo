package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer createTransfer(Transfer transfer);
    Transfer getTransfer(Long transferId);
    Transfer processTransfer(Transfer transfer);
    List<Transfer> getAllTransfers(String username);


}
