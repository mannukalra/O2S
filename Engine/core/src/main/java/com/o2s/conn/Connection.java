package com.o2s.conn;

import com.o2s.data.dto.DeviceDto;

public interface Connection {
    
    String runCommand(String cmd);

    void discoverOS(DeviceDto device);

    void configureBasePath(DeviceDto device);

}
