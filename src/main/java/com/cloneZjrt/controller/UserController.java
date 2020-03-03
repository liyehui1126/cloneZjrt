package com.cloneZjrt.controller;

import com.cloneZjrt.dao.UserDAO;
import com.cloneZjrt.exception.BusinessException;
import com.cloneZjrt.model.UserEntity;
import com.cloneZjrt.result.ReturnResult;
import com.cloneZjrt.schedule.MyScheduler;
import com.cloneZjrt.service.UserService;
import com.cloneZjrt.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;

    @Resource
    private UserDAO userDAO;

//    @RequestMapping(value = "/countId", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
//    @ResponseBody
//    public Object countId(){
//
//
//    }

    @GetMapping(value = "/test")
    public String test(Long userId){
//        System.out.println("test");
//        return "test";
//        return new BusinessException("错误",400l).toString();

//        List<String> strings = new ArrayList<>();
//        try{
//            return strings.get(2);
//        }catch (Exception e){
//            throw e;
//        }
        return JWTUtil.sign(userId,86400000);
    }

    @RequestMapping(value = "test2", method = RequestMethod.GET)
    @XXSecurity({RoleConstant.One,RoleConstant.Two})
    public String test2(@RequestHeader("token")String token) {
        Long userId = JWTUtil.unsign(token,Long.class);
        UserEntity userEntity = userService.getUserById(userId);
        return "OK";
//        return ReturnResult.success(userEntity);
    }

    @RequestMapping(value = "test3", method = RequestMethod.GET)
    @XXSecurity({RoleConstant.Four})
    public String test3(@RequestHeader("token")String token) {
        Long userId = JWTUtil.unsign(token,Long.class);
        UserEntity userEntity = userService.getUserById(userId);
        return "Ok";
//        return ReturnResult.success(userEntity);
    }

    @RequestMapping(value = "test4", method = RequestMethod.GET)
    public String test4() throws Exception{
//        MyScheduler myScheduler = new MyScheduler();
//        myScheduler.quartzText();
        return "Ok";
//        return ReturnResult.success(userEntity);
    }

    @RequestMapping(value = "test5", method = RequestMethod.GET)
    public String test5() throws Exception{
        return "Ok";
    }

//    @PostMapping(value = "/login")
//    public String login(UserEntity user){
//        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(),user.getPassword());
//        Subject subject= SecurityUtils.getSubject();
//        try{
//            subject.login(token);
//        }catch (Exception e){
//            return e.getMessage() + "登陆失败";
//        }
//        if(subject.hasRole("admin")){
//            return "权限正确";
//        }
//        return "权限失败";
//    }
//
//    @GetMapping(value = "/getRoles")
//    public String getRoles(@RequestHeader("token")String token){
//        Long userid = JWT.unsign(token,Long.class);
//        UserEntity user = userService.queryById(userid);
//        UsernamePasswordToken upt = new UsernamePasswordToken(user.getName(),user.getPassword());
//        Subject subject= SecurityUtils.getSubject();
//        try{
//            subject.login(upt);
//        }catch (Exception e){
//            return e.getMessage();
//        }
//        List<String> roles = userService.queryUserRoles(userid);
//        return roles.toString();
//    }


    @RequestMapping(value = "getUser", method = RequestMethod.POST)
    public UserEntity getUser(@RequestParam(name = "userId") Long userId) {
        System.out.println(userId);
        UserEntity userEntity = new UserEntity();
        try{
            userEntity = userService.getUserById(userId);
        }catch (Exception e){
            throw new BusinessException("错误",400l);
        }
        return userEntity;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

//    @RequestMapping(value = "/register", method = RequestMethod.GET)
//    public String register() {
//        return "register";
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginUser(String userName, HttpSession session) {
        System.out.println(userName);
        if(userService.getUserByName(userName) == null){
            try{
                userService.register(new UserEntity(userName));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        UserEntity user = userService.getUserByName(userName);
        session.setAttribute("user",user);
        return new ModelAndView("chat");
    }

}
