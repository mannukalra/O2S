package com.o2s.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.o2s.conn.Connection;
import com.o2s.conn.ConnectionFactory;
import com.o2s.conn.ex.AuthFailException;
import com.o2s.conn.ex.NonZeroExitStatusException;
import com.o2s.data.dto.MessageBean;
import com.o2s.data.repo.DeviceRepository;

@Controller
public class SocketController {

    @Autowired
    DeviceRepository deviceRepo;
    Map<String, Connection> connectionsMap = new HashMap<>();//TODO auto cleanup;
    
    @MessageMapping("/cmd")
    @SendTo("/topic/allhosts")
    public MessageBean send(@Payload MessageBean messageBean) {
        var conn = connectionsMap.get(messageBean.getHost());
        if("DISCONNECT".equals(messageBean.getStatus())){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            connectionsMap.remove(messageBean.getHost());
        }else {     
            if(conn == null || !conn.isConnected()){
                try {
                    conn = ConnectionFactory.createConnection(messageBean.getHost(), deviceRepo);
                    connectionsMap.put(messageBean.getHost(), conn);
                } catch (AuthFailException e) {
                    e.printStackTrace();
                }
            }
            
            if("JOIN".equals(messageBean.getStatus())){
                messageBean.setStatus("MESSAGE");
                messageBean.setMessage("Connected successfully!!");
            }else{
                try {
                    messageBean.setMessage(conn.executeCommand(messageBean.getMessage()));
                } catch (NonZeroExitStatusException e) {
                    e.printStackTrace();
                    messageBean.setMessage(e.getMessage());
                }
            }
        }
        return messageBean;
    }
}
