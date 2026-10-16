package parcel;

/** The available delivery services, each bound to its fee strategy. */
public enum DeliveryService {
    STANDARD("Standard", new StandardFeeStrategy()),
    EXPRESS("Express", new ExpressFeeStrategy()),
    SAME_DAY("Same Day", new SameDayFeeStrategy());

    private final String displayName;
    private final DeliveryFeeStrategy strategy;

    DeliveryService(String displayName, DeliveryFeeStrategy strategy) {
        this.displayName = displayName;
        this.strategy = strategy;
    }

    public String getDisplayName() {
        return displayName;
    }

    public DeliveryFeeStrategy getStrategy() {
        return strategy;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
