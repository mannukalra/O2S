package com.o2s.rest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.o2s.data.dto.MessageBean;

@Controller
public class SocketController {
    
    @MessageMapping("/cmd")
    @SendTo("/topic/allhosts")
    public MessageBean send(@Payload MessageBean message) {
        if("JOIN".equals(message.getStatus())){
            message.setStatus("MESSAGE");
            message.setMessage("Connected successfully!!");
        }
        return message;
    }
}
