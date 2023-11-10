public class PopulationProjector {
    public static void main(String[] args) {
    // complete this function to solve the problem }
        int currentPopulation = 334300850;
        int secondsPerYear = 365 * 24 * 60 * 60;
        int birthsPerYear = secondsPerYear / 9;
        int deathsPerYear = secondsPerYear / 10;
        int immigrantsPerYear = secondsPerYear / 32;
        int year2024 = currentPopulation+birthsPerYear-deathsPerYear+immigrantsPerYear;
        int year2025 = year2024+birthsPerYear-deathsPerYear+immigrantsPerYear;
        int year2026 = year2025+birthsPerYear-deathsPerYear+immigrantsPerYear;
        int year2027 = year2026+birthsPerYear-deathsPerYear+immigrantsPerYear;
        int year2028 = year2027+birthsPerYear-deathsPerYear+immigrantsPerYear;
        // Calculate and display the projected population for the next five years
        System.out.println("Here are the projected population numbers for the next five years:");
        System.out.println("- Year 2024: "+ year2024);
        System.out.println("- Year 2025: "+ year2025);
        System.out.println("- Year 2026: "+ year2026);
        System.out.println("- Year 2027: "+ year2027);
        System.out.println("- Year 2028: "+ year2028);
    }
}