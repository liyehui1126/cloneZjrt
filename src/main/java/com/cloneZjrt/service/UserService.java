package com.cloneZjrt.service;

import com.cloneZjrt.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    
    public Integer userSize();

    public List<UserEntity> findAll();

    public UserEntity getUserByName(String username);

    public UserEntity getUserById(Long userId);

    public int register(UserEntity userEntity);

    public List<Long> getRoleIdByUserId(Long userId);
}
