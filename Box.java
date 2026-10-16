package parcel;

public class Box extends Parcel {
    public static final double MAX_WEIGHT = 50.0;
    private static final double RATE_PER_KG = 40.0;
    private static final int VOLUMETRIC_DIVISOR = 3500;

    private final int length;
    private final int width;
    private final int height;

    public Box(String trackingNumber, String receiverName, double weight,
               int length, int width, int height) throws InvalidInputException {
        this(trackingNumber, receiverName, weight, MAX_WEIGHT, "Box", length, width, height);
    }

    /** Used by subclasses (e.g., Fragile) that have their own weight limit. */
    protected Box(String trackingNumber, String receiverName, double weight,
                  double maxWeight, String typeName,
                  int length, int width, int height) throws InvalidInputException {
        super(trackingNumber, receiverName, weight, maxWeight, typeName);
        this.length = Validator.validateDimension(length, "Length");
        this.width = Validator.validateDimension(width, "Width");
        this.height = Validator.validateDimension(height, "Height");
    }

    public double getVolumetricWeight() {
        return ((long) length * width * height) / (double) VOLUMETRIC_DIVISOR;
    }

    /** Bigger of actual vs. volumetric weight, rounded UP to a whole kg (done in integer math). */
    @Override
    public double getChargeableWeight() {
        long volume = (long) length * width * height;
        long volumetricCeil = (volume + VOLUMETRIC_DIVISOR - 1) / VOLUMETRIC_DIVISOR;
        long actualCeil = (long) Math.ceil(getWeight());
        return Math.max(actualCeil, volumetricCeil);
    }

    @Override
    public double getBaseFee() { return RATE_PER_KG * getChargeableWeight(); }

    @Override
    public String getType() { return "Box"; }

    public int getLength() { return length; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
