package parcel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * SINGLETON PATTERN: exactly one ParcelManager holds all parcels.
 * Uses the thread-safe "initialization-on-demand holder" idiom.
 */
public final class ParcelManager {

    private final Map<String, Parcel> parcels = new LinkedHashMap<>();

    private ParcelManager() { }

    private static final class Holder {
        private static final ParcelManager INSTANCE = new ParcelManager();
    }

    public static ParcelManager getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized boolean exists(String trackingNumber) {
        return trackingNumber != null && parcels.containsKey(trackingNumber.trim().toUpperCase());
    }

    public synchronized void addParcel(Parcel parcel) throws DuplicateTrackingNumberException {
        if (parcel == null) {
            throw new IllegalArgumentException("Parcel cannot be null.");
        }
        if (parcels.containsKey(parcel.getTrackingNumber())) {
            throw new DuplicateTrackingNumberException(parcel.getTrackingNumber());
        }
        parcels.put(parcel.getTrackingNumber(), parcel);
    }

    public synchronized Parcel findParcel(String trackingNumber) throws ParcelNotFoundException {
        Parcel p = trackingNumber == null ? null : parcels.get(trackingNumber.trim().toUpperCase());
        if (p == null) {
            throw new ParcelNotFoundException();
        }
        return p;
    }

    public synchronized List<Parcel> getAllParcels() {
        return Collections.unmodifiableList(new ArrayList<>(parcels.values()));
    }
}
