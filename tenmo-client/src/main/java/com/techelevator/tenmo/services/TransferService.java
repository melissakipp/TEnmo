package com.techelevator.tenmo.services;

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
        this.apiUrl = apiUrl + "/account/transfer";
    }

    public boolean transfer(AuthenticatedUser currentUser, Long accountTo, BigDecimal amount) {
        String token = currentUser.getToken();
        Long accountFrom = currentUser.getUser().getId();
        Transfer transfer = new Transfer(accountFrom, accountTo, amount);
        boolean isSuccessful = true;
        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(apiUrl, HttpMethod.POST, makeAuthEntity(token), Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            isSuccessful = false;
            BasicLogger.log(e.getMessage());
            System.out.println("There was an error. Please refer to the log files.");
        }
        return isSuccessful;
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
