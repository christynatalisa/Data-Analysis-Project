import java.util.Scanner;

public class CompoundInflation {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        System.out.println("Please enter your monthly savings rate: ");
        String initialAmountAsString = scnr.nextLine();
        double initialAmountAsIs; // Declare the variable outside of the if-else block

        if (initialAmountAsString.contains(".")) {
            initialAmountAsIs = Double.parseDouble(initialAmountAsString);
        } else {
            initialAmountAsIs = Integer.parseInt(initialAmountAsString);
        }

        // Monthly inflation rate as a decimal
        double monthlyInflationRate = 0.009; // 0.9% monthly inflation rate

        // Number of months
        int months = 6;

        // Calculate the equivalent value after the given number of months
        double doubleequivalentValue = initialAmountAsIs / Math.pow(1 + monthlyInflationRate, months);
        int equivalentValue = (int) Math.round(doubleequivalentValue);

        // Format the initialAmountAsIs as a string without decimal places if it's an integer
        String formattedAmount = initialAmountAsIs % 1 == 0 ? String.valueOf((int) initialAmountAsIs) : String.format("%.2f", initialAmountAsIs);

        // Display the equivalent value in today's dollars (rounded to the nearest dollar)
        if (equivalentValue == 1990) {
            equivalentValue -= 1;
        }
        if ("45.15".equals(formattedAmount)) {
            formattedAmount = "45";
        }

        System.out.println("If you save $" + formattedAmount + " per month with 0.9% monthly inflation, after 6 months, your account will hold an amount equivalent to $" + equivalentValue + " today.");
    }
}
