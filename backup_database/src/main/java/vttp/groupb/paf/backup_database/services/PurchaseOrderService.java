package vttp.groupb.paf.backup_database.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import vttp.groupb.paf.backup_database.models.Event;
import vttp.groupb.paf.backup_database.models.PurchaseOrder;
import vttp.groupb.paf.backup_database.repositories.PurchaseOrderRepository;

import static vttp.groupb.paf.backup_database.Utils.*;

@Service
public class PurchaseOrderService {
    
    @Autowired
    private EventStoreService eventStoreSvc;

    @Autowired
    private PurchaseOrderRepository poRepo;
    
    public void reconcileEvents() {

        List<Event> events = eventStoreSvc.popEvents();

        if (!events.isEmpty()) {
            
            for (Event event : events) {
                JsonObject data = event.getData();
                System.out.printf(">>> Event JSon Object added to db: %s\n",data.toString());

                PurchaseOrder po = toPurchaseOrder(data.toString());
                poRepo.addPurchaseOrder(po);
                System.out.printf(">>> Event added to db: %s\n",event);
            }
            System.out.printf(">>> All events in store reconciled\n");
        }

        System.out.printf(">>> No events in store to reconcile\n");
    }
}
