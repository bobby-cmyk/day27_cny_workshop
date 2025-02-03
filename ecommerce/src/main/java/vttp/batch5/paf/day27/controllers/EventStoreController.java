package vttp.batch5.paf.day27.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.batch5.paf.day27.models.Event;
import vttp.batch5.paf.day27.services.EventStoreService;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventStoreController {

    @Autowired
    private EventStoreService eventStoreSvc;
    
    @GetMapping(path="/popevents") 
    public ResponseEntity<String> popEvents() {

        List<Event> events = eventStoreSvc.popEvents();

        if (events.isEmpty()) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder();
            objBuilder.add("hasEvents", false);
            objBuilder.add("message", "No new events");
            JsonObject jsonBody = objBuilder.build();
            return ResponseEntity.ok().body(jsonBody.toString());
        }

        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        objBuilder.add("hasEvents", true);
        
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Event event : events) {
            arrBuilder.add(event.toJsonObject());
        }

        objBuilder.add("events", arrBuilder.build());
        JsonObject jsonBody = objBuilder.build();
        return ResponseEntity.ok().body(jsonBody.toString());
    }
}
