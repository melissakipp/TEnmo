package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("users")
    // using Principal object to maintain security and return username in methods
    public User[] getUsers(Principal principal) {
        int idToFilterOut = userDao.findIdByUsername(principal.getName());
        return userDao.findAllButCurrentUser(idToFilterOut);
    }

}
