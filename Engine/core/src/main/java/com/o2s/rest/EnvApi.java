package com.o2s.rest;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.http.MediaType;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.o2s.db.model.Environment;
import com.o2s.svc.EnvironmentSvc;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


// @CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/o2s")
public class EnvApi {

    @Autowired
    EnvironmentSvc envService;

    @GetMapping("/envs")
    public Flux<Environment> getEnvs(){
        return Flux.fromIterable(envService.getAllEnvs()).log();
    }

    @PostMapping(path = "/envs")
    public Mono<Environment> addEnv(@RequestBody Environment env){
        var addedEnv = envService.addEnv(env);
        return Mono.just(addedEnv).log();
    }

    @GetMapping(value = "/envs/{id}")
    public Mono<Environment> getEnv(@PathVariable("id") Integer id) {
        return Mono.just(envService.getEnvById(id));
    }
    
}
