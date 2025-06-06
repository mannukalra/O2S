package org.o2s.app.web;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/test")
public class TestController {

    @GET
    public Response resetCache() {
        var result = Map.of("testk1", "v1", "testk2", "v2", "testk3", "v3");
        return Response.ok().entity(result).build();
    }
}
