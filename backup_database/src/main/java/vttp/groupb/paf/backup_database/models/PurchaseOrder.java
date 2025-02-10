package vttp.groupb.paf.backup_database.models;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class PurchaseOrder {

    private String poId;
    private String name;
    private String address;
    private LocalDate deliveryDate;
    private List<LineItem> lineItems = new LinkedList<>();

    public void setPoId(String poId) { this.poId = poId;}
    public String getPoId() { return this.poId; }

    public void setName(String name) { this.name = name;}
    public String getName() { return this.name; }

    public void setAddress(String address) { this.address = address;}
    public String getAddress() { return this.address; }

    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate;}
    public LocalDate getDeliveryDate() { return this.deliveryDate; }

    public void setLineItems(List<LineItem> lineItems) { this.lineItems = lineItems;}
    public List<LineItem> getLineItems() { return this.lineItems; }
    public void addLineItem(LineItem lineItem) { this.lineItems.add(lineItem); }

    @Override
    public String toString() {
        return "PurchaseOrder[poId=%s, name=%s, address=%s, deliveryDate=%s lineItems=%d]"
            .formatted(poId, name, address, deliveryDate, lineItems.size());
    }

    public JsonObject toJsonObject() {
        JsonObjectBuilder poBuilder = Json.createObjectBuilder();

        poBuilder.add("poId", poId);
        poBuilder.add("name", name);
        poBuilder.add("address", address);
        poBuilder.add("deliveryDate", deliveryDate.toString());
        
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (LineItem li : lineItems) {

            arrBuilder.add(li.toJsonObject());
        }

        poBuilder.add("lineItems",arrBuilder.build());

        return poBuilder.build();
    }

    
}

