package com.o2s.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.o2s.dao.Environment;
import com.o2s.svc.EnvironmentSvc;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/envs")
public class EnvApi {

    @Autowired
    EnvironmentSvc envService;

    @GetMapping("/all")
    public Flux<Environment> getAllEnvs(){
        List<Environment> envs = new ArrayList<>();
        envs.add(envService.createNewEnv(1l, "Env1"));
        envs.add(envService.createNewEnv(2l, "Env2"));
        return Flux.fromIterable(envs).log();
    }
    
}
