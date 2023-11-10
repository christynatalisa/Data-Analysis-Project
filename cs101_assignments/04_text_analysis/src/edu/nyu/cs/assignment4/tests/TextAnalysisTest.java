// DO NOT TOUCH THIS FILE!
package edu.nyu.cs.assignment4.tests;

// import the class to be tested
import edu.nyu.cs.assignment4.TextAnalysis;

// import junit 4 testing framework
import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;

import java.util.Arrays;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule; // system rules lib - useful for capturing system output
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;


public class TextAnalysisTest {
    @ClassRule
    public static final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Rule public final SystemOutRule systemOutRule = new SystemOutRule();

    @Before
    public void init() {
        // any pre-test setup here
    }

    @Test
    public void testGetFilePathFromUser() {
        String[] expecteds = {
            "test/data/test1.txt", "test/data/trump_speech_010621.txt", "foo/bar/baz"};
        for (String expected : expecteds) {
            systemInMock.provideLines(expected);
            try {
                String actual = TextAnalysis.getFilepathFromUser().trim();
                // System.out.println(actual);
                if (!actual.equals(expected)) {
                    assertEquals(
                        "Expected the getFilePathFromUser() function to return the file path we entered",
                        "Returned " + actual + " instead.");
                }
            } catch (Exception e) {
                assertEquals("Expected the getFilePathFromUser() not to crash when called",
                    "The function threw an Exception when called.");
            }
        }
    }

    @Test
    public void testGetTicsFromUser() {
        String[] mockInputs = {
            "you,know,um,sort,of", "you,know ,foo,  suppa", "well,now, then, what"};
        String[][] expecteds = {{"you", "know", "um", "sort","of"}, {"you","know", "foo", "suppa"},
            {"well", "now", "then", "what"}};
        for (int i = 0; i < mockInputs.length; i++) {
            String mockInput = mockInputs[i];
            String[] expected = expecteds[i];
            systemInMock.provideLines(mockInput);
            try {
                String[] actual = TextAnalysis.getTicsFromUser();
                if (!(expected.length == actual.length)) {
                    assertEquals("Expected the getTicsFromUser() function to return an array of "
                            + expected.length + " tics, given the input: " + mockInput,
                        "Returned an array of " + actual.length + " instead");
                    return;
                }
                for (int j = 0; j < expected.length; j++) {
                    if (!(expected[j].equals(actual[j]))) {
                        assertEquals(
                            "Expected the getTicsFromUser() function to return an array including the string "
                                + expected[j] + " at position " + j
                                + ", given the input: " + mockInput,
                            "Returned an array with the string " + actual[j] + " at position " + j
                                + " instead.");
                        return;
                    }
                }
            } catch (Exception e) {
                assertEquals("Expected the getTicsFromUser() function not to crash when called",
                    "The function crashed when given the input: " + mockInput);
            }
        }
    }

    @Test
    public void testCountOccurrences() {
        String[] haystacks = {"foo foo bar baz.baz.foo? baz bar,bum,foo foofoo!",
            "this that, the other.  and this too", "which.is? it.is.it.working or.is.it.not"};
        String[] needles = {"foo", "this", "it"};
        int[] expecteds = {4, 2, 3};

        for (int i = 0; i < haystacks.length; i++) {
            String needle = needles[i];
            String haystack = haystacks[i];
            int expected = expecteds[i];
            try {
                int actual = TextAnalysis.countOccurrences(needle, haystack);
                if (actual != expected) {
                    assertEquals("Expected the countOccurrences() function to return " + expected
                            + " when counting the occurrences of '" + needle + "' in the text '"
                            + haystack + "'",
                        "Returned " + actual + " instead.");
                }
            } catch (Exception e) {
                assertEquals("Expected the countOccurrences() function not to crash.",
                    "The function crashed when passed the arguments '" + needle + "' and '"
                        + haystack + "'.");
            }
        }
    }

    @Test
    public void testCalculatePercentages() {
        int[][] mockInputs = {
            {10, 1},
            {1, 10},
            {1, 10},
            {30, 100},
        };
        int[] expecteds = {1000, 10, 10, 30};
        for (int i = 0; i < mockInputs.length; i++) {
            int[] mockInput = mockInputs[i];
            int expected = expecteds[i];
            try {
                int actual = TextAnalysis.calculatePercentage(mockInput[0], mockInput[1]);
                if (expected != actual) {
                    assertEquals("Expected the calculatePercentage() function to return " + expected
                            + ", given the arguments " + mockInput[0] + " and " + mockInput[1],
                        "Instead it returned " + actual);
                }
            } catch (Exception e) {
                assertEquals(
                    "Expected the calculatePercentage() function not to crash when called.",
                    "The function threw an Exception when given the arguments " + mockInput[0]
                        + " and " + mockInput[1]);
            }
        }
    }

    @Test
    public void testCalculateTicDensity() {
        String[][] mockTics = {{"foo", "bar"}, {"goo", "gar"}, {"Boo", "bar", "bAz"}};
        String[] mockTexts = {
            "foo bar, bar foo foo. bar bar foo?",
            "foo goo gar, Foo goo. gar bar foo!",
            "foo bar? goo. gar boo bar. baz Bum? Foo bar? goo. gar boo bar. baz Bum?",
        };
        double[] expecteds = {1, .5, .5};

        for (int i = 0; i < mockTics.length; i++) {
            String[] mockTic = mockTics[i];
            String mockText = mockTexts[i];
            double expected = expecteds[i];
            try {
                double actual = TextAnalysis.calculateTicDensity(mockTic, mockText);
                if (expected != actual) {
                    assertEquals("Expected the calculateTicDensity() function to return " + expected
                            + ", given the arguments " + Arrays.toString(mockTic) + " and "
                            + mockText,
                        "Instead it returned " + actual);
                }
            } catch (Exception e) {
                assertEquals("Expected the calculateTicDensity() function not to crash.",
                    "It crashed when given the arguments " + Arrays.toString(mockTic) + " and "
                        + mockText);
            }
        }
    }

    @Test
    public void testMainTrumpSpeech() {
        systemOutRule.clearLog();
        String filePath = TextAnalysis.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String trumpSpeechPath = filePath + "../trump_speech_010621.txt";
        systemOutRule.enableLog(); // start capturing System.out
        try {
            String[] input = {trumpSpeechPath, "uh,like, um,so"};
            String[] args = {};
            systemInMock.provideLines(input);
            TextAnalysis.main(args);
            String output = systemOutRule.getLogWithNormalizedLineSeparator();
            assertTrue(output.contains("87")); // expects 87 tics
            assertTrue(output.contains("0.01")); // density should be this
            assertTrue(output.contains("32"));// 32 likes
            assertTrue(output.contains("37"));// 37% of tics were likes
            assertTrue(output.contains("55"));// 55 so
            assertTrue(output.contains("63"));// 63% of tics were so
        } catch (Exception e) {
            assertEquals("Expected the main() not to crash when called",
                "The function threw an Exception when called.");
        }
    }

}

