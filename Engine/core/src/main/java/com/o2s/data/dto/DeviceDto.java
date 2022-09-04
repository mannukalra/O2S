package com.o2s.data.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class DeviceDto implements Serializable {
    private Integer id;
    private Integer envId;
    private String host;
    private Integer port;
    private String alias;
    private String userName;
    private String password;
    private String os;
    private String protocol;
    private Map<String, String> props;
       
}
