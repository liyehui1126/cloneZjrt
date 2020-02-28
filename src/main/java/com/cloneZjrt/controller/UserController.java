package com.cloneZjrt.controller;

import com.cloneZjrt.dao.UserDAO;
import com.cloneZjrt.model.UserEntity;
import com.cloneZjrt.service.UserService;
import com.cloneZjrt.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {

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
    public String test(){
        System.out.println("test");
        return "test";
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
    public UserEntity getUser(Long userId) {
        System.out.println(userId);
        return userService.getUserById(userId);

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
