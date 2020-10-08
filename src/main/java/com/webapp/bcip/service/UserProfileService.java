package com.webapp.bcip.service;

import java.util.List;

import com.webapp.bcip.model.UserProfile;


public interface UserProfileService {

    UserProfile findById(int id);

    UserProfile findByType(String type);

    List<UserProfile> findAll();
	
}
