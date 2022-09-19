package com.o2s.data.dto;

import lombok.Data;

@Data
public class Response {
    String status;
    String message;
    Object dto;

    public Response(String status, String message, Object dto) {
        this.status = status;
        this.message = message;
        this.dto = dto;
    }

}
