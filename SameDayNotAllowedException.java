package parcel;

/** Thrown when Same Day delivery is requested for a parcel that is too heavy. */
public class SameDayNotAllowedException extends ParcelException {
    public SameDayNotAllowedException() {
        super("Same Day delivery is only for parcels up to 10 kg!");
    }
}
