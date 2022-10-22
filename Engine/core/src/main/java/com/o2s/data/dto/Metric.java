package com.o2s.data.dto;


import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Metric {
    String id;
    String type;
    List<String> fields;
    int interval;

    public Metric(String id, String type, int interval) {
        this.id = id;
        this.type = type;
        this.interval = interval;
        this.fields = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Metric other = (Metric) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    
    
}