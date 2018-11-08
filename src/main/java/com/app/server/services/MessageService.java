package com.app.server.services;

import com.app.server.http.utils.APPResponse;
import com.app.server.models.Message;
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

public class MessageService {

    private static MessageService self;
    private ObjectWriter ow;
    private MongoCollection<Document> messageCollection = null;

    private MessageService() {
        this.messageCollection = MongoPool.getInstance().getCollection("messages");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    }

    public static MessageService getInstance(){
        if (self == null)
            self = new MessageService();
        return self;
    }

    public ArrayList<Message> getAll(String id) {
        ArrayList<Message> messageList = new ArrayList<Message>();

        BasicDBObject query = new BasicDBObject();
        query.put("connectionId", id);

        FindIterable<Document> results = this.messageCollection.find(query);
        if (results == null) {
            return messageList;
        }
        for (Document item : results) {
            Message message = convertDocumentToMessage(item);
            messageList.add(message);
        }
        return messageList;
    }

    public Message getOne(String id) {

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        Document item = messageCollection.find(query).first();
        if (item == null) {
            return  null;
        }
        return  convertDocumentToMessage(item);
    }

    public Message create(String connectionId, Object request) {

        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));
            Message message = convertJsonToMessage(json, connectionId);
            Document doc = convertMessageToDocument(message, connectionId);
            messageCollection.insertOne(doc);
            ObjectId id = (ObjectId)doc.get( "_id" );
            message.setId(id.toString());
            message.setConnectionId(connectionId);
            return message;
        } catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        }
    }


    public Object update(String id, String connectionId, Object request) {
        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));
            query.put("connectionId", new ObjectId(connectionId));

            Document doc = new Document();
            if (json.has("senderId"))
                doc.append("senderId",json.getString("senderId"));
            if (json.has("receiverId"))
                doc.append("receiverId",json.getString("receiverId"));
            if (json.has("messageContent"))
                doc.append("messageContent",json.getString("messageContent"));
            if (json.has("messageDate"))
                doc.append("messageDate",json.getString("messageDate"));
            if (json.has("messageStatus"))
                doc.append("messageStatus",json.getBoolean("messageStatus"));

            Document set = new Document("$set", doc);
            messageCollection.updateOne(query,set);
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




    public Object delete(String id, String connectionId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        query.put("connectionId", new ObjectId(connectionId));

        messageCollection.deleteOne(query);

        return new JSONObject();
    }


    public Object deleteAll(String connectionId) {
        BasicDBObject query = new BasicDBObject();
        query.put("connectionId", new ObjectId(connectionId));

        messageCollection.deleteMany(new BasicDBObject());

        return new JSONObject();
    }

    private Message convertDocumentToMessage(Document item) {
        Message message = new Message(
                item.getString("connectionId"),
                item.getString("senderId"),
                item.getString("receiverId"),
                item.getString("messageContent"),
                item.getString("messageDate"),
                item.getBoolean("messageStatus")
        );
        message.setId(item.getObjectId("_id").toString());
        return message;
    }

    private Document convertMessageToDocument(Message message, String connectionId){
        Document doc = new Document("connectionId", message.getconnectionId())
                .append("senderId", message.getsenderId())
                .append("receiverId", message.getreceiverId())
                .append("messageContent", message.getmessageContent())
                .append("messageDate", message.getmessageDate())
                .append("messageStatus", message.getmessageStatus());
        return doc;
    }

    private Message convertJsonToMessage(JSONObject json, String connectionId){
        Message message = new Message( connectionId,
                json.getString("senderId"),
                json.getString("receiverId"),
                json.getString("messageContent"),
                json.getString("messageDate"),
                json.getBoolean("messageStatus"));
        return message;
    }
} // end of main()