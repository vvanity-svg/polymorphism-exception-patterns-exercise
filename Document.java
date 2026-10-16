package parcel;

public class Document extends Parcel {
    public static final double MAX_WEIGHT = 0.5;
    private static final double FLAT_FEE = 50.0;

    private final int pages;

    public Document(String trackingNumber, String receiverName, double weight, int pages)
            throws InvalidInputException {
        super(trackingNumber, receiverName, weight, MAX_WEIGHT, "Document");
        this.pages = Validator.validatePages(pages);
    }

    @Override
    public String getType() { return "Document"; }

    @Override
    public double getChargeableWeight() { return getWeight(); }

    @Override
    public double getBaseFee() { return FLAT_FEE; }

    public int getPages() { return pages; }
}
