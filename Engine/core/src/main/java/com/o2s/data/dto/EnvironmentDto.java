package com.o2s.data.dto;

import java.io.Serializable;
import java.util.List;

import com.o2s.data.enm.EnvironmentType;
import com.o2s.data.enm.Status;

import lombok.Data;

@Data
public class EnvironmentDto implements Serializable {

    private Integer id;
	private String name;
	private EnvironmentType type;
	private String description;
	private String country;
	private String state;
	private String city;
	private Status status;
	private List<DeviceDto> devices;
       
}
