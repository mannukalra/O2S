package com.o2s.conn;

import com.o2s.data.dto.DeviceDto;
import com.o2s.data.model.Device;

public class ConnectionFactory {
    
    public static Connection createConnection(String host, String user, String password, String protocol){
        Connection conn = null;
        if("SSH".equalsIgnoreCase(protocol))
            conn = new SSHConnection(host, user, password);
        else if("WINRM".equalsIgnoreCase(protocol))
            conn = new WinRMConnection(host, user, password);

        return conn;
    }

    public static Connection createConnection(DeviceDto device){
        return createConnection(device.getHost(), device.getUser(), device.getPassword(), device.getProtocol());
    }

    public static Connection createConnection(Device device){
        return createConnection(device.getHost(), device.getUser(), device.getPassword(), device.getProtocol());
    }

}

