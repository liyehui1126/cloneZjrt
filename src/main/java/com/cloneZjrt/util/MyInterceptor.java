package com.cloneZjrt.util;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;

public class MyInterceptor implements HandlerInterceptor {

    //请求发送到Controller之前调用
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

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

        String token = httpServletRequest.getHeader("token");

        if(token.equals("test")){
            return true;
        }

        return false;
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
