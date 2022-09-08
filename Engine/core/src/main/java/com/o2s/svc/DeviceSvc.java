package com.o2s.svc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2s.data.dto.DeviceDto;
import com.o2s.data.repo.DeviceRepository;

@Service
public class DeviceSvc {
    
    @Autowired
    DeviceRepository deviceRepo;

    public List<DeviceDto> getDevicesByEnvId(Integer envId){
        var result = new ArrayList<DeviceDto>();
        var devices = deviceRepo.findAllByEnvId(envId);

        var mapper = new ObjectMapper();
        for(var device : devices){
            var devDto = mapper.convertValue(device, DeviceDto.class);
            devDto.setEnvId(envId);
            result.add(devDto);
        }
        return result;
    }

}
