package com.o2s.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.o2s.conn.ConnectionFactory;
import com.o2s.conn.ex.AuthFailException;
import com.o2s.conn.ex.NonZeroExitStatusException;
import com.o2s.data.dto.DeviceDto;
import com.o2s.data.dto.Monitor;

public class ScheduleExecutor implements Runnable {
    
    public static Map<String, Set<Monitor>> scheduledDevices = new HashMap<>();
    
    private int counter;
    private DeviceDto device;
    
    public ScheduleExecutor(DeviceDto device){
        this.device = device;
    }

    private void executeScripts() {
        var deployedMonitors = scheduledDevices.get(device.getHost());
        if(deployedMonitors != null && !deployedMonitors.isEmpty()){
            try {
                var connection = ConnectionFactory.createConnection(device);
                var path = device.getBasePath()+"/";
                
                for(var monitor : deployedMonitors){
                    if(counter % monitor.getInterval() == 0)
                        connection.executeScript(path+(monitor.getId()), device.getType(), null);
                }
            } catch (AuthFailException | NonZeroExitStatusException e) {
                e.printStackTrace();
            }
           
        }   
    }
    
    @Override
    public void run() {
        executeScripts();
        System.out.println(new Date()+" Runnable Task with device "+device.getHost() +" on thread "+Thread.currentThread().getName());

        counter++;
        if(counter == 10000)
            counter = 0;
    }
}
