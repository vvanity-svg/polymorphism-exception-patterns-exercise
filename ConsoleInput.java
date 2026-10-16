package parcel;

import java.util.Scanner;

/** Console helper: every read method loops until the user enters a valid value. */
public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            throw new InputClosedException();
        }
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt, String field, int min, int max) {
        while (true) {
            try {
                int v = Validator.parseInteger(readLine(prompt), field);
                if (v < min || v > max) {
                    throw new InvalidInputException(
                        field + " must be between " + min + " and " + max + ".");
                }
                return v;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /** Reads a tracking number in the correct format (no duplicate / existence check). */
    public String readTrackingNumber(String prompt) {
        while (true) {
            try {
                return Validator.validateTrackingNumber(readLine(prompt));
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String readReceiverName() {
        while (true) {
            try {
                return Validator.validateReceiverName(readLine("Enter receiver name: "));
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public double readWeight(double maxWeight, String typeName) {
        while (true) {
            try {
                double w = Validator.parseDecimal(readLine("Enter weight (kg): "), "Weight");
                return Validator.validateWeight(w, maxWeight, typeName);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int readPages() {
        while (true) {
            try {
                int p = Validator.parseInteger(
                    readLine("Enter number of pages (1-100): "), "Number of pages");
                return Validator.validatePages(p);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int readDimension(String label) {
        while (true) {
            try {
                int d = Validator.parseInteger(readLine("Enter " + label.toLowerCase() + " (cm): "), label);
                return Validator.validateDimension(d, label);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String readHandlingNote() {
        while (true) {
            try {
                return Validator.validateHandlingNote(readLine("Enter handling note: "));
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public DeliveryService readDeliveryService() {
        System.out.println("Select delivery service:");
        System.out.println("  1. Standard");
        System.out.println("  2. Express");
        System.out.println("  3. Same Day");
        int choice = readInt("Enter choice (1-3): ", "Choice", 1, 3);
        return DeliveryService.values()[choice - 1];
    }
}
