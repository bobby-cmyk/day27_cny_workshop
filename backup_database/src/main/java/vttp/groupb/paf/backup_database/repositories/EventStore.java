package vttp.groupb.paf.backup_database.repositories;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.groupb.paf.backup_database.models.Event;

@Repository
public class EventStore {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // lpush eventStore poJsonString
    public boolean addEvent(Event event) {

        try {
            redisTemplate.opsForList().leftPush("eventStore", event.toJsonObject().toString());

            return true;
        }

        catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    // brpop eventStore
    public List<Event> popEvents() {

        List<Event> events = new ArrayList<>();

        while (true) {
            Optional<String> opt = Optional.ofNullable(redisTemplate.opsForList().rightPop("eventStore"));

            if (opt.isPresent()) {
                String eventString = opt.get();
                
                JsonReader reader = Json.createReader(new StringReader(eventString));
                JsonObject eventObj = reader.readObject();

                Event event = new Event();
                event.setEventId(eventObj.getString("eventId"));
                event.setTimestamp(eventObj.getJsonNumber("timestamp").longValue());
                event.setOperation(eventObj.getString("operation"));
                event.setData(eventObj.getJsonObject("data"));
                events.add(event);

                continue;
            }

            break;
        }

        return events;
    }
}
