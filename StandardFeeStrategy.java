package parcel;

/** Standard: fee = base fee. */
public class StandardFeeStrategy implements DeliveryFeeStrategy {
    @Override
    public double calculateFee(double baseFee) {
        return baseFee;
    }

    @Override
    public boolean isAllowedFor(Parcel parcel) {
        return true;
    }
}
