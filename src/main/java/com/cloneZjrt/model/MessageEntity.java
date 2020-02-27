package com.cloneZjrt.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class MessageEntity {

    private Long id;

    private Long send_user_id;

    private String  chatDate;

    private String value;

    private Long receive_user_id;

    private String msgType;

    public MessageEntity(Long send_user_id, String chatDate, String value, Long receive_user_id, String msgType){
        this.send_user_id = send_user_id;
        this.chatDate = chatDate;
        this.value = value;
        this.receive_user_id = receive_user_id;
        this.msgType = msgType;
    }

    public MessageEntity(){

    }
}
