package com.webapp.bcip.model;

import java.io.Serializable;

public enum UserProfileType implements Serializable{
    GUEST("GUEST"),
    USER("USER"),
    ADMIN("ADMIN");

    String userProfileType;

    private UserProfileType(String userProfileType){
        this.userProfileType = userProfileType;
    }

    public String getUserProfileType(){
        return userProfileType;
    }
	
}
