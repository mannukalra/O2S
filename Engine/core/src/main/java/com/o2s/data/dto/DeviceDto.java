package com.o2s.data.dto;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DeviceDto implements Serializable {
    private Integer id;
    private String host;
    private Integer port;
    private String alias;
    private String userName;
    private String password;
    private String os;
    private String protocol;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer envId;
    
    private Map<String, String> props;
       
}
