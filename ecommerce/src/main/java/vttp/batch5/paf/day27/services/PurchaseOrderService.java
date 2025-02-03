package vttp.batch5.paf.day27.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch5.paf.day27.exceptions.EventRepositoryException;
import vttp.batch5.paf.day27.exceptions.EventStoreException;
import vttp.batch5.paf.day27.exceptions.PurchaseOrderRepositoryException;
import vttp.batch5.paf.day27.models.Event;
import vttp.batch5.paf.day27.models.PurchaseOrder;
import vttp.batch5.paf.day27.repositories.EventRepository;
import vttp.batch5.paf.day27.repositories.EventStore;
import vttp.batch5.paf.day27.repositories.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {

  @Autowired
  private EventStore eventStore;

  @Autowired
  private EventRepository eventRepo;

  @Autowired
  private PurchaseOrderRepository poRepo;

  @Transactional
  public String createPurchaseOrder(PurchaseOrder po) {
    String poId = UUID.randomUUID().toString().substring(0, 8);

    po.setPoId(poId);
    po.getLineItems().forEach(li -> li.setPoId(poId));

    Event event = createPurchaseOrderEvent(po);

    // Add to eventstore
    if (!eventStore.addEvent(event)) {
      throw new EventStoreException("Failed to add event to Redis List");
    }
  
    // Add to MongoDB
    if (!eventRepo.addEvent(event)) {
      throw new EventRepositoryException("Failed to add event to MongoDB");
    }

    // Add to SQL
    if (!poRepo.addPurchaseOrder(po)) {
      throw new PurchaseOrderRepositoryException("Failed to add purchase order to MySQL");
    }
    
    return poId;
  }

  private Event createPurchaseOrderEvent(PurchaseOrder po) {

    Event event = new Event();

    String eventId = UUID.randomUUID().toString().substring(0, 8);
    long timestamp = getTimestamp();

    event.setEventId(eventId);
    event.setTimestamp(timestamp);
    event.setOperation("CREATE_PO");
    event.setData(po.toJsonObject());

    return event;
  }

  private long getTimestamp() {
    LocalDateTime currentTime = LocalDateTime.now();
    ZoneId zoneId = ZoneId.of("UTC");
    long timestamp = currentTime.atZone(zoneId).toEpochSecond();

    return timestamp;
  }

}
