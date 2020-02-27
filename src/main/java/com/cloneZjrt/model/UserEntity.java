package com.cloneZjrt.model;

import lombok.Data;

@Data
public class UserEntity {
    private long userId;
    private String userName;

    public UserEntity(){

    }

    public UserEntity(long userId, String userName){
        this.userId = userId;
        this.userName = userName;
    }

    public UserEntity( String userName){
        this.userName = userName;
    }

}
