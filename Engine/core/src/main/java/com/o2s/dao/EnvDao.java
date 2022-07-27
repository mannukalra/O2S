package com.o2s.dao;

import lombok.Data;

@Data
public class EnvDao {

    Integer id;
    String name;
    String description;

    public EnvDao(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
   
}
