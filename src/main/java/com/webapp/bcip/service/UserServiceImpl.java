package com.webapp.bcip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.bcip.dao.UserDao;
import com.webapp.bcip.model.User;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

@Autowired
private UserDao dao;

@Autowired
private PasswordEncoder passwordEncoder;

    public User findById(int id) {
        return dao.findById(id);
    }

    public User findByUsername(String username) {
        User user = dao.findByUsername(username);
        return user;
    }

    public User findByEmail(String email) {
        User user = dao.findByEmail(email);
        return user;
    }
        
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    public void updateUser(User user) {
        User entity = dao.findById(user.getId());
        if(entity!=null){
            entity.setUsername(user.getUsername());
            if(!user.getPassword().equals(entity.getPassword())){
                entity.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setEmail(user.getEmail());
            entity.setOrganization(user.getOrganization());
            entity.setUserProfiles(user.getUserProfiles());
        }
    }


    public void deleteUserByUsername(String username) {
        dao.deleteByUsername(username);
    }

    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    public boolean isUsernameUnique(Integer id, String username) {
        User user = findByUsername(username);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }
	
    public boolean isEmailUnique(Integer id, String email) {
        User user = findByEmail(email);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }
    
}
