package com.webapp.bcip.dao;

import java.util.List;

import com.webapp.bcip.model.UserProfile;


public interface UserProfileDao {

    List<UserProfile> findAll();

    UserProfile findByType(String type);

    UserProfile findById(int id);
}
