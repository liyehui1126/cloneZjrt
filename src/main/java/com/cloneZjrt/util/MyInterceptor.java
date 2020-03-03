package com.cloneZjrt.util;

import com.cloneZjrt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    //请求发送到Controller之前调用
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        /**
         * 配置跨域
         */
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");//允许所有域名访问
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        /**
         * 根据自己的业务编写代买
         * 以验证请求头为例
         */
        if(httpServletRequest.getHeader("token") == null){
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");

            HashMap<String,String> returnJson = new HashMap<>();
            returnJson.put("status","415");
            returnJson.put("message","拒绝");
            returnJson.put("data",null);

            PrintWriter out;
            out = httpServletResponse.getWriter();
            out.append(returnJson.toString());

            return false;
        }

//        String token = httpServletRequest.getHeader("token");
//
//        if(token.equals("test")){
//            return true;
//        }

        String token = httpServletRequest.getHeader("token");
        if (token == null) {
            //这里应该用自定义异常
            httpServletResponse.getWriter().write("未登录，请重新登录后操作");
            return false;
        } else {
//            Claims c = util.parseJWT(jwt);
            //拿到自定义的角色数组
//            List<Integer> list = (List<Integer>) c.get("roles");
            Long userId = JWTUtil.unsign(token,Long.class);
            if (userId == null || userService.getUserById(userId) == null) {
//                throw new InsufficientAuthorityException("非法用户");
                System.out.println("非法用户");
            }
            List<Long> roleList = userService.getRoleIdByUserId(userId);
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                //拿到方法上的注解,方法上的注解优先
                XXSecurity eMethod = handlerMethod.getMethodAnnotation(XXSecurity .class);
                //拿不到，也能写在了类上，下面做判断
                if (eMethod != null) {
                    long[] value = eMethod.value();
                    //注解内容为空，说明这个方法不需要做权限控制
                    if (value.length == 0) {
                        return true;
                    }
                    //注解中的权限包含该用户的权限就直接放心
                    for (long i : value) {
                        if (roleList.contains(i)) {
                            return true;
                        }
                    }
                    //走完上面的循环，说明用户没有权限，我这里使用的自定义异常，当然return false也可以
//                    throw new InsufficientAuthorityException("权限不足");
                    System.out.println("权限不足");
                    return false;
                } else {
                    //拿到类上的注解，和上面同理
                    XXSecurity eType = handlerMethod.getMethod().getDeclaringClass().getAnnotation(XXSecurity .class);
                    if (eType != null) {
                        long[] value = eType.value();
                        if (value.length == 0) {
                            return true;
                        }
                        for (long i : value) {
                            if (roleList.contains(i)) {
                                return true;
                            }
                        }
                        //直接return false也行
//                        throw new InsufficientAuthorityException("权限不足");
                        System.out.println("权限不足");
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            //走到这里说明在方法和类上都没有权限注解，直接放行
            return true;
        }
    }

    //请求发送到Controller之后调用
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //完成请求的处理的回调方法
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
