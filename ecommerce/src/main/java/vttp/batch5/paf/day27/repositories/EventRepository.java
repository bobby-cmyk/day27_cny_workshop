package vttp.batch5.paf.day27.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import vttp.batch5.paf.day27.models.Event;

@Repository
public class EventRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    // db.events.insert
    public boolean addEvent(Event event) {
        
        Document eventDoc = eventObjToDoc(event);

        try {
            mongoTemplate.insert(eventDoc, "events");

            return true;
        }
        catch(Exception e) {
            e.printStackTrace();

            return false;
        }
    } 

    private Document eventObjToDoc(Event event) {
        Document eventDoc = new Document();

        eventDoc.put("eventId", event.getEventId());
        eventDoc.put("timestamp", event.getTimestamp());
        eventDoc.put("operation", event.getOperation());
        eventDoc.put("data", cleanJson(event.getData()));

        return eventDoc;
    }


    private static Map<String, Object> cleanJson(JsonObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            JsonValue value = jsonObject.get(key);
            map.put(key, extractValue(value));
        }
        return map;
    }

    private static Object extractValue(JsonValue value) {
        switch (value.getValueType()) {
            case OBJECT:
                return cleanJson(value.asJsonObject()); // Recursively clean objects
            case ARRAY:
                List<Object> list = new ArrayList<>();
                for (JsonValue val : value.asJsonArray()) {
                    list.add(extractValue(val));
                }
                return list;
            case STRING:
                return ((JsonString) value).getString(); // Extract raw string
            case NUMBER:
                JsonNumber num = (JsonNumber) value;
                return num.isIntegral() ? num.longValue() : num.doubleValue(); // Convert to int or double
            case TRUE:
                return true;
            case FALSE:
                return false;
            case NULL:
                return null;
            default:
                return value.toString(); // Fallback
        }
    }
}

