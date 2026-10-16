package parcel;

public enum ParcelStatus {
    PENDING("Pending"),
    DELIVERED("Delivered");

    private final String displayName;

    ParcelStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
