package parcel;

/** Unchecked exception raised when the input stream ends (e.g., Ctrl+D / Ctrl+Z). */
public class InputClosedException extends RuntimeException {
    public InputClosedException() {
        super("Input stream closed.");
    }
}
