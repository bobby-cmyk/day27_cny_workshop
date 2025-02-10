package vttp.groupb.paf.backup_database;

import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.groupb.paf.backup_database.models.LineItem;
import vttp.groupb.paf.backup_database.models.PurchaseOrder;

public class Utils {

    public static PurchaseOrder toPurchaseOrder(String payload) {

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject jo = reader.readObject();

        PurchaseOrder po = new PurchaseOrder();
        po.setPoId(jo.getString("poId", ""));
        po.setName(jo.getString("name"));
        po.setAddress(jo.getString("address"));
        try {
            po.setDeliveryDate(LocalDate.parse(jo.getString("deliveryDate")));
        } catch (Exception ex) {
            po.setDeliveryDate(null);
        }
        po.setLineItems(toLineItem(jo.getJsonArray("lineItems")));

        return po;
    }

    public static List<LineItem> toLineItem(JsonArray arr) {

        return  arr.stream()
            .map(JsonValue::asJsonObject)
            .map(jo -> {
                LineItem li = new LineItem();
                li.setName(jo.getString("name"));
                li.setQuantity(jo.getInt("quantity"));
                li.setUnitPrice((float)jo.getJsonNumber("unitPrice").doubleValue());
                return li;
            }).toList();

    }
}