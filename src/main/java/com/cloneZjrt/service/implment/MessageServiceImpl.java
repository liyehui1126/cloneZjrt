package com.cloneZjrt.service.implment;

import com.cloneZjrt.dao.MessageDao;
import com.cloneZjrt.model.MessageEntity;
import com.cloneZjrt.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageDao messageDAO;

    @Override
    public int addMessage(MessageEntity messageEntity) {
        return messageDAO.addMessage(messageEntity);
    }
}
