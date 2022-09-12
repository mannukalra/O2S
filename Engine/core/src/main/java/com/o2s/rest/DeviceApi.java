package com.o2s.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.o2s.conn.ConnectionFactory;
import com.o2s.data.dto.DeviceDto;
import com.o2s.data.enm.Status;
import com.o2s.svc.DeviceSvc;
import com.o2s.util.nashorn.NHEngine;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/device")
public class DeviceApi {

    @Autowired
    DeviceSvc deviceSvc;

    @PostMapping(path = "/retrieve")
    public Mono<DeviceDto> retrieveDevice(@RequestBody DeviceDto device){
        String validationStatus = null;
        try(var connection = ConnectionFactory.createConnection(device);){
            if(connection != null){
                validationStatus = new NHEngine().discoverTypeAndValidate(connection, device);
            }else{
                // error while establishing connection
            }
        }catch(Exception ex){
            ex.printStackTrace();//
        }
        if("success".equals(validationStatus))
            device.setStatus(Status.HEALTHY);

        return Mono.just(device).log();
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
