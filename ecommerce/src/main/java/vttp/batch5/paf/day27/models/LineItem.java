package vttp.batch5.paf.day27.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class LineItem {
    private int id;
    private String name;
    private int quantity;
    private float unitPrice;
    private String poId;

    
    public void setId(int id) { this.id = id; }
    public int getId() { return this.id; }

    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getQuantity() { return this.quantity; }

    public void setUnitPrice(float unitPrice) { this.unitPrice = unitPrice; }
    public float getUnitPrice() { return this.unitPrice; }

    public String getPoId() {return poId;}
    public void setPoId(String poId) {this.poId = poId;}
    
    @Override
    public String toString() {
        return "LineItem[id=%d, name=%s, quantity=%d, unitPrice=%f]"
                .formatted(id, name, quantity, unitPrice);

    }

    public JsonObject toJsonObject() {

        JsonObjectBuilder liBuilder = Json.createObjectBuilder();

        liBuilder.add("id",id);
        liBuilder.add("name", name);
        liBuilder.add("quantity", quantity);
        liBuilder.add("unitPrice", unitPrice);
        liBuilder.add("poId", poId);

        return liBuilder.build();
    }
}
