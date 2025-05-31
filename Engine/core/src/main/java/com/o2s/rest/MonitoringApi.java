package com.o2s.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/monitor")
public class MonitoringApi {

    @GetMapping(value = "/test/{host}")
    public Mono<String> validateO2SAccess(@PathVariable("host") String host) {
        System.out.println("validateO2SAccess for host "+ host);
        return Mono.just("successo2s").log();
    }
    
}
