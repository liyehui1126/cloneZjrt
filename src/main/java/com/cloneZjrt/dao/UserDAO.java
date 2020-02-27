package com.cloneZjrt.dao;

import com.cloneZjrt.model.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserDAO {

    List<UserEntity> queryAll();

    List<String> queryUserRoles(Long userId);

    UserEntity getUserById(Long userId);

    UserEntity getUserByName(String userName);

    int register(UserEntity userEntity);
}
