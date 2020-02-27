package com.cloneZjrt.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloneZjrt.model.UserEntity;
import com.cloneZjrt.model.MessageEntity;
import com.cloneZjrt.service.MessageService;
import com.cloneZjrt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MyWebSocketHander implements WebSocketHandler {

    //在线用户的SOCKETsession(存储了所有的通信通道)
    public static final Map<UserEntity, WebSocketSession> USER_SOCKETSESSION_MAP;

    //存储所有的在线用户
    static {
        USER_SOCKETSESSION_MAP = new HashMap<UserEntity, WebSocketSession>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

        //这里的"ws_user"对应HandshakeInterceptor中配置的
        UserEntity user = (UserEntity) webSocketSession.getAttributes().get("ws_user");
        USER_SOCKETSESSION_MAP.put(user,webSocketSession);

        //这里传到前端的应该是JSON格式
        String messageFormat = "{onlineNum:\"%d\",userName:\"%s\" , msgTyp " + ":\"%s\"}";
        String msg = String.format(messageFormat, USER_SOCKETSESSION_MAP.size(), USER_SOCKETSESSION_MAP.keySet().toString(), "notice");

        TextMessage testMsg = new TextMessage(msg + "");

        sendMessageToAll(testMsg);
    }

    /**
     * 群发信息：给所有在线用户发送消息
     * @param message
     */
    private void sendMessageToAll(final TextMessage message){
        //对用户发送的消息内容进行转义

        //获取到所有在线用户的SocketSession对象
        Set<Map.Entry<UserEntity, WebSocketSession>> entrySet = USER_SOCKETSESSION_MAP.entrySet();
        for (Map.Entry<UserEntity, WebSocketSession> entry : entrySet) {
            //某用户的WebSocketSession
            final WebSocketSession webSocketSession = entry.getValue();
            //判断连接是否仍然打开的
            if(webSocketSession.isOpen()){
                //开启多线程发送消息（效率高）
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            if (webSocketSession.isOpen()) {
                                webSocketSession.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }

    /**
     *
     * 说明：给某个人发信息
     * @param UserEntity
     * @param message
     */
    private void sendMessageToUser(UserEntity UserEntity, TextMessage message) throws IOException{
        //获取到要接收消息的用户的session
        WebSocketSession webSocketSession = USER_SOCKETSESSION_MAP.get(UserEntity);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            //发送消息
            webSocketSession.sendMessage(message);
        }
    }

    /**
     * 客户端发送服务器的消息时的处理函数，在这里收到消息之后可以分发消息
     */
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void handleMessage(WebSocketSession webSocketSession,
                              WebSocketMessage<?> webSocketMessage) throws Exception {

        String messageFormat = null;
        FileOutputStream output;

        System.out.println(webSocketSession.getAttributes().get("ws_user"));
        //发送消息的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sendMsgDate = dateFormat.format(new Date());

        UserEntity user = (UserEntity) webSocketSession.getAttributes().get("ws_user");

        String msgContent = webSocketMessage.getPayload() + "";
        System.out.println("前端传到后台的数据 " + msgContent);
        JSONObject chat = JSON.parseObject(msgContent);
        //消息的内容
        String msgJSON = chat.get("message").toString();
        //消息接收者名字
        String receiveUserName = chat.get("receive").toString();
        //消息的样式
        String msgJSONType = chat.get("type").toString();
        String exit = "exit";
        String MessageEntity = "MessageEntity";
        String img = "img";

        System.out.println("JSON验证" + chat);
        System.out.println(chat.get("type").toString());

        if (msgJSONType.equals(exit)) {
            messageFormat = "{onlineNum:\"%d\",userName:\"%s\" ,userNameList:\"%s\", msgTyp:\"%s\"}";
            //从用户列表中移除已退出的用户
            USER_SOCKETSESSION_MAP.remove(user);

            String msg = String.format(messageFormat, USER_SOCKETSESSION_MAP.size()-1 ,msgJSON ,USER_SOCKETSESSION_MAP.keySet().toString(),"exit");
            TextMessage testMsg = new TextMessage(msg + "");
            sendMessageToAll(testMsg);

        } else if (msgJSONType.equals(MessageEntity)) {

            MessageEntity messageEntity = new MessageEntity(user.getUserId(), sendMsgDate, msgContent, user.getUserId(), "msg");
            messageService.addMessage(messageEntity);

//            messageFormat = "{UserEntity:\"%s\",sendDate:\"%s\" ," + "sendContent:\"%s\" , msgTyp :\"%s\"}";
//            String message = String.format(messageFormat, UserEntity.getUserName(), sentMsgDate, msgJSON , "msg");
//            TextMessage toMsg = new TextMessage(message + "");
//            //遍历所有的用户，发信息，这个要注意哦，要不然不能做到多人同时聊天
//            sendMessageToAll(toMsg);

            messageFormat = "{UserEntity:\"%s\",sendDate:\"%s\" ," + "sendContent:\"%s\" , msgTyp :\"%s\"}";

            if(!receiveUserName.equals("") || receiveUserName!=null){

                String message = String.format(messageFormat, user.getUserName(), sendMsgDate, msgJSON , "msg");
                TextMessage testMsg = new TextMessage(message + "");
                sendMessageToUser(userService.getUserByName(receiveUserName),testMsg);
                if(!receiveUserName.equals(user.getUserName())){
                    sendMessageToUser(user,testMsg);
                }

            }else {

                String message = String.format(messageFormat, user.getUserName(), sendMsgDate, msgJSON , "msg");
                TextMessage testMsg = new TextMessage(message + "");
                //确保每个用户信息都能同步到
                sendMessageToAll(testMsg);
            }
        }else if(msgJSONType.equals(img)){
            System.out.println("send pic");
            //设置图片保存路径
            output = new FileOutputStream(new File("D:\\images\\"+chat.get("filename").toString().split(":")[0]));
            System.out.println("图片路径"+"D:\\images\\"+chat.get("filename").toString().split(":")[0]);
            output.close();
        }

    }


    @Override
    public void handleTransportError(WebSocketSession webSocketSession,
                                     Throwable throwable) throws Exception {

        // 记录日志，准备关闭连接
        System.out.println("Websocket异常断开:" + webSocketSession.getId() + "已经关闭");

        //一旦发生异常，强制用户下线，关闭session
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }

//      msg.setText("万众瞩目的【"+loginUser.getNickname()+"】已经退出。。。！");

        UserEntity user = (UserEntity) webSocketSession.getAttributes().get("ws_user");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sendMsgDate = dateFormat.format(new Date());


        String messageFormat = "{UserEntity:\"%s\",sendDate:\"%s\" ," + "sendContent:\"%s\" , msgTyp :\"%s\"}";
        String message = String.format(messageFormat, user.getUserName(), sendMsgDate, "万众瞩目的【"+user.getUserName()+"】退出了群聊！" , "msg");

        System.out.println(message);

        TextMessage testMsg = new TextMessage(message + "");
        //确保每个用户信息都能同步到
        sendMessageToAll(testMsg);
        Collection<WebSocketSession> values = USER_SOCKETSESSION_MAP.values();
        values.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession,
                                      CloseStatus closeStatus) throws Exception {
        // 记录日志，准备关闭连接
        System.out.println("Websocket正常断开:" + webSocketSession.getId() + "已经关闭");

        UserEntity userRemove = (UserEntity) webSocketSession.getAttributes().get("ws_user");
        USER_SOCKETSESSION_MAP.remove(userRemove);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
