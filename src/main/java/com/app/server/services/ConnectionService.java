package com.app.server.services;

import com.app.server.http.utils.APPResponse;
import com.app.server.models.Connection;
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

public class ConnectionService {

    private static ConnectionService self;
    private ObjectWriter ow;
    private MongoCollection<Document> connectionCollection = null;

    private ConnectionService() {
        this.connectionCollection = MongoPool.getInstance().getCollection("connections");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    }

    public static ConnectionService getInstance(){
        if (self == null)
            self = new ConnectionService();
        return self;
    }

    public ArrayList<Connection> getAll() {
        ArrayList<Connection> connectionList = new ArrayList<Connection>();

        FindIterable<Document> results = this.connectionCollection.find();
        if (results == null) {
            return connectionList;
        }
        for (Document item : results) {
            Connection connection = convertDocumentToConnection(item);
            connectionList.add(connection);
        }
        return connectionList;
    }

    public Connection getOne(String id) {

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        Document item = connectionCollection.find(query).first();
        if (item == null) {
            return  null;
        }
        return  convertDocumentToConnection(item);
    }

    public Connection create(Object request) {

        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));
            Connection connection = convertJsonToConnection(json);
            Document doc = convertConnectionToDocument(connection);
            connectionCollection.insertOne(doc);
            ObjectId id = (ObjectId)doc.get( "_id" );
            connection.setId(id.toString());
            return connection;
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
            if (json.has("senderId"))
                doc.append("senderId",json.getString("senderId"));
            if (json.has("receiverId"))
                doc.append("receiverId",json.getString("receiverId"));
            if (json.has("connectionDate"))
                doc.append("connectionDate",json.getString("connectionDate"));
            if (json.has("connectionStatus"))
                doc.append("connectionStatus",json.getBoolean("connectionStatus"));
            if (json.has("connectionType"))
                doc.append("connectionType",json.getString("connectionType"));

            Document set = new Document("$set", doc);
            connectionCollection.updateOne(query,set);
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

        connectionCollection.deleteOne(query);

        return new JSONObject();
    }


    public Object deleteAll() {

        connectionCollection.deleteMany(new BasicDBObject());

        return new JSONObject();
    }

    private Connection convertDocumentToConnection(Document item) {
        Connection connection = new Connection(
                item.getString("senderId"),
                item.getString("receiverId"),
                item.getString("connectionDate"),
                item.getBoolean("connectionStatus"),
                item.getString("connectionType")
        );
        connection.setId(item.getObjectId("_id").toString());
        return connection;
    }

    private Document convertConnectionToDocument(Connection connection){
        Document doc = new Document("senderId", connection.getsenderId())
                .append("receiverId", connection.getreceiverId())
                .append("connectionDate", connection.getconnectionDate())
                .append("connectionStatus", connection.getconnectionStatus())
                .append("connectionType", connection.getconnectionType());
        return doc;
    }

    private Connection convertJsonToConnection(JSONObject json){
        Connection connection = new Connection( json.getString("senderId"),
                json.getString("receiverId"),
                json.getString("connectionDate"),
                json.getBoolean("connectionStatus"),
                json.getString("connectionType"));
        return connection;
    }
} // end of main()
