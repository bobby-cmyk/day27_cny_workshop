package vttp.groupb.paf.backup_database.exceptions;

public class EventStoreException extends RuntimeException{
    public EventStoreException(String message) {
        super(message);
    }
}
