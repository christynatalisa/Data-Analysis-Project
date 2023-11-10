package edu.nyu.cs.assignment5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A program to play Sudoku and validate its correctness.
 * Complete the functions to perform the tasks indicated in the comments.
 */
public class SudokuValidator {
    // use this "global"-ish Scanner variable when getting keyboard input from the
    // user within any function; this avoids common problems using several different
    // Scanners within different functions
    public static Scanner scn = new Scanner(System.in);

    /**
     * The main method is automatically called first in a Java program.
     * This method contains the main logic of the program that makes use of all
     * the other methods to solve the problem.
     * This main method makes use of the other methods to perform the tasks.
     * Those methods are:
     * - wonPuzzle() returns true if the puzzle is complete and valid
     * - getFilePathFromUser() asks the user for a CSV file
     * - printRemainingMoves() prints all empty fields in the puzzle
     * - remainingMoves() computes all empty fields in the puzzle
     * - getContentsOfFile() reads a CSV file and writes its content into a
     * two-dimensional array
     * - printPuzzle() prints the Sudoku puzzle to the command line
     * - printRemainingMoves() print all remaining empty fields
     * - makeMove() adds a number to the puzzle if the number and position are valid
     * - validatePuzzle() checks if every number is at most once contained in every
     * row, column, and block
     *
     * @param args An array of any command-line arguments.
     */
    public static void main(String[] args) throws Exception {
        String filepath = getFilepathFromUser();
        int[][] puzzle = getContentsOfFile(filepath);

        printPuzzle(puzzle);

        if (validatePuzzle(puzzle)) {
            System.out.println("Puzzle is valid.");
        } else {
            System.out.println("Puzzle is invalid, exiting.");
            return;
        }

        while (!wonPuzzle(puzzle)) {
            printRemainingMoves(puzzle);
            if (remainingMoves(puzzle).size() == 0) {
                break;
            }
            System.out.println("What is your next move?");
            String line = scn.nextLine();
            if (line.equals("quit")) {
                break;
            } else {
                String[] tokens = line.split(" ");
                try {
                    int row = Integer.parseInt(tokens[0]);
                    int col = Integer.parseInt(tokens[1]);
                    int value = Integer.parseInt(tokens[2]);

                    if (!makeMove(puzzle, row, col, value)) {
                        System.out.println("Try again!");
                    }
                } catch (Exception e) {
                    System.out.println("Did not understand command");
                }
            }
            printPuzzle(puzzle);

        }

        if (wonPuzzle(puzzle)) {
            System.out.println("Congratulations!");
        } else {
            System.out.println("Condolences!");
        }
    }

    // User has won if there are no remaining moves and the puzzle is valid
    /**
     * The Sudoku puzzle was solved successfully if there are no remaining moves and
     * the puzzle is valid.
     *
     * This method should make use of `remainingMoves()` and `validatePuzzle()`.
     *
     * @param puzzle the Sudoku puzzle
     * @return true if the puzzle was solved
     */
    public static boolean wonPuzzle(int[][] puzzle) {
        // Check if the puzzle is valid
        if (validatePuzzle(puzzle) && remainingMoves(puzzle).isEmpty()) {
            return true; // The puzzle is valid, and there are no remaining moves
        }
    
        // If both conditions are met, the puzzle is solved
        return false;
    }
    
    
    

