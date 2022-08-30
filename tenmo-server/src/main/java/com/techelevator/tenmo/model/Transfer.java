package com.techelevator.tenmo.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {

    private Long transferId;
    private Long transferTypeId;
    private String transferTypeDescription;
    private Long transferStatusId;
    private String transferStatusDescription;
    @NotNull
    private Long accountFrom;
    @NotNull
    private Long accountTo;
    @Positive
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
    private String sender;
    private String recipient;

    public Transfer(
            Long transferId,
            Long transferTypeId,
            String transferTypeDescription,
            Long transferStatusId,
            String transferStatusDescription,
            Long accountFrom,
            Long accountTo,
            BigDecimal amount,
            String sender,
            String recipient) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferTypeDescription = transferTypeDescription;
        this.transferStatusId = transferStatusId;
        this.transferStatusDescription = transferStatusDescription;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Transfer(){}

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }
    public String getTransferTypeDescription() {
        return transferTypeDescription;
    }

    public void setTransferTypeDescription(String transferTypeDescription) {
        this.transferTypeDescription = transferTypeDescription;
    }

    public Long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDescription() {
        return transferStatusDescription;
    }

    public void setTransferStatusDescription(String transferStatusDescription) {
        this.transferStatusDescription = transferStatusDescription;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
