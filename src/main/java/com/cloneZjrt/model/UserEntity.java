package com.cloneZjrt.model;

import lombok.Data;

import java.util.List;

@Data
public class UserEntity {
    private long userId;
    private String userName;

    private List<RoleEntity> roleEntities;

    public UserEntity(){

    }

    public UserEntity(long userId, String userName){
        this.userId = userId;
        this.userName = userName;
    }

    public UserEntity( String name){
        this.userName = userName;
    }

}
