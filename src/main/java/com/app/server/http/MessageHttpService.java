
package com.app.server.http;

import com.app.server.http.exceptions.APPInternalServerException;
import com.app.server.http.exceptions.APPNotFoundException;
import com.app.server.http.utils.APPResponse;
import com.app.server.http.utils.PATCH;
import com.app.server.models.Message;
import com.app.server.services.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("/connections/{connectionId}/messages")

public class MessageHttpService {
    private MessageService messageservice;
    private ObjectWriter owms;


    public MessageHttpService() {
        messageservice = MessageService.getInstance();
        owms = new ObjectMapper().writer().withDefaultPrettyPrinter();

    }

    @OPTIONS
    @PermitAll
    public Response optionsById() {
        return Response.ok().build();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getAllMessages(@PathParam("connectionId") String id)
    {
        return new APPResponse(messageservice.getAll(id));
    }


    @GET
    @Path("{messageId}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getOneMessages(@PathParam("messageId") String id)
    {
        try {
            Message d = messageservice.getOne(id);
            if (d == null)
                throw new APPNotFoundException(56,"Message not found");
            return new APPResponse(d);
        }
        catch(IllegalArgumentException e){
            throw new APPNotFoundException(56,"Message not found");
        }
        catch (Exception e) {
            throw new APPInternalServerException(0,"Something happened. Come back later.");
        }
    }



    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse create(@PathParam("connectionId") String connectionId, Object request) {

        return new APPResponse(messageservice.create(connectionId, request));
    }

    @PATCH
    @Path("{messageId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public APPResponse update(@PathParam("messageId") String id, @PathParam("connectionId") String connectionId, Object request){

        return new APPResponse(messageservice.update(id,connectionId,request));

    }

    @DELETE
    @Path("{messageId}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse delete(@PathParam("messageId") String id, @PathParam("connectionId") String connectionId) {

        return new APPResponse(messageservice.delete(id, connectionId));
    }

    @DELETE
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse delete(@PathParam("connectionId") String connectionId) {

        return new APPResponse(messageservice.deleteAll(connectionId));
    }

}
