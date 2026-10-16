package parcel;

import java.util.List;

public class Main {
    private static final ParcelManager manager = ParcelManager.getInstance();
    private static final ConsoleInput in = new ConsoleInput();

    public static void main(String[] args) {
        System.out.println("=== PARCEL DELIVERY SYSTEM ===");
        try {
            boolean running = true;
            while (running) {
                printMenu();
                int choice = in.readInt("Enter choice (1-5): ", "Choice", 1, 5);
                switch (choice) {
                    case 1 -> addParcel();
                    case 2 -> viewAllParcels();
                    case 3 -> changeDeliveryService();
                    case 4 -> markAsDelivered();
                    case 5 -> {
                        System.out.println("Thank you for using the Parcel Delivery System. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (InputClosedException e) {
            System.out.println("\nInput closed. Exiting. Goodbye!");
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("---------- MENU ----------");
        System.out.println("1. Add Parcel");
        System.out.println("2. View All Parcels");
        System.out.println("3. Change Delivery Service");
        System.out.println("4. Mark as Delivered");
        System.out.println("5. Exit");
        System.out.println("--------------------------");
    }

    // ---------------- 1. Add Parcel ----------------
    private static void addParcel() {
        System.out.println("Select parcel type:");
        System.out.println("  1. Document");
        System.out.println("  2. Box");
        System.out.println("  3. Fragile");
        int type = in.readInt("Enter choice (1-3): ", "Choice", 1, 3);

        // Tracking number: valid format AND not a duplicate (re-ask until OK)
        String tracking;
        while (true) {
            tracking = in.readTrackingNumber("Enter tracking number (e.g., ABC0001): ");
            if (manager.exists(tracking)) {
                System.out.println(new DuplicateTrackingNumberException(tracking).getMessage());
            } else {
                break;
            }
        }

        String receiver = in.readReceiverName();
        Parcel parcel;
        try {
            switch (type) {
                case 1 -> {
                    double w = in.readWeight(Document.MAX_WEIGHT, "Document");
                    int pages = in.readPages();
                    parcel = new Document(tracking, receiver, w, pages);
                }
                case 2 -> {
                    double w = in.readWeight(Box.MAX_WEIGHT, "Box");
                    int l = in.readDimension("Length");
                    int wd = in.readDimension("Width");
                    int h = in.readDimension("Height");
                    parcel = new Box(tracking, receiver, w, l, wd, h);
                }
                default -> {
                    double w = in.readWeight(Fragile.MAX_WEIGHT, "Fragile");
                    int l = in.readDimension("Length");
                    int wd = in.readDimension("Width");
                    int h = in.readDimension("Height");
                    String note = in.readHandlingNote();
                    parcel = new Fragile(tracking, receiver, w, l, wd, h, note);
                }
            }

            // Keep asking until an allowed service is chosen
            while (true) {
                DeliveryService service = in.readDeliveryService();
                try {
                    parcel.changeService(service);
                    break;
                } catch (SameDayNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }

            manager.addParcel(parcel);
            System.out.printf("Parcel added successfully! Delivery fee: Php %.2f%n",
                    parcel.getDeliveryFee());
        } catch (ParcelException e) {
            // Safety net: inputs are already validated, but never crash the menu.
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ---------------- 2. View All Parcels ----------------
    private static void viewAllParcels() {
        List<Parcel> all = manager.getAllParcels();
        if (all.isEmpty()) {
            System.out.println("No parcels to display.");
            return;
        }
        String fmt = "| %-10s | %-8s | %-20s | %-12s | %-9s | %12s | %-9s |%n";
        String line = "+" + "-".repeat(12) + "+" + "-".repeat(10) + "+" + "-".repeat(22) + "+"
                + "-".repeat(14) + "+" + "-".repeat(11) + "+" + "-".repeat(14) + "+" + "-".repeat(11) + "+";
        System.out.println(line);
        System.out.printf(fmt, "Tracking #", "Type", "Receiver", "Chargeable", "Service", "Fee (Php)", "Status");
        System.out.println(line);
        for (Parcel p : all) {
            System.out.printf(fmt,
                    p.getTrackingNumber(),
                    p.getType(),
                    truncate(p.getReceiverName(), 20),
                    Validator.trim(p.getChargeableWeight()) + " kg",
                    p.getService().getDisplayName(),
                    String.format("%,.2f", p.getDeliveryFee()),
                    p.getStatus());
        }
        System.out.println(line);
        System.out.println("Total parcels: " + all.size());
    }

    private static String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 3) + "...";
    }

    // ---------------- 3. Change Delivery Service ----------------
    private static void changeDeliveryService() {
        String tracking = in.readTrackingNumber("Enter tracking number: ");
        try {
            Parcel parcel = manager.findParcel(tracking);
            if (parcel.isDelivered()) {
                throw new ParcelAlreadyDeliveredException();
            }
            System.out.println("Current service: " + parcel.getService());
            DeliveryService newService = in.readDeliveryService();
            if (newService == parcel.getService()) {
                System.out.println("Parcel already uses the " + newService + " service. No changes made.");
                return;
            }
            parcel.changeService(newService);
            System.out.printf("Delivery service changed! New fee: Php %.2f%n", parcel.getDeliveryFee());
        } catch (ParcelException e) {
            // Not found / already delivered / Same Day not allowed (old service is kept)
            System.out.println(e.getMessage());
        }
    }

    // ---------------- 4. Mark as Delivered ----------------
    private static void markAsDelivered() {
        String tracking = in.readTrackingNumber("Enter tracking number: ");
        try {
            Parcel parcel = manager.findParcel(tracking);
            if (parcel.markDelivered()) {
                System.out.println("Parcel delivered successfully!");
            } else {
                System.out.println("Parcel was already delivered.");
            }
        } catch (ParcelNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
