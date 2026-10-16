package parcel;

/** Abstract parent of all parcels. Subclasses supply their own weight and fee rules. */
public abstract class Parcel {
    private final String trackingNumber;
    private final String receiverName;
    private final double weight;
    private DeliveryService service = DeliveryService.STANDARD;
    private ParcelStatus status = ParcelStatus.PENDING;

    protected Parcel(String trackingNumber, String receiverName, double weight,
                     double maxWeight, String typeName) throws InvalidInputException {
        this.trackingNumber = Validator.validateTrackingNumber(trackingNumber);
        this.receiverName = Validator.validateReceiverName(receiverName);
        this.weight = Validator.validateWeight(weight, maxWeight, typeName);
    }

    // ---- polymorphic behaviour ----
    public abstract String getType();

    /** Weight actually used for pricing. */
    public abstract double getChargeableWeight();

    /** Fee before the delivery-service multiplier. */
    public abstract double getBaseFee();

    // ---- strategy-based fee ----
    public double getDeliveryFee() {
        return service.getStrategy().calculateFee(getBaseFee());
    }

    /**
     * Changes the delivery service.
     * @throws ParcelAlreadyDeliveredException if the parcel is already delivered
     * @throws SameDayNotAllowedException      if the service is not allowed for this parcel
     */
    public void changeService(DeliveryService newService)
            throws ParcelAlreadyDeliveredException, SameDayNotAllowedException {
        if (newService == null) {
            throw new IllegalArgumentException("Delivery service cannot be null.");
        }
        if (isDelivered()) {
            throw new ParcelAlreadyDeliveredException();
        }
        if (!newService.getStrategy().isAllowedFor(this)) {
            throw new SameDayNotAllowedException();
        }
        this.service = newService;
    }

    /** @return true if the parcel was pending and is now delivered; false if already delivered. */
    public boolean markDelivered() {
        if (isDelivered()) {
            return false;
        }
        status = ParcelStatus.DELIVERED;
        return true;
    }

    public boolean isDelivered() { return status == ParcelStatus.DELIVERED; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getReceiverName() { return receiverName; }
    public double getWeight() { return weight; }
    public DeliveryService getService() { return service; }
    public ParcelStatus getStatus() { return status; }
}
