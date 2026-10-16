package parcel;

public class Fragile extends Box {
    public static final double MAX_WEIGHT = 30.0;
    private static final double HANDLING_FEE = 100.0;

    private final String handlingNote;

    public Fragile(String trackingNumber, String receiverName, double weight,
                   int length, int width, int height, String handlingNote)
            throws InvalidInputException {
        super(trackingNumber, receiverName, weight, MAX_WEIGHT, "Fragile", length, width, height);
        this.handlingNote = Validator.validateHandlingNote(handlingNote);
    }

    @Override
    public double getBaseFee() { return super.getBaseFee() + HANDLING_FEE; }

    @Override
    public String getType() { return "Fragile"; }

    public String getHandlingNote() { return handlingNote; }
}
