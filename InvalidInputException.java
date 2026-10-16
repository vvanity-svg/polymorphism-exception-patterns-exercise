package parcel;

/** Thrown when a user-supplied value fails validation. */
public class InvalidInputException extends ParcelException {
    public InvalidInputException(String message) {
        super(message);
    }
}
