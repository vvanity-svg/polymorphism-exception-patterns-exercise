package parcel;

/** Express: fee = base fee x 1.5. */
public class ExpressFeeStrategy implements DeliveryFeeStrategy {
    @Override
    public double calculateFee(double baseFee) {
        return baseFee * 1.5;
    }

    @Override
    public boolean isAllowedFor(Parcel parcel) {
        return true;
    }
}
