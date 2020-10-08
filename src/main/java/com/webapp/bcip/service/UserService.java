package com.webapp.bcip.service;

import java.util.List;

import com.webapp.bcip.model.User;


public interface UserService {
	
    User findById(int id);

    User findByUsername(String username);

    User findByEmail(String email);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserByUsername(String username);

    List<User> findAllUsers(); 

    boolean isUsernameUnique(Integer id, String username);
    
    boolean isEmailUnique(Integer id, String email);

}