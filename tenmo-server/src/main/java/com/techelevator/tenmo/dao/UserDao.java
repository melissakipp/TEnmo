package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

public interface UserDao {

    User[] findAll();

    User[] findAllButCurrentUser(int idToFilterOut);

    User findUserByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    String findNameByAccountId(Long accountId);
}
