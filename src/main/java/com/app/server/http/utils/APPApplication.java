package com.app.server.http.utils;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//@ApplicationPath("/")

@Path("")

public class APPApplication extends ResourceConfig {

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }
    public APPApplication() {
//        MeteringService.getInstance();
        System.out.println("Application Instantiated");
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public Object getAll() {
        Version ver = new Version("0.2.37", "2018-08-10");
        return ver;
    }

    public class Version {
        String version, date;
        public Version(String version,String date) {
            this.version = version;
            this.date = date;
        }

    }
}