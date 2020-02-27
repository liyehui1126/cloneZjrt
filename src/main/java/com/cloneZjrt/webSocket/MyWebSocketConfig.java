package com.cloneZjrt.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Component
@EnableWebSocket
//@Configuration
public class MyWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Autowired
    private MyWebSocketHander myWebSocketHander;

    // websocket 处理类
//    @Bean
//    public MyWebSocketHander myWebSocketHandler(){
//        return new MyWebSocketHander();
//    }

    private static final String LINK_URI = "websocket.do";
    //添加websocket处理器，添加握手拦截器  拦截器先执行 然后到处理器
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myWebSocketHander,LINK_URI).addInterceptors(new MyHandShakeInterceptor());
    }
}
