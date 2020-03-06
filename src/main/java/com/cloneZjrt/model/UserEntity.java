package com.cloneZjrt.model;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "userinfo")
public class UserEntity {

    @TableId(value = "userid",type = IdType.AUTO)
    private long userId;
    @TableField(value = "username")
    private String userName;

    @TableField(exist = false)
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
