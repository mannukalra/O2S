package com.o2s.data.dto;

import lombok.Data;

@Data
public class Monitor {
    String id;
    String type;
    int interval;

    public Monitor(String id, String type, int interval) {
        this.id = id;
        this.type = type;
        this.interval = interval;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Monitor other = (Monitor) obj;
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
