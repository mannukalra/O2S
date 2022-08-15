package com.o2s.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.o2s.db.model.Environment;
import com.o2s.db.repo.EnvironmentRepository;

@Service
public class EnvironmentSvc {
    
    @Autowired
    EnvironmentRepository envRepo;

    public Environment addEnv(String name, String desc, Environment.Type type){
        var env = new Environment(name, desc, type);
        envRepo.save(env);
        return env;
    }

    public Environment addEnv(Environment env){
        envRepo.save(env);
        return env;
    }

    public List<Environment> getAllEnvs(){
        return envRepo.findAll();
    }

    public Environment getEnvById(Integer id){
        return envRepo.findById(id).get();
    }
}
