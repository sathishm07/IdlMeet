package com.app.server.http;

import com.app.server.http.utils.APPResponse;
import com.app.server.models.Driver;
import com.app.server.services.DriversService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.bson.Document;
import org.json.JSONObject;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("drivers")

public class DriversHttpService {
    private DriversService service;
    private ObjectWriter ow;


    public DriversHttpService() {
        service = DriversService.getInstance();
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    }

    @OPTIONS
    @PermitAll
    public Response optionsById() {
        return Response.ok().build();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getAll() {

        return new APPResponse(service.getAll());
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getOne(@PathParam("id") String id) {

        return new APPResponse(service.getOne(id));
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse create(Object request) {
        JSONObject json = null;
        Driver driver;
        try{
            json = new JSONObject(ow.writeValueAsString(request));
            driver = new Driver( json.getString("firstName"),
                    json.getString("middleName"),
                    json.getString("lastName"),
                    json.getString("address1"),
                    json.getString("address2"),
                    json.getString("city"),
                    json.getString("state"),
                    json.getString("country"),
                    json.getString("postalCode"));
            return new APPResponse(service.create(driver));

        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return new APPResponse();
    }

    @DELETE
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse delete(@PathParam("id") String id) {

        return new APPResponse(service.delete(id));
    }




}
