package parcel;

/** STRATEGY PATTERN: each delivery service supplies its own fee rule. */
public interface DeliveryFeeStrategy {

    /** Computes the delivery fee from the parcel's base fee. */
    double calculateFee(double baseFee);

    /** Whether this service may be used for the given parcel. */
    boolean isAllowedFor(Parcel parcel);
}
