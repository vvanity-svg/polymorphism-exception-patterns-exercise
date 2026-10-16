package parcel;

/** Same Day: fee = (base fee x 2) + Php 100; only for chargeable weight <= 10 kg. */
public class SameDayFeeStrategy implements DeliveryFeeStrategy {
    public static final double MAX_CHARGEABLE_WEIGHT = 10.0;
    private static final double SURCHARGE = 100.0;

    @Override
    public double calculateFee(double baseFee) {
        return (baseFee * 2) + SURCHARGE;
    }

    @Override
    public boolean isAllowedFor(Parcel parcel) {
        return parcel.getChargeableWeight() <= MAX_CHARGEABLE_WEIGHT;
    }
}
