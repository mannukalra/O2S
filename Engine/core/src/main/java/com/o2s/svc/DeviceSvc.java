package com.o2s.svc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2s.data.dto.DeviceDto;
import com.o2s.data.model.Device;
import com.o2s.data.repo.DeviceRepository;
import com.o2s.data.repo.EnvironmentRepository;

@Service
public class DeviceSvc {
    
    @Autowired
    DeviceRepository deviceRepo;

    @Autowired
    EnvironmentRepository envRepo;

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

    public Integer addDevice(DeviceDto deviceDto){
        var mapper = new ObjectMapper();    //TODO apt mapper usage and exception handling
        var targetDevice = mapper.convertValue(deviceDto, Device.class);

        var env = envRepo.findById(deviceDto.getEnvId()).get();

        targetDevice.setEnvironment(env);
        var persistedDevice = deviceRepo.save(targetDevice);

        return persistedDevice.getId();
    }

}
