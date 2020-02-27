package com.cloneZjrt.service.implment;


import com.cloneZjrt.dao.UserDAO;
import com.cloneZjrt.model.UserEntity;
import com.cloneZjrt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserDAO userDAO;
    
    @Override
    public Integer userSize() {
        return userDAO.queryAll().size();
    }

    @Override
    public List<UserEntity> findAll() {
        return userDAO.queryAll();
    }

    @Override
    public UserEntity getUserByName(String username) {
        return userDAO.getUserByName(username);
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public int register(UserEntity userEntity) {
        return userDAO.register(userEntity);
    }
}
