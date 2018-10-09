package com.app.server.services;

import com.app.server.models.Driver;
import com.app.server.util.MongoPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;

public class DriversService {

    private static DriversService self;
    private MongoCollection<Document> driversCollection = null;

    private DriversService() {
        this.driversCollection = MongoPool.getInstance().getCollection("drivers");
    }

    public static DriversService getInstance(){
        if (self == null)
            self = new DriversService();
        return self;
    }

    public ArrayList getAll() {

        ArrayList<Driver> driverList = new ArrayList<Driver>();

        FindIterable<Document> results = this.driversCollection.find();
        if (results == null) {
            return driverList;
        }
        for (Document item : results) {
            Driver driver = new Driver(
                    item.getString("firstName"),
                    item.getString("middleName"),
                    item.getString("lastName"),
                    item.getString("address1"),
                    item.getString("address2"),
                    item.getString("city"),
                    item.getString("state"),
                    item.getString("country"),
                    item.getString("postalCode")
            );
            driver.setId(item.getObjectId("_id").toString());
            driverList.add(driver);
        }
        return driverList;
    }

} // end of main()
