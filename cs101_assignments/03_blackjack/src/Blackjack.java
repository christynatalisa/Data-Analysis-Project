package edu.nyu.cs.assignment3;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Blackjack {
    // do not modify this code
    public static Random initRandom(String[] args) {
        if (args.length >= 1) {
            return new Random(Long.parseLong(args[0]));
        } else {
            return new Random();
        }
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!");

        // The random number generator is given to you, do not change this line.
        // Every random number should be generated through this variable.
        Random random = initRandom(args);

        List<Integer> playerNumberList = new ArrayList<>();
        List<Integer> dealerNumberList = new ArrayList<>();

        boolean continueAdding = true;

        // Generate two initial cards for the player
        int randomNumber1 = random.nextInt(12) + 1;
        playerNumberList.add(randomNumber1);
        int randomNumber2 = random.nextInt(12) + 1;
        playerNumberList.add(randomNumber2);

        // Show user their initial cards
        List<String> playerCardStrings = new ArrayList<>();
        for (Integer card : playerNumberList) {
            playerCardStrings.add(String.valueOf(card));
        }
        System.out.println("Your cards are: " + String.join(", ", playerCardStrings));

        // Ask the user to 'hit' or 'stand'
        System.out.println("Would you like to hit or stand? ");
        String choice = scnr.nextLine();


        while (continueAdding) {
            if (choice.equals("hit")) {
                // Give a new card to the user
                int randomNumber = random.nextInt(12) + 1;
                playerNumberList.add(randomNumber);
                List<String> playerCardStringss = new ArrayList<>();
                for (Integer card : playerNumberList) {
                    playerCardStringss.add(String.valueOf(card));
                }
                // Check if the player has busted
                int playerSum = playerNumberList.stream().mapToInt(Integer::intValue).sum();
                if (playerSum > 21) {
                    System.out.println("You have bust!");
                    continueAdding = false;
                } else {
                    // Continue the loop to ask for more hits or stand
                    System.out.println("Your cards are: " + String.join(", ", playerCardStringss));
                    System.out.println("Would you like to hit or stand? ");
                    choice = scnr.nextLine();
                }
            } else {
                // user stands and we let the dealer play
                continueAdding = false;
            }
        }

        // Generate two initial cards for the dealer
        int randommNumber1 = random.nextInt(12) + 1;
        dealerNumberList.add(randommNumber1);
        int randommNumber2 = random.nextInt(12) + 1;
        dealerNumberList.add(randommNumber2);

        Random boolrandom = new Random();

        // Generate a random boolean value
        boolean randomBoolean = boolrandom.nextBoolean();

        if (randomBoolean) {
            while (randomBoolean) {
                int randomDealerNumber1 = random.nextInt(12) + 1; // Generate a random number between 1 and 12
                dealerNumberList.add(randomDealerNumber1); // Add the random number to the list
                System.out.println("The dealer hits.");
                // Generate a new random decision for the dealer
                randomBoolean = random.nextBoolean();
            }
        }

        System.out.println("The dealer stands.");

        // Calculate the sum of playerNumberList
        int playerSum = 0;
        for (Integer card : playerNumberList) {
            playerSum += card;
        }
        // Calculate the sum of dealerNumberList
        int dealerSum = 0;
        for (Integer card : dealerNumberList) {
            dealerSum += card;
        }

        List<String> playerCardStrings1 = new ArrayList<>();
        for (Integer card : playerNumberList) {
            playerCardStrings1.add(String.valueOf(card));
        }

        List<String> dealerCardStrings = new ArrayList<>();
        for (Integer card : dealerNumberList) {
            dealerCardStrings.add(String.valueOf(card));
        }

        System.out.println("Your cards are: " + String.join(", ", playerCardStrings1));
        System.out.println("The dealer's cards are: " + String.join(", ", dealerCardStrings));

        // Determine the winner and print the result
        if (playerSum == dealerSum) {
            System.out.println("Tie!");
        } else if (playerSum > 21) {
            //System.out.println("You have bust!");
            if (dealerSum <=21){
                System.out.println("Dealer wins!");
            }
        } else if (dealerSum > 21) {
            System.out.println("The dealer has bust!");
            if (playerSum <=21){
                System.out.println("You win!");
            }
        } else if (playerSum>dealerSum){
            System.out.println("You win!");
        } else if (dealerSum>playerSum){
            System.out.println("Dealer wins!");
        }
    
        scnr.close();
    }
}
