package com.cloneZjrt.webSocket;

import com.cloneZjrt.model.UserEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * websocket拦截器
 * 拦截握手前，握手后的两个切面
 * 目前暂做简单的拦截
 */
@Component
public class MyHandShakeInterceptor implements HandshakeInterceptor {

    /**
     * 握手之前，若返回false，则不建立链接
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        System.out.println("=====!!!!");
        if(serverHttpRequest instanceof ServletServerHttpRequest){
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if(null != session.getAttribute("user")){
                UserEntity user = (UserEntity) session.getAttribute("user");
                map.put("ws_user", user);
                System.out.println(user);
            }else{
                System.out.println("握手=======失败");
                return false;
            }
//            HttpServletRequest servletRequest = ((ServletServerHttpRequest)serverHttpRequest).getServletRequest();
//            User user = (User)servletRequest.getSession().getAttribute("user");
            //这里给map赋值 相当于websockethandler的afterConnectionEstablished方法里的WebSocketSession
            //key是session，value是变量
//            map.put("ws_user", user);
//            System.out.println(user);
        }
        System.out.println("--------------握手开始...");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("--------------握手成功啦...");
    }
}
