package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

public interface UserDao {

    User[] findAll(int idToFilterOut);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);
}
