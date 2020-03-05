package com.cloneZjrt.dao;

import com.cloneZjrt.model.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserDAO {

    List<UserEntity> queryAll();

    List<Long> queryUserRoles(Long userId);

    UserEntity getUserById(Long userId);

    UserEntity getUserByName(@Param("userName") String userName);

//    List<UserEntity> getUserByNameTest(@Param("userName") String userName);

    List<UserEntity> getUserByNameTest(@Param("userName") String userName);

    int register(UserEntity userEntity);

//    增加单个
    int insertUser(UserEntity userEntity);
//    增加列表
    int insertUsers(List<UserEntity> userEntities);
//    增加属性值
    int insertUsernameById(Long userId, String userName);
//    修改字段
    int update(UserEntity userEntity);
//    分页查询
    List<UserEntity> queryList(Long startRow,
                               Long pageSize);

//    模糊查询查询（like和CONCAT）
//    时间范围查询
    List<UserEntity> queryByTime(String beginTime,
                                 String endTime);
//    批量查询
    List<UserEntity> queryByIds(@Param("userIds")List<Long> userIds,
                                @Param("startRow")Long startRow,
                                @Param("pageSize")Long pageSize);

}
