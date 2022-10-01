package com.techelevator.tenmo.services;

import com.techelevator.tenmo.exceptions.UserNotFoundException;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Formatter;

public class TransferService  {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl;
    private final long SEND_MONEY_ID = 2L;
    private final long SUCCESS_STATUS_ID = 2L;

    public TransferService(String apiUrl) {
        this.apiUrl = apiUrl + "account/";
    }

    public String transfer(AuthenticatedUser currentUser, Long accountTo, BigDecimal amount) throws UserNotFoundException {
        String token = currentUser.getToken();
        Long accountFrom = currentUser.getUser().getId();
        Transfer transfer = new Transfer(accountFrom, accountTo, amount);
        // setting theses here so that they could easily be updated for different transfer types
        // Todo: fix magic numbers ex. 2L
        transfer.setTransferTypeId(SEND_MONEY_ID);
        transfer.setTransferStatusId(SUCCESS_STATUS_ID);
        String returnMessage = "";

        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(apiUrl + "transfer", HttpMethod.POST, makeAuthEntity(token, transfer), String.class);
            returnMessage = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            System.out.println("User not found. Please refer to the log files.");

        }
        return returnMessage;
    }

    public Transfer[] getAllTransfers(AuthenticatedUser currentUser) {
        String token = currentUser.getToken();
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(apiUrl + "transfer/history", HttpMethod.GET, makeAuthEntity(token), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            System.out.println("There was an error. Please refer to the log files.");
        }
        if (transfers == null) {
            System.out.println("There are no transfers for this customer.");
        }
        return transfers;
    }

    public String getTransfer(AuthenticatedUser currentUser, int transferIdInput) {
        String token = currentUser.getToken();
        String transferDetail = "";
        StringBuilder sb = new StringBuilder();
        Formatter fm = new Formatter(sb);
        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(apiUrl + "transfer/" + transferIdInput, HttpMethod.GET, makeAuthEntity(token), Transfer.class);
            Transfer transfer = response.getBody();
            sb.append("\n--------------------------------------------");
            sb.append("\nTransfer Details");
            sb.append("\n--------------------------------------------");
            fm.format("%nId: %-5d %nFrom: %-15s %nTo: %-15s %nType: %-15s %nStatus: %-15s %nAmount: %-15s %n",
                    transfer.getTransferId(),
                    transfer.getSender(),
                    transfer.getRecipient(),
                    transfer.getTransferTypeDescription(),
                    transfer.getTransferStatusDescription(),
                    transfer.getAmount().toString());

            transferDetail = String.valueOf(sb);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            System.out.println("There was an error. Please refer to the log files.");
        };
        return transferDetail;
    }

    private HttpEntity<Void> makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeAuthEntity(String token, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<Transfer>(transfer, headers);
    }


}
