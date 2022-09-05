package com.o2s.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.o2s.conn.SSHConnection;
import com.o2s.conn.WinRMConnection;
import com.o2s.data.dto.DeviceDto;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/device")
public class DeviceApi {


    @PostMapping(path = "/retrieve")
    public Mono<Map<String, String>> addEnv(@RequestBody DeviceDto device){
        String result = null;
        if("SSH".equalsIgnoreCase(device.getProtocol())){
            result = new SSHConnection(device.getHost(), device.getUserName(), device.getPassword()).runCommand("cat /etc/os-release");
        }else{
            result = new WinRMConnection(device.getHost(), device.getUserName(), device.getPassword()).runCommand("[System.Environment]::OSVersion");
        }
        return Mono.just(Map.of("result", result)).log();
    }
    
}
