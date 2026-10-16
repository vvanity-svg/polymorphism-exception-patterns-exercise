package parcel;

/** Thrown when an operation is attempted on a parcel that is already delivered. */
public class ParcelAlreadyDeliveredException extends ParcelException {
    public ParcelAlreadyDeliveredException() {
        super("Parcel already delivered!");
    }
}
