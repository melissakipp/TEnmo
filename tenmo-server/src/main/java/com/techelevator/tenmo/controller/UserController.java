package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("users")
    public User[] getUsers(Principal principal) {
        // TODO: Updated getUsers() to include Principal so that we can get the id, passed id to findAll() to filter out by that ID
        int idToFilterOut = userDao.findIdByUsername(principal.getName());
        return userDao.findAll(idToFilterOut);
    }

}
