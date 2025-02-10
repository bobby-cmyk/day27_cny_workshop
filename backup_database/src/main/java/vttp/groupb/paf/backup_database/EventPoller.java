package vttp.groupb.paf.backup_database;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vttp.groupb.paf.backup_database.models.Event;
import vttp.groupb.paf.backup_database.services.EventStoreService;
import vttp.groupb.paf.backup_database.services.ExternalService;

@Component
public class EventPoller {
    
    @Autowired
    private EventStoreService eventStoreSvc;

    @Autowired
    private ExternalService externalSvc;

    @Scheduled(fixedRate = 15000)
    public void pollEvents() {
        Optional<List<Event>> opt = externalSvc.retrieveEvents();

        if (opt.isPresent() && !opt.get().isEmpty()) {
            List<Event> events = opt.get();
            for (Event event : events) {
                eventStoreSvc.pushEvent(event);
                System.out.printf(">>> Event push into Redis: %s\n", event);
            }
        
        }

        System.out.printf(">>> No new events\n");
    }
}
