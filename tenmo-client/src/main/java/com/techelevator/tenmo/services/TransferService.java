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

public class TransferService  {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl;

    public TransferService(String apiUrl) {
        this.apiUrl = apiUrl + "account/transfer";
    }

    public String transfer(AuthenticatedUser currentUser, Long accountTo, BigDecimal amount) throws UserNotFoundException {
        String token = currentUser.getToken();
        Long accountFrom = currentUser.getUser().getId();
        Transfer transfer = new Transfer(accountFrom, accountTo, amount);
        transfer.setTransferTypeId(2L);
        transfer.setTransferStatusId(2L);
        String returnMessage = "";

        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(apiUrl, HttpMethod.POST, makeAuthEntity(token, transfer), String.class);
            returnMessage = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            System.out.println("User not found. Please refer to the log files.");
            //throw new UserNotFoundException();
        }
        return returnMessage;
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
