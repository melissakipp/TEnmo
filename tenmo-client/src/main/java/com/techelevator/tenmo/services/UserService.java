package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl;

    public UserService(String apiUrl) {
        this.apiUrl = apiUrl + "users";
    }

    public List<User> getAllUsersExceptCurrent(AuthenticatedUser currentUser) {
        String token = currentUser.getToken();
        List<User> users = new ArrayList<>();

        try {
            ResponseEntity<User[]> response =
                    restTemplate.exchange(apiUrl, HttpMethod.GET,
                            makeAuthEntity(token), User[].class);
            users = Arrays.asList(response.getBody());
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            System.out.println("There was an error. Please refer to the log files.");
        }
        return users;
    }
    private HttpEntity<Void> makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }
}
