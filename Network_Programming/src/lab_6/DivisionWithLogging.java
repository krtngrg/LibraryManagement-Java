package lab_6;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DivisionWithLogging {

    private static final Logger logger = Logger.getLogger("errors");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter numerator: ");
            int numerator = scanner.nextInt();

            System.out.print("Enter denominator: ");
            int denominator = scanner.nextInt();

            int result = numerator / denominator;
            System.out.println("Result: " + result);

        } catch (ArithmeticException e) {
            logger.log(Level.WARNING, "Division error: cannot divide by zero", e);
            System.out.println("Error: Cannot divide by zero.");

        } catch (Exception e) {
            logger.log(Level.WARNING, "Unexpected error occurred", e);
            System.out.println("Error: Invalid input.");
        } finally {
            scanner.close();
        }
    }
}
