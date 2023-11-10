import java.util.Scanner;

public class RunningSpeedCalculator {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        double kmPerMile = 1.609344;

        // Prompt the user for kilometers and minutes
        System.out.print("How many kilometers did you run? ");
        double kilometers = scnr.nextDouble();
        
        System.out.print("How many minutes did it take you? ");
        double minutes = scnr.nextDouble();

        // Calculate the average speed in miles per hour
        double miles = kilometers / kmPerMile;
        double hours = minutes / 60;
        double speedInMph = miles / hours;

        // Display the average speed
        System.out.println("Your average speed was " + speedInMph + " miles per hour.");

        scnr.close();
    }
}
