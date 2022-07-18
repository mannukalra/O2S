package com.o2s.svc;

import org.springframework.stereotype.Service;

import com.o2s.dao.Environment;

@Service
public class EnvironmentSvc {
    
    public Environment createNewEnv(Long id, String name){
        return new Environment(id, name);
    }
}
