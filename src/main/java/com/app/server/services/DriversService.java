package com.app.server.services;

import com.app.server.models.Driver;
import com.app.server.util.MongoPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Services run as singletons
 */

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

    public ArrayList<Driver> getAll() {
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

    public Driver getOne(String id) {

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        Document item = driversCollection.find(query).first();
        if (item == null) {
            return  null;
        }
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
        return driver;
    }

    public Object create(JSONObject obj) {
        try {
            Document doc = new Document("firstName", obj.getString("firstName"))
                    .append("middleName", obj.getString("middleName"))
                    .append("lastName", obj.getString("lastName"))
                    .append("address1", obj.getString("address1"))
                    .append("address2", obj.getString("address2"))
                    .append("city", obj.getString("city"))
                    .append("state", obj.getString("state"))
                    .append("country", obj.getString("country"))
                    .append("postalCode", obj.getString("postalCode"));

            driversCollection.insertOne(doc);

        } catch(JSONException e) {
            System.out.println("Failed to create a document");

        }
        return obj;
    }


    public Object update(String id, JSONObject obj) {
        try {

            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));

            Document doc = new Document();
            if (obj.has("firstName"))
                doc.append("firstName",obj.getString("firstName"));
            if (obj.has("middleName"))
                doc.append("middleName",obj.getString("middleName"));
            if (obj.has("lastName"))
                doc.append("lastName",obj.getString("lastName"));
            if (obj.has("address1"))
                doc.append("address1",obj.getString("address1"));
            if (obj.has("address2"))
                doc.append("address2",obj.getString("address2"));
            if (obj.has("city"))
                doc.append("city",obj.getString("city"));
            if (obj.has("state"))
                doc.append("state",obj.getString("state"));
            if (obj.has("country"))
                doc.append("country",obj.getString("country"));
            if (obj.has("postalCode"))
                doc.append("postalCode",obj.getString("postalCode"));


            Document set = new Document("$set", doc);
            driversCollection.updateOne(query,set);

        } catch(JSONException e) {
            System.out.println("Failed to update a document");

        }
        return obj;
    }




    public Object delete(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        driversCollection.deleteOne(query);

        return new JSONObject();
    }




} // end of main()
