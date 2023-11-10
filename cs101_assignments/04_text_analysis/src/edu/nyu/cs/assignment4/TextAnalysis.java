package edu.nyu.cs.assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A program to analyze the use of verbal tics in any transcribed text.
 * Complete the functions to perform the tasks indicated in the comments.
 */
public class TextAnalysis {

    // use this "global"-ish Scanner variable when getting keyboard input from the
    // user within any function; this avoids common problems using several different
    // Scanners within different functions
    public static Scanner scanner = new Scanner(System.in);

    /**
     * The main function is automatically called first in a Java program.
     * This function contains the main logic of the program that makes use of all
     * the other functions to solve the problem.
     * This main function MUST make use of the other functions to perform the tasks
     * those functions are designed for, i.e.:
     * - you must use the getFilepathFromUser() to get the file path to the text
     * file that the user wants to analyze
     * - you must use the getContentsOfFile() function whenever you need to get the
     * contents of the text file
     * - you must use the getTicsFromUser() function whenever you need to get the
     * set of tics the user wants to analyze in the text
     * - you must use the countOccurrences() function whenever you want to count the
     * number of occurrences of a given tic within the text
     * - you must use the calculatePercentage() function whenever you want to
     * calculate the percentage of all tics in the text that a given tic consumes
     * - you must use the calculateTicDensity() function to calculate the proportion
     * of all words in the text that are tic words
     * 
     * @param args An array of any command-line arguments.
     */
    public static void main(String[] args) throws Exception {

        // complete this function according to the instructions

        try {
            // Get the file path from the user
            String filePath = getFilepathFromUser();
    
            // Get the contents of the text file
            String textContent = getContentsOfFile(filePath);
    
            // Get the tics from the user
            String[] tics = getTicsFromUser();
    
            // Initialize variables to keep track of tic statistics
            int totalTicCount = 0;
            double totalTicPercentage = 0.0;
    
            // Calculate the tic density
            double ticDensity = calculateTicDensity(tics, textContent);
            
            
            for (String tic : tics) {
                // Count occurrences of the current tic in the text
                int ticCount = countOccurrences(tic, textContent);
    
                totalTicCount += ticCount;
            }

            // Display the overall statistics
            System.out.println("............................... Analyzing text .................................");

            System.out.println("Total number of tics: " + totalTicCount);
            System.out.println("Density of Tics: " + ticDensity);

            System.out.println("...............................Tic breakdown....................................");
            // Iterate through each tic and calculate its statistics

            for (String tic : tics) {
                // Count occurrences of the current tic in the text
                int ticCount = countOccurrences(tic, textContent);
    
                

                

                // Calculate the percentage of the current tic in the text
                double ticPercentage = calculatePercentage(ticCount, totalTicCount);
    
                // Add the current tic's statistics to the total
                
                totalTicPercentage += ticPercentage;
    
                // Display the statistics for the current tic
                System.out.println(tic+"\t\t/ "+ ticCount +"\t\t\t\t/ " +ticPercentage + "% of all tics" );
                
            }
    
            
    
            
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

    }
    
    /**
     * 
     * getFilepathFromUser method
     * Asks the user to enter the path to the text file they want to analyze.
     * Hint:
     * - use the "global"-ish Scanner variable scanner to get the response from the
     * user, rather than creating a new Scanner variable within this function.
     * - do not close the "global"-ish Scanner so that you can use it in other
     * functions
     * 
     * @return The file path that the user enters, e.g.
     *         "trump_speech_010621.txt"
     */
    public static String getFilepathFromUser() {
        // complete the getFilepathFromUser function according to the instructions above
        System.out.println("What file would you like to open?");
        String filePath = scanner.nextLine();
        return filePath;
        // replace this line by the actual return
    }

    /**
     * getContentsOfFile method
     * Opens the specified file and returns the text therein.
     * If the file can't be opened, print out the message:
     * "Oh no... can't find the file!"
     * 
     * @param filename The path to a text file containing a speech transcript with
     *                 verbal tics in it.
     * @return The full text in the file as a String.
     */
    public static String getContentsOfFile(String filepath) {

        // the code in this function is given to you... don't change it.

        String fullText = "";
        // opening up a file may fail if the file is not there, so use try/catch to
        // protect against such errors
        try {
            // try to open the file and extract its contents
            Scanner scanner = new Scanner(new File(filepath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fullText += line + "\n"; // nextLine() removes line breaks, so add them back in
            }
            // scanner.close(); // be nice and close the Scanner
        } catch (FileNotFoundException e) {
            // in case we fail to open the file, output a friendly message.
            System.out.println("Oh no... can't find the file!");
        }
        return fullText; // return the full text
    }

    /**
     * getTicsFromUser method
     * Asks the user to enter a comma-separated list of tics, e.g. "uh,like, um,so",
     * and returns an array containing those tics, e.g.
     * { "uh", "like", "um", "so" }.
     * 
     * - use the "global"-ish Scanner variable scanner to get the response from the
     * user, rather than creating a new Scanner variable within this function.
     * - do not close the "global"-ish Scanner so that you can use it in other
     * functions
     * 
     * @return A String array containing each of the tics to analyze, with any
     *         leading or trailing whitespace removed from each tic.
     */

    // write the getTicsFromUser function according to the instructions
    public static String[] getTicsFromUser() {
        System.out.println("What words would you like to look for?");
        String userInput = scanner.nextLine(); // Read the user's input

        // Split the input string by commas and remove leading/trailing whitespace
        String[] tics = userInput.split(",");
        for (int i = 0; i < tics.length; i++) {
            tics[i] = tics[i].trim(); // Remove leading and trailing whitespace
        }
        return tics;
    }


    /**
     * countOccurrences method
     * Counts how many times a given string (the needle) occurs within another
     * string (the haystack), ignoring case.
     * 
     * @param needle   The String to search for.
     * @param haystack The String within which to search.
     * @return The number of occurrences of the "needle" String within the
     *         "haystack" String, ignoring case.
     */

    // write the countOccurrences function according to the instructions
    // Note that this line might be useful:
    // String[] haystackSplit = input.split("[ \n\t.,?!]+");

    public static int countOccurrences(String needle, String haystack) {
        // Split the haystack into words and convert both needle and words to lowercase for case-insensitive search
        needle = needle.toLowerCase();
        String[] words = haystack.toLowerCase().split("[ \n\t.,?!]+");

        int count = 0;
        for (String word : words) {
            if (word.equals(needle)) {
                count++;
            }
        }

        return count;
    }


    /**
     * calculatePercentage method
     * Calculates the equivalent percentage from the proportion of one number to
     * another number.
     * 
     * @param num1 The number to be converted to a percentage. i.e. the numerator in
     *             the ratio of num1 to num2.
     * @param num2 The overall number out of which the num1 number is taken. i.e.
     *             the denominator in the ratio of num1 to num2.
     * @return The percentage that rum1 represents out of the total of num2, rounded
     *         to the nearest integer.
     */

    // write the calculatePercentage function according to the instructions above

    public static int calculatePercentage(int num1, int num2) {
        // Calculate the percentage
        double percentage = (double) num1 / num2 * 100;
    
        // Round the percentage to the nearest integer
        int roundedPercentage = (int) Math.round(percentage);
    
        return roundedPercentage;
    }
    

    /**
     * calculateTicDensity method
     * Calculates the "density" of tics in the text. In other words, the proportion
     * of tic words to the total number of words in a text.
     * 
     * - assume that words in the text are separated from one another by any of the
     * following characters: space ( ), line break (\n), tab (\t), period (.), comma
     * (,), question mark (?), or exclamation mark (!)
     * - all Strings have a .split() method which can split by any of a collection
     * of characters given as an argument; This function returns an array of the
     * remaining text that was separated by any of those characters
     * - e.g. "foo-bar;baz.bum".split("[-;.]+") will result in an array with {
     * "foo", "bar", "baz", and "bum" } as the values.
     * 
     * @param tics     An array of tic words to analyze.
     * @param fullText The full text.
     * @return The proportion of the number of tic words present in the text to the
     *         total number of words in the text, as a double.
     */

    // write the calculateTicDensity function according to the instructions above


    public static double calculateTicDensity(String[] tics, String fullText) {
        // Split the full text into words using the specified delimiters
        String[] words = fullText.split("[ \n\t.,?!]+");
    
        // Count the total number of words
        int totalWords = words.length;
    
        // Count the number of tic words present in the text
        int ticCount = 0;
        
        for (String word : words) {
            for (String tic : tics) {
                // Compare each word with each tic, ignoring case
                if (word.equalsIgnoreCase(tic)) {
                    ticCount++;
                    break; // No need to continue checking this word against other tics
                }
            }
        }
    
        // Calculate the proportion of tic words to total words
        double ticDensityCalculation = (double) ticCount / totalWords;
        double ticDensity = Math.round(ticDensityCalculation * 100.0) / 100.0;
        return ticDensity;
    }
    
} // end of class
