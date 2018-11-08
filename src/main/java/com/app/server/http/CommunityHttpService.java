package com.app.server.http;

        import com.app.server.http.exceptions.APPInternalServerException;
        import com.app.server.http.exceptions.APPNotFoundException;
        import com.app.server.http.utils.APPResponse;
        import com.app.server.http.utils.PATCH;
        import com.app.server.models.Community;
        import com.app.server.services.CommunityService;
        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.fasterxml.jackson.databind.ObjectWriter;
        import org.json.JSONObject;

        import javax.annotation.security.PermitAll;
        import javax.ws.rs.*;
        import javax.ws.rs.core.MediaType;
        import javax.ws.rs.core.Response;
        import java.util.HashMap;

@Path("community")

public class CommunityHttpService {
    private CommunityService service;
    private ObjectWriter ow;

    public CommunityHttpService() {
        service = CommunityService.getInstance();
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
    @Path("{communityId}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getOne(@PathParam("communityId") String id) {
        try {
            Community d = service.getOne(id);
            if (d == null)
                throw new APPNotFoundException(56,"Connection not found");
            return new APPResponse(d);
        }
        catch(IllegalArgumentException e){
            throw new APPNotFoundException(56,"Connection not found");
        }
        catch (Exception e) {
            throw new APPInternalServerException(0,"Something happened. Come back later.");
        }
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse create(Object request) {

        return new APPResponse(service.create(request));
    }

    @PATCH
    @Path("{communityId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public APPResponse update(@PathParam("communityId") String id, Object request){

        return new APPResponse(service.update(id,request));

    }

    @DELETE
    @Path("{communityId}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse delete(@PathParam("communityId") String id) {

        return new APPResponse(service.delete(id));
    }

    @DELETE
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse delete() {

        return new APPResponse(service.deleteAll());
    }

}