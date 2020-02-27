package com.cloneZjrt.util;

import com.cloneZjrt.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     *  获取当前用户的密码
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String name = (String) authenticationToken.getPrincipal();
//        String password = getPassword(name);
//        if (password == null) {
//            return null;
//        }
//        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, password, "MyRealm");
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, "password", "MyRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(name));
        return simpleAuthenticationInfo;
    }

//    private String getPassword(String name) {
//        String password = userService.getPasswordByName(name);
//        return password;
//    }

    /**
     * 获取当前用户的权限集
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String name = (String) principalCollection.getPrimaryPrincipal();
//        Set<String> roles = getRoleByName(name);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

//    private Set<String> getRoleByName(String name) {
//        Set<String> set = userService.getRoles(name);
//        return set;
//    }

}
