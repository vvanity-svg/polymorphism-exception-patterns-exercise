package parcel;

/** Thrown when no parcel matches the given tracking number. */
public class ParcelNotFoundException extends ParcelException {
    public ParcelNotFoundException() {
        super("Parcel not found!");
    }
}
