package vttp.groupb.paf.backup_database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vttp.groupb.paf.backup_database.services.PurchaseOrderService;

@Component
public class ReconcileDatabase {
    
    @Autowired
    private PurchaseOrderService poSvc;

    @Scheduled(fixedRate = 30000)
    public void reconcileDb() {

        poSvc.reconcileEvents();
    }
}


