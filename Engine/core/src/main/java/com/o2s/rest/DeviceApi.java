package com.o2s.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.o2s.conn.SSHConnection;
import com.o2s.conn.WinRMConnection;
import com.o2s.data.dto.DeviceDto;
import com.o2s.svc.DeviceSvc;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/device")
public class DeviceApi {

    @Autowired
    DeviceSvc deviceSvc;

    @PostMapping(path = "/retrieve")
    public Mono<Map<String, String>> addEnv(@RequestBody DeviceDto device){
        String result = null;
        if("SSH".equalsIgnoreCase(device.getProtocol())){
            String os = new SSHConnection(device.getHost(), device.getUserName(), device.getPassword()).getOS();
            result = os; //TODO trigger discovery based on os
        }else{
            result = new WinRMConnection(device.getHost(), device.getUserName(), device.getPassword()).runCommand("(Get-WmiObject -class Win32_OperatingSystem).Caption");
        }
        return Mono.just(Map.of("result", result)).log();
    }
    
    @GetMapping(value = "/devices/{envId}")
    public Flux<DeviceDto> getDevicesByEnv(@PathVariable("envId") Integer envId) {
        return Flux.fromIterable(deviceSvc.getDevicesByEnvId(envId)).log();
    }

    @PostMapping(path = "/device")
    public Mono<Integer> addDevice(@RequestBody DeviceDto deviceDto){
        var addedDeviceId = deviceSvc.addDevice(deviceDto);
        return Mono.just(addedDeviceId).log();
    }
}
