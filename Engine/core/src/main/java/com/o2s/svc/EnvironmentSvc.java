package com.o2s.svc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2s.data.dto.EnvironmentDto;
import com.o2s.data.model.Device;
import com.o2s.data.model.Environment;
import com.o2s.data.repo.EnvironmentRepository;

@Service
public class EnvironmentSvc {
    
    @Autowired
    EnvironmentRepository envRepo;

    public Integer addEnv(EnvironmentDto envDto){
        var mapper = new ObjectMapper();    //TODO apt mapper usage and exception handling
        var targetEnv = mapper.convertValue(envDto, Environment.class);
        if(envDto.getDevices() != null){
            envDto.getDevices().forEach(deviceDto -> targetEnv.getDevices().add(mapper.convertValue(deviceDto, Device.class)) );
        }
        targetEnv.getDevices().forEach(device -> device.setEnvironment(targetEnv));
        var persistedEnv = envRepo.save(targetEnv);

        return persistedEnv.getId();
    }

    public List<EnvironmentDto> getAllEnvs(){
        var envs = envRepo.findAll();
        var resultEnvs = new ArrayList<EnvironmentDto>();
        var mapper = new ObjectMapper(); 
        for(var env : envs){
            var envDto = mapper.convertValue(env, EnvironmentDto.class);
            // envDto.getDevices().forEach(devDto -> devDto.setEnvId(env.getId()));
            resultEnvs.add(envDto);
        }
        return resultEnvs;
    }

    public EnvironmentDto getEnvById(Integer id){
        var env = envRepo.findById(id).get();
        var mapper = new ObjectMapper();
        return mapper.convertValue(env, EnvironmentDto.class);
    }
}
