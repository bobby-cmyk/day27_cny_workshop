package vttp.groupb.paf.backup_database.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.groupb.paf.backup_database.models.Event;
import vttp.groupb.paf.backup_database.repositories.EventStore;

@Service
public class EventStoreService {
    
    @Autowired
    private EventStore eventStore;

    public boolean pushEvent(Event event) {
        return eventStore.addEvent(event);
    }

    public List<Event> popEvents() {
        return eventStore.popEvents();
    }
}
