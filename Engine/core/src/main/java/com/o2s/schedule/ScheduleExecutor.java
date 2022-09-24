package com.o2s.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.o2s.conn.ConnectionFactory;
import com.o2s.conn.ex.AuthFailException;
import com.o2s.conn.ex.NonZeroExitStatusException;
import com.o2s.data.dto.DeviceDto;

public class ScheduleExecutor implements Runnable {
    
    public static Map<String, Set<String>> scheduledDevices = new HashMap<>();

    private DeviceDto device;
    
    public ScheduleExecutor(DeviceDto device){
        this.device = device;
    }

    private void executeScripts() {
        var scripts = scheduledDevices.get(device.getHost());
        if(scripts != null && !scripts.isEmpty()){
            try {
                var connection = ConnectionFactory.createConnection(device);
                var path = device.getBasePath()+"/";
                
                for(var script : scripts){
                    connection.executeScript(path+script, device.getType(), null);
                }
            } catch (AuthFailException | NonZeroExitStatusException e) {
                e.printStackTrace();
            }
           
        }   
    }
    
    @Override
    public void run() {
        executeScripts();
        System.out.println(new Date()+" Runnable Task with device "+device.getHost()
          +" on thread "+Thread.currentThread().getName());
    }
}
