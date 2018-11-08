package com.app.server.services;

import com.app.server.http.utils.APPResponse;
import com.app.server.models.Community;
import com.app.server.util.MongoPool;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.HashMap;

/**
 * Services run as singletons
 */

public class CommunityService {

    private static CommunityService self;
    private ObjectWriter ow;
    private MongoCollection<Document> communityCollection = null;

    private CommunityService() {
        this.communityCollection = MongoPool.getInstance().getCollection("community");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    }

    public static CommunityService getInstance(){
        if (self == null)
            self = new CommunityService();
        return self;
    }

    public ArrayList<Community> getAll() {
        ArrayList<Community> communityList = new ArrayList<Community>();

        FindIterable<Document> results = this.communityCollection.find();
        if (results == null) {
            return communityList;
        }
        for (Document item : results) {
            Community community = convertDocumentToCommunity(item);
            communityList.add(community);
        }
        return communityList;
    }

    public Community getOne(String id) {

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        Document item = communityCollection.find(query).first();
        if (item == null) {
            return  null;
        }
        return  convertDocumentToCommunity(item);
    }

    public Community create(Object request) {

        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));
            Community community = convertJsonToCommunity(json);
            Document doc = convertCommunityToDocument(community);
            communityCollection.insertOne(doc);
            ObjectId id = (ObjectId)doc.get( "_id" );
            community.setId(id.toString());
            return community;
        } catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        }
    }


    public Object update(String id, Object request) {
        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));

            Document doc = new Document();
            if (json.has("managerId"))
                doc.append("managerId",json.getString("managerId"));
            if (json.has("communityName"))
                doc.append("communityName",json.getString("communityName"));
            if (json.has("communityDescription"))
                doc.append("communityDescription",json.getString("communityDescription"));
            if (json.has("createdDate"))
                doc.append("createdDate",json.getString("createdDate"));

            Document set = new Document("$set", doc);
            communityCollection.updateOne(query,set);
            return request;

        } catch(JSONException e) {
            System.out.println("Failed to update a document");
            return null;


        }
        catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        }
    }




    public Object delete(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        communityCollection.deleteOne(query);

        return new JSONObject();
    }


    public Object deleteAll() {

        communityCollection.deleteMany(new BasicDBObject());

        return new JSONObject();
    }

    private Community convertDocumentToCommunity(Document item) {
        Community connection = new Community(
                item.getString("managerId"),
                item.getString("communityName"),
                item.getString("communityDescription"),
                item.getString("createdDate")
        );
        connection.setId(item.getObjectId("_id").toString());
        return connection;
    }

    private Document convertCommunityToDocument(Community community){
        Document doc = new Document("managerId", community.getmanagerId())
                .append("communityName", community.getcommunityName())
                .append("communityDescription", community.getcommunityDescription())
                .append("createdDate", community.getcreatedDate());
        return doc;
    }

    private Community convertJsonToCommunity(JSONObject json){
        Community community = new Community( json.getString("managerId"),
                json.getString("communityName"),
                json.getString("communityDescription"),
                json.getString("createdDate"));
        return community;
    }
} // end of main()
