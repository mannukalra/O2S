package com.o2s.rest;


import java.util.Map;

import javax.wsdl.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.o2s.conn.ConnectionFactory;
import com.o2s.conn.ex.AuthFailException;
import com.o2s.conn.ex.NonZeroExitStatusException;
import com.o2s.data.dto.DeviceDto;
import com.o2s.data.dto.Response;
import com.o2s.data.enm.Status;
import com.o2s.svc.AsyncLauncherSvc;
import com.o2s.svc.DeviceSvc;
import com.o2s.util.nashorn.NHEngine;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/device")
public class DeviceApi {

    @Autowired
    DeviceSvc deviceSvc;

    @Autowired
    AsyncLauncherSvc asyncLauncher;

    @PostMapping(path = "/retrieve")
    public Mono<Response> retrieveDevice(@RequestBody DeviceDto device){
        var response = new Response("success", "", device);
        String validationResult = null;
        try(var connection = ConnectionFactory.createConnection(device);){
            if(connection != null){
                validationResult = new NHEngine().discoverTypeAndValidate(connection, device, asyncLauncher);
            }else{
                // error while establishing connection
            }
        }catch(AuthFailException | NonZeroExitStatusException ex){
            response.setStatus("error");
            response.setMessage("Error while establishing connection, "+ex.getMessage());
        }catch(Exception ex){
            response.setStatus("error");
            response.setMessage("Error while establishing connection, "+ex.getMessage());
        }
        if(validationResult != null && validationResult.contains("successo2s"))
            device.setStatus(Status.HEALTHY);

        
        return Mono.just(response).log();
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

    @PostMapping(path = "/shell")
    public Mono<Response> addDevice(@RequestBody Map<String, String> cmdData){
        var response = new Response("success", "", null);
        String message = null;
        try(var connection = ConnectionFactory.createConnection(cmdData.get("host"), 
            cmdData.get("user"), cmdData.get("password"), cmdData.get("protocol"));){
            if(connection != null){
                message = connection.executeCommand(cmdData.get("command"), Boolean.valueOf(cmdData.get("isShell")));
            }else{
                // error while establishing connection
            }
        }catch(AuthFailException | NonZeroExitStatusException ex){
            response.setStatus("error");
            response.setMessage("Error while establishing connection, "+ex.getMessage());
        }catch(Exception ex){
            response.setStatus("error");
            response.setMessage("Error while establishing connection, "+ex.getMessage());
        }
        if(message != null)
            response.setMessage(message);
        return Mono.just(response).log();
    }
}
