package com.o2s.svc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2s.data.dto.EnvironmentDto;
import com.o2s.data.model.Environment;
import com.o2s.data.repo.EnvironmentRepository;

@Service
public class EnvironmentSvc {
    
    @Autowired
    EnvironmentRepository envRepo;

    public Integer addEnv(EnvironmentDto envDto){
        var mapper = new ObjectMapper();    //TODO apt mapper usage
        var targetEnv = mapper.convertValue(envDto, Environment.class);    
        var persistedEnv = envRepo.save(targetEnv);

        return persistedEnv.getId();
    }

    public List<EnvironmentDto> getAllEnvs(){
        var envs = envRepo.findAll();
        var resultEnvs = new ArrayList<EnvironmentDto>();
        var mapper = new ObjectMapper(); 
        for(var env : envs){
            resultEnvs.add(mapper.convertValue(env, EnvironmentDto.class));
        }
        return resultEnvs;
    }

    public EnvironmentDto getEnvById(Integer id){
        var env = envRepo.findById(id).get();
        var mapper = new ObjectMapper();
        return mapper.convertValue(env, EnvironmentDto.class);
    }
}
