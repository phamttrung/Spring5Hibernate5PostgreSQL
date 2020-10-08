package com.webapp.bcip.dao;

import java.util.List;

import com.webapp.bcip.model.User;


public interface UserDao {

    User findById(int id);

    User findByUsername(String username);
    
    User findByEmail(String email);

    void save(User user);

    void deleteByUsername(String username);

    List<User> findAllUsers();

}

