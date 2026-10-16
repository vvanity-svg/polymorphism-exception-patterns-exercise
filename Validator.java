package parcel;

import java.util.regex.Pattern;

/** Central place for all input validation rules. */
public final class Validator {

    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_NOTE_LENGTH = 100;
    public static final int MAX_DIMENSION_CM = 1000;
    public static final int MIN_PAGES = 1;
    public static final int MAX_PAGES = 100;

    private static final Pattern TRACKING = Pattern.compile("^ABC\\d{4}$");
    private static final Pattern DECIMAL = Pattern.compile("^\\d{1,6}(\\.\\d{1,3})?$");
    private static final Pattern INTEGER = Pattern.compile("^\\d{1,9}$");
    private static final Pattern NAME = Pattern.compile("^\\p{L}[\\p{L} .'-]*$");

    private Validator() { }

    /** Trims, upper-cases and checks the "ABC" + 4 digits format. */
    public static String validateTrackingNumber(String raw) throws InvalidInputException {
        if (raw == null || raw.isBlank()) {
            throw new InvalidInputException("Tracking number cannot be empty.");
        }
        String t = raw.trim().toUpperCase();
        if (!TRACKING.matcher(t).matches()) {
            throw new InvalidInputException(
                "Invalid tracking number! Use 'ABC' followed by 4 digits (e.g., ABC0001).");
        }
        return t;
    }

    public static String validateReceiverName(String raw) throws InvalidInputException {
        if (raw == null || raw.isBlank()) {
            throw new InvalidInputException("Receiver name cannot be empty.");
        }
        String n = raw.trim().replaceAll("\\s+", " ");
        if (n.length() > MAX_NAME_LENGTH) {
            throw new InvalidInputException(
                "Receiver name cannot exceed " + MAX_NAME_LENGTH + " characters.");
        }
        if (!NAME.matcher(n).matches()) {
            throw new InvalidInputException(
                "Receiver name must start with a letter and contain only letters, "
                + "spaces, periods, apostrophes or hyphens.");
        }
        return n;
    }

    public static String validateHandlingNote(String raw) throws InvalidInputException {
        if (raw == null || raw.isBlank()) {
            throw new InvalidInputException("Handling note cannot be empty.");
        }
        String n = raw.trim().replaceAll("\\s+", " ");
        if (n.length() > MAX_NOTE_LENGTH) {
            throw new InvalidInputException(
                "Handling note cannot exceed " + MAX_NOTE_LENGTH + " characters.");
        }
        return n;
    }

    public static double validateWeight(double weight, double maxWeight, String typeName)
            throws InvalidInputException {
        if (Double.isNaN(weight) || Double.isInfinite(weight) || weight <= 0) {
            throw new InvalidInputException("Weight must be greater than 0 kg.");
        }
        if (weight > maxWeight) {
            throw new InvalidInputException(
                typeName + " weight cannot exceed " + trim(maxWeight) + " kg.");
        }
        return weight;
    }

    public static int validatePages(int pages) throws InvalidInputException {
        if (pages < MIN_PAGES || pages > MAX_PAGES) {
            throw new InvalidInputException(
                "Number of pages must be between " + MIN_PAGES + " and " + MAX_PAGES + ".");
        }
        return pages;
    }

    public static int validateDimension(int value, String label) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(label + " must be greater than 0 cm.");
        }
        if (value > MAX_DIMENSION_CM) {
            throw new InvalidInputException(
                label + " cannot exceed " + MAX_DIMENSION_CM + " cm.");
        }
        return value;
    }

    /** Parses a plain positive decimal (no signs, no exponents, no NaN/Infinity). */
    public static double parseDecimal(String raw, String field) throws InvalidInputException {
        if (raw == null || raw.isBlank()) {
            throw new InvalidInputException(field + " cannot be empty.");
        }
        String s = raw.trim();
        if (!DECIMAL.matcher(s).matches()) {
            throw new InvalidInputException(
                field + " must be a positive number with up to 3 decimal places (e.g., 1.5).");
        }
        return Double.parseDouble(s);
    }

    /** Parses a plain non-negative whole number. */
    public static int parseInteger(String raw, String field) throws InvalidInputException {
        if (raw == null || raw.isBlank()) {
            throw new InvalidInputException(field + " cannot be empty.");
        }
        String s = raw.trim();
        if (!INTEGER.matcher(s).matches()) {
            throw new InvalidInputException(field + " must be a whole number.");
        }
        return Integer.parseInt(s);
    }

    /** Formats a number without trailing zeros (0.50 -> "0.5", 3.0 -> "3"). */
    public static String trim(double value) {
        return new java.math.BigDecimal(String.valueOf(value))
                .stripTrailingZeros().toPlainString();
    }
}
