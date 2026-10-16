package parcel;

/** Thrown when a tracking number is already used by another parcel. */
public class DuplicateTrackingNumberException extends ParcelException {
    public DuplicateTrackingNumberException(String trackingNumber) {
        super("Tracking number " + trackingNumber + " already exists!");
    }
}