    /**
     * Asks the user to enter the path to the text file they want to analyze.
     * Hint:
     * - use the "global"-ish Scanner variable scn to get the response from the
     * user, rather than creating a new Scanner variable within this function.
     * - do not close the "global"-ish Scanner so that you can use it in other
     * functions
     *
     * @return The file path that the user enters, e.g.
     *         "sudoku_puzzle.csv"
     */
    public static String getFilepathFromUser() {
        
        
        System.out.println("What file would you like to open?");
        String filepath = scn.nextLine();
        return filepath;
    }

/**
     * Find all of the remaining moves as an ArrayList of arrays, where each array
     * is of size 2. Any position in the Sudoku board where there is a 0 is a valid
     * move. Note that the moves should be sorted by smallest row and then smallest
     * column, i.e. lexicographical order.
     *
     * @param puzzle the Sudoku puzzle
     * @return ArrayList of all remaining moves
     */
    public static ArrayList<int[]> remainingMoves(int[][] puzzle) {
        ArrayList<int[]> moves = new ArrayList<>();
        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[0].length; col++) {
                if (puzzle[row][col] == 0) {
                    int[] move = {row, col};
                    moves.add(move);
                }
            }
        }
    
        // Sort the moves in lexicographical order
        Collections.sort(moves, (a, b) -> {
            if (a[0] == b[0]) {
                return Integer.compare(a[1], b[1]);
            } else {
                return Integer.compare(a[0], b[0]);
            }
        });
    
        return moves;
    }
    

    /**
     * Print out a list of the remaining moves.
     * If no moves are left, print "No moves left!", otherwise print
     * "Remaining moves:" followed by all possible moves.
     *
     * Possible output:
     * ```
     * Remaining moves:
     * (0,0) (4,2) (5,6)
     * ```
     * or
     * ```
     * No moves left!
     * ```
     *
     * The method should make use of `remainingMoves()`.
     *
     * @param puzzle the Sudoku puzzle
     */
    public static void printRemainingMoves(int[][] puzzle) {
        ArrayList<int[]> remainingMoves = remainingMoves(puzzle);
        
        if (remainingMoves.isEmpty()) {
            System.out.println("No moves left!");
        } else {
            System.out.print("Remaining moves: ");
            for (int i = 0; i < remainingMoves.size(); i++) {
                int[] move = remainingMoves.get(i);
                System.out.print("(" + move[0] + "," + move[1] + ")");
                if (i < remainingMoves.size() - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println(); // Print a newline after the remaining moves
        }
    }
    

    /**
     * Opens the specified file and returns the text therein. If the file can't be
     * opened, print out the message, "Oh no... can't find the file!"
     *
     * @param filename The path to a CSV file containing a Sudoku puzzle
     * @return A Sudoku puzzle represented by an int[][] array
     */
    public static int[][] getContentsOfFile(String filepath) {
        try {
            Scanner fileScanner = new Scanner(new File(filepath));
    
            // Create a 9x9 Sudoku puzzle grid
            int[][] puzzle = new int[9][9];
    
            for (int row = 0; row < 9; row++) {
                if (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] values = line.split(",");
    
                    if (values.length != 9) {
                        // The row does not have 9 values; it's not a valid Sudoku puzzle
                        fileScanner.close();
                        System.out.println("Invalid Sudoku puzzle");
                        return null; // Return null to indicate failure
                    }
    
                    for (int col = 0; col < 9; col++) {
                        puzzle[row][col] = Integer.parseInt(values[col]);
                    }
                } else {
                    // The file does not have enough lines; it's not a valid Sudoku puzzle
                    fileScanner.close();
                    System.out.println("Invalid Sudoku puzzle");
                    return null; // Return null to indicate failure
                }
            }
    
            fileScanner.close();
            
    
            // // Print the Sudoku puzzle
            // for (int row = 0; row < 9; row++) {
            //     for (int col = 0; col < 9; col++) {
            //         System.out.print(puzzle[row][col] + " ");
            //     }
            //     System.out.println();
            // }
    
            return puzzle; // Return the constructed puzzle
        
    
        } catch (FileNotFoundException e) {
            // In case we fail to open the file, output a friendly message.
            System.out.println("Oh no... can't find the file!");
        }
    
        // If there was an error, or the file format was incorrect, return null.
        return null;
    }
    
    
    

    // prints out the puzzle with a line of "===" above and vertical "|" between
    // number
    // see the example outputs to see how this should look
    /**
     * Print out the puzzle to the command line.
     *
     * - Use the given char[][] `board` and replace the X's by the puzzle numbers.
     * - If the number is 0, print an empty space (' ') instead of the number.
     * - Do not forget to convert the number into the correct ASCII character!
     * - Your output must match exactly the format from the example outputs.
     *
     * @param puzzle the Sudoku puzzle
     */
    public static void printPuzzle(int[][] puzzle) {
        char[][] board = {
            "╔═══════════════════════════════════╗".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║═══════════╬═══════════╬═══════════║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║═══════════╬═══════════╬═══════════║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "╚═══════════════════════════════════╝".toCharArray()
        };
    
        // Define a mapping of puzzle values to ASCII characters
        char[] valueToChar = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    
        int valueIndex = 0;  // Index for valueToChar array
    
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                char cell = board[row][col];
                if (cell == 'X') {
                    // Replace 'X' with the corresponding digit from the puzzle
                    int value = puzzle[row / 2][col / 4];
                    if (value == 0) {
                        // Replace 'X' with a space for value 0
                        board[row][col] = ' ';
                    } else {
                        // Replace 'X' with the digit
                        board[row][col] = valueToChar[value - 1];
                    }
                }
            }
        }
    
        // Print the modified board
        for (char[] row : board) {
            System.out.println(new String(row));
        }
    }

    // validate that row/col are in the right range
    // make sure the piece hasn't been used before
    // if the move invalidated the board then reset the value to 0
    // returns if the move was successful
    /**
     * Try to insert the given value into the puzzle. Make sure that the given
     * position is within the bounds of the puzzle, the position is not already
     * occupied, and the value is within the expected range.
     *
     * Also, use `validatePuzzle()` to make sure that this move does not break the
     * game. If it does, set the value in this position back to 0.
     *
     * @param puzzle the Sudoku puzzle
     * @param row    the row-index where the value should be placed
     * @param col    the column-index where the value should be placed
     * @param value  the value that should be placed at the given (row,col)
     * @return true if the move was successful, false otherwise
     */
    public static boolean makeMove(int[][] puzzle, int row, int col, int value) {
        // Check if row and col are within the bounds of the puzzle
        if (row < 0 || row >= puzzle.length || col < 0 || col >= puzzle[0].length) {
            return false; // Out of bounds
        }
        
        // Check if the cell is already occupied
        if (puzzle[row][col] != 0) {
            return false; // Cell is already occupied
        }
    
        // Check if the value is within the expected range (e.g., 1 to 9)
        if (value < 1 || value > 9) {
            return false; // Invalid value
        }
    
        // Attempt to make the move
        puzzle[row][col] = value;
    
        // Check if the puzzle is still valid
        if (!validatePuzzle(puzzle)) {
            // If the move invalidates the puzzle, reset the cell to 0
            puzzle[row][col] = 0;
            return false; // Move is not successful
        }
    
        // Move is successful
        return true;
    }
    

    /**
     * Check that none of the entries in `counts` is larger than one.
     * The entry at index 0 is ignored. The input array must have a length of 10.
     *
     * @param counts array of length 10
     * @return true if none of the entries is larger than 1 (ignoring index 0)
     */
    public static boolean validateCountData(int[] counts) {
        // Check if the input array has a length of 10
        if (counts.length != 10) {
            throw new IllegalArgumentException("Input array must have a length of 10.");
        }
    
        // Iterate through the elements of the array, starting from index 1
        for (int i = 1; i < counts.length; i++) {
            // If any entry is larger than 1, return false
            if (counts[i] > 1) {
                return false;
            }
        }
    
        // If no entry is larger than 1, return true
        return true;
    }
    

    /**
     * Validate the given row by making sure that no number other than 0 appears
     * more than once.
     *
     * Make use of `validateCountData()`.
     *
     * @param puzzle the Sudoku puzzle
     * @param row    the row that should be validated
     * @return true if no number besides 0 appears more than once
     */
    public static boolean validateRow(int[][] puzzle, int row) {
        // Create an array to store the counts of each number
        int[] counts = new int[10]; // Assuming Sudoku uses numbers 1-9
    
        // Iterate through the specified row
        for (int col = 0; col < puzzle[row].length; col++) {
            int number = puzzle[row][col];
    
            // If the number is not 0, increment its count
            if (number != 0) {
                counts[number]++;
            }
    
            // Check if the count exceeds 1, indicating a duplicate number
            if (counts[number] > 1) {
                return false; // Validation failed
            }
        }
    
        // If we reached this point, the row is valid
        return true;
    }
    

    /**
     * Validate the given column by making sure that no number other than 0 appears
     * more than once.
     *
     * Make use of `validateCountData()`.
     *
     * @param puzzle the Sudoku puzzle
     * @param col    the column that should be validated
     * @return true if no number besides 0 appears more than once
     */
    public static boolean validateColumn(int[][] puzzle, int col) {
        // Create an array to store the counts of each number
        int[] counts = new int[10]; // Assuming Sudoku uses numbers 1-9
    
        // Iterate through the specified column
        for (int row = 0; row < puzzle.length; row++) {
            int number = puzzle[row][col];
    
            // If the number is not 0, increment its count
            if (number != 0) {
                counts[number]++;
            }
    
            // Check if the count exceeds 1, indicating a duplicate number
            if (counts[number] > 1) {
                return false; // Validation failed
            }
        }
    
        // If we reached this point, the column is valid
        return true;
    }

    // make sure each 3x3 block has at most one of any number other than 0
    /**
     * Validate the given block by making sure that no number other than 0 appears
     * more than once.
     *
     * The `blockRow` and `blockCol` must be indices in the range [0,3), where
     * blockRow==0 corresponds to the top row,
     * blockCol==0 corresponds to the left column
     * blockRow==2 corresponds to the bottom row,
     * blockCol==2 corresponds to the right column
     *
     * Make use of `validateCountData()`.
     *
     * @param puzzle   the Sudoku puzzle
     * @param blockRow the block row
     * @param blockCol the block column
     * @return true if no number besides 0 appears more than once
     */
    public static boolean validateBlock(int[][] puzzle, int blockRow, int blockCol) {
        // Create an array to store the counts of each number
        int[] counts = new int[10]; // Assuming Sudoku uses numbers 1-9
    
        // Determine the starting row and column indices for the block
        int startRow = blockRow * 3;
        int startCol = blockCol * 3;
    
        // Iterate through the 3x3 block
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                int number = puzzle[row][col];
    
                // If the number is not 0, increment its count
                if (number != 0) {
                    counts[number]++;
                }
    
                // Check if the count exceeds 1, indicating a duplicate number
                if (counts[number] > 1) {
                    return false; // Validation failed
                }
            }
        }
    
        // If we reached this point, the block is valid
        return true;
    }
    

    /**
     * Validate all rows.
     *
     * Make use of `validateRow()`.
     *
     * @param puzzle the Sudoku puzzle
     * @return true if all rows are valid, false otherwise
     */
    public static boolean validateRows(int[][] puzzle) {
        // Iterate through each row
        for (int row = 0; row < 9; row++) {
            if (!validateRow(puzzle, row)) {
                return false; // If any row is invalid, return false
            }
        }
    
        // If all rows are valid, return true
        return true;
    }
    

    /**
     * Validate all columns.
     *
     * Make use of `validateColumn()`.
     *
     * @param puzzle the Sudoku puzzle
     * @return true if all columns are valid, false otherwise
     */
    public static boolean validateColumns(int[][] puzzle) {
        // Iterate through each column
        for (int col = 0; col < 9; col++) {
            if (!validateColumn(puzzle, col)) {
                return false; // If any column is invalid, return false
            }
        }
    
        // If all columns are valid, return true
        return true;
    }
    

    /**
     * Validate all 3x3 blocks.
     *
     * Make use of `validateBlock()`.
     *
     * @param puzzle the Sudoku puzzle
     * @return true if all blocks are valid, false otherwise
     */
    public static boolean validateBlocks(int[][] puzzle) {
        // Iterate through each 3x3 block
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                if (!validateBlock(puzzle, blockRow, blockCol)) {
                    return false; // If any block is invalid, return false
                }
            }
        }
    
        // If all blocks are valid, return true
        return true;
    }
    

    /**
     * Validate the whole Sudoku puzzle by validating all rows, columns and blocks.
     * A puzzle is considered valid, if none of the non-zero entries appears twice
     * in its column, row, and block.
     *
     * @param puzzle the Sudoku puzzle
     * @return true if all rows, columns, and blocks are valid
     */
    public static boolean validatePuzzle(int[][] puzzle) {
        // Validate all rows
        if (!validateRows(puzzle)) {
            return false; // If any row is invalid, the puzzle is not valid
        }
        
        // Validate all columns
        if (!validateColumns(puzzle)) {
            return false; // If any column is invalid, the puzzle is not valid
        }
        
        // Validate all 3x3 blocks
        if (!validateBlocks(puzzle)) {
            return false; // If any block is invalid, the puzzle is not valid
        }
    
        // If all rows, columns, and blocks are valid, the puzzle is valid
        return true;
    }
} // end of class
