package com.o2s.dao;

import lombok.Data;

@Data
public class Environment {

    Long id;
    String name;

    public Environment(Long id, String name) {
        this.id = id;
        this.name = name;
    }
   
}
