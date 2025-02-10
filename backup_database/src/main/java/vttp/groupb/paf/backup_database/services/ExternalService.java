package vttp.groupb.paf.backup_database.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.groupb.paf.backup_database.models.Event;

@Service
public class ExternalService {
    
    public Optional<List<Event>> retrieveEvents() {
        
        String url = "https://ecommerce-app-production-5764.up.railway.app/api/popevents";

        RequestEntity<Void> req = RequestEntity
            .get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
            String payload = resp.getBody();
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject payloadObj = reader.readObject();

            if (payloadObj.getBoolean("hasEvents")) {

                List<Event> events = new ArrayList<>();

                JsonArray eventsArr = payloadObj.getJsonArray("events");

                for (JsonValue eventValue : eventsArr) {
                    JsonObject eventObj = eventValue.asJsonObject();
                    
                    Event event = new Event();
                    event.setEventId(eventObj.getString("eventId"));
                    event.setTimestamp(eventObj.getJsonNumber("timestamp").longValue());
                    event.setOperation(eventObj.getString("operation"));
                    event.setData(eventObj.getJsonObject("data"));
                    events.add(event);
                }

                return Optional.of(events);
            }

            return Optional.empty();
        }

        catch (Exception e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }
}
