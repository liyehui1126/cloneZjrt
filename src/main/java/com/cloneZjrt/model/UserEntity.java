package com.cloneZjrt.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName(value = "userinfo")
public class UserEntity extends Model<UserEntity> {

    @TableId(value = "userid",type = IdType.AUTO)
    private long userId;
    @TableField(value = "username")
    private String userName;

    @TableField(exist = false)
    private List<RoleEntity> roleEntities;

    public UserEntity(){

    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    public UserEntity(long userId, String userName){
        this.userId = userId;
        this.userName = userName;
    }

    public UserEntity( String name){
        this.userName = userName;
    }

}
