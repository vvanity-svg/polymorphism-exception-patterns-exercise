package parcel;

/** Base class for all checked, business-rule exceptions in the system. */
public class ParcelException extends Exception {
    public ParcelException(String message) {
        super(message);
    }
}
