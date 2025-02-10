package vttp.groupb.paf.backup_database.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Event {
    private String eventId;
    private long timestamp;
    private String operation;
    private JsonObject data;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", timestamp=" + timestamp + ", operation=" + operation + ", data=" + data
                + "]";
    }

    public JsonObject toJsonObject() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("eventId", eventId);
        builder.add("timestamp", timestamp);
        builder.add("operation", operation);
        builder.add("data", data);

        return builder.build();
    }
}
