package vttp.batch5.paf.day27.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batch5.paf.day27.models.Event;
import vttp.batch5.paf.day27.repositories.EventStore;

@Service
public class EventStoreService {
    
    @Autowired
    private EventStore eventStore;

    public List<Event> popEvents() {
        return eventStore.popEvents();
    }
}
