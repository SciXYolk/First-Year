import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * The test class WordTrackerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class WordTrackerTester
{
   private WordTracker testInstance;

    /**
     * Default constructor for test class WordTrackerTester
     */
    public WordTrackerTester()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        testInstance = new WordTracker();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    /**
     * Test getCount when no words have been added.
     */
    public void testEmpty()
    {
        assertEquals(0, testInstance.getCount("the"));
    }

    

    @Test
    /**
     * Test whether the equals method of String is being used
     * to test for equality.
     */
    public void testEquals()
    {
        testInstance.addWord(new String("t") + new String("h") + new String("e"));
        assertEquals(1, testInstance.getCount("the"), "equals() might not be being used to compare strings.");
    }
    
    @Test
    /**
     * Test for a single occurrence.
     */
    public void testOneWord()
    {
        testInstance.addWord("the");
        assertEquals(1, testInstance.getCount("the"));
    }
    
    @Test
    /**
     * Test for case-sensitivity.
     */
    public void testCaseSensitive()
    {
        testInstance.addWord("the");
        assertEquals(1, testInstance.getCount("the"));
        assertEquals(0, testInstance.getCount("THE"));
    }

    @Test
    /**
     * Test for two occurrences.
     */
    public void testTwoWords()
    {
        testInstance.addWord("the");
        testInstance.addWord("the");
        assertEquals(2, testInstance.getCount("the"));
        assertEquals(0, testInstance.getCount("cat"));
    }

    @Test
    /**
     * Test for different numbers of occurrences of different words.
     */
    public void testMultiple()
    {
        testInstance.addWord("the");
        testInstance.addWord("cat");
        testInstance.addWord("the");
        testInstance.addWord("dog");
        testInstance.addWord("cat");
        testInstance.addWord("the");
        assertEquals(3, testInstance.getCount("the"));
        assertEquals(2, testInstance.getCount("cat"));
        assertEquals(1, testInstance.getCount("dog"));
        assertEquals(0, testInstance.getCount("horse"));
    }


    @Test
    /**
     * Test getCount when there is a substring match.
     */
    public void testContains()
    {
        testInstance.addWord("the");
        testInstance.addWord("there");
        testInstance.addWord("catheter");
        testInstance.addWord("writhe");
        assertEquals(1, testInstance.getCount("the"));
    }

    @Test
    /**
     * Check that the fields of WordTracker are instance variables
     * rather than static variables.
     */
    public void testNonStatic()
    {
         WordTracker shadow = new WordTracker();
         // Only run the test if addWord and getCount work ok.
         boolean okToProceed;
         try {
             okToProceed = shadow.getCount("the") == 0;
             okToProceed = okToProceed && testInstance.getCount("the") == 0;
             testInstance.addWord("the");
             okToProceed = okToProceed && testInstance.getCount("the") == 1;
         }
         catch(Exception ex) {
             okToProceed = false;
         }
         if(okToProceed) {
             assertEquals(0, shadow.getCount("the"));
         }
         else {
             assertTrue(true);
         }
    }
    
    @Test
    /**
     * Test that wordPair returns false when neither word has occurred.
     */
    public void testPairEmpty()
    {
        assertFalse(testInstance.wordPair("the", "cat"));
    }
    
    @Test
    /**
     * Test that wordPair return false when only the first word has occurred.
     */
    public void testPairFirst()
    {
        testInstance.addWord("the");
        assertFalse(testInstance.wordPair("the", "cat"));
    }
    
    @Test
    /**
     * Test that wordPair return false when only the second word has occurred.
     */
    public void testPairSecond()
    {
        testInstance.addWord("cat");
        assertFalse(testInstance.wordPair("the", "cat"));
    }
    
    @Test
    /**
     * Test that wordPair return false when there is an intervening word.
     */
    public void testPairInvervening()
    {
        testInstance.addWord("the");
        testInstance.addWord("house");
        testInstance.addWord("cat");
        assertFalse(testInstance.wordPair("the", "cat"));
    }
    
    @Test
    /**
     * Test that wordPair matches at the start of the input.
     */
    public void testPairStart()
    {
        testInstance.addWord("the");
        testInstance.addWord("cat");
        testInstance.addWord("the");
        testInstance.addWord("dog");
        assertTrue(testInstance.wordPair("the", "cat"));
    }
    
    @Test
    /**
     * Test that wordPair matches in the middle of the input.
     */
    public void testPairMiddle()
    {
        testInstance.addWord("the");
        testInstance.addWord("rabbit");
        testInstance.addWord("the");
        testInstance.addWord("cat");
        testInstance.addWord("the");
        testInstance.addWord("dog");
        assertTrue(testInstance.wordPair("the", "cat"));
    }
    
    @Test
    /**
     * Test that wordPair matches at the end of the input.
     */
    public void testPairEnd()
    {
        testInstance.addWord("the");
        testInstance.addWord("rabbit");
        testInstance.addWord("the");
        testInstance.addWord("cat");
        assertTrue(testInstance.wordPair("the", "cat"));
    }
        
    @Test
    /**
     * Test that wordPair returns true at an even occurrence.
     */
    public void testPairEven()
    {
        testInstance.addWord("the");
        testInstance.addWord("the");
        testInstance.addWord("the");
        testInstance.addWord("the");
        testInstance.addWord("cat");
        testInstance.addWord("the");
        testInstance.addWord("dog");
        assertTrue(testInstance.wordPair("the", "cat"));
    }
        
    @Test
    /**
     * Test that wordPair returns true at an odd occurrence.
     */
    public void testPairOdd()
    {
        testInstance.addWord("the");
        testInstance.addWord("the");
        testInstance.addWord("the");
        testInstance.addWord("the");
        testInstance.addWord("the");
        testInstance.addWord("cat");
        testInstance.addWord("the");
        testInstance.addWord("dog");
        assertTrue(testInstance.wordPair("the", "cat"));
    }
    
    @Test
    /**
     * Test that wordPair returns false if the order is reversed.
     */
    public void testPairReversed()
    {
        testInstance.addWord("cat");
        testInstance.addWord("the");
        testInstance.addWord("the");
        testInstance.addWord("dog");
        assertFalse(testInstance.wordPair("the", "cat"));
    }
    
    @Test
    /**
     * Test that wordPair behaves correctly when the first word
     * is the last one added.
     */
    public void testPairNotAtEnd()
    {
        testInstance.addWord("one");
        testInstance.addWord("rabbit");
        testInstance.addWord("the");
        assertFalse(testInstance.wordPair("the", "cat"));
    }
    @Test
    /**
     * Test substring count with no matches.
     */
    public void testNoSubstrings()
    {
        assertEquals(0, testInstance.getSubstringCount("the"));
    }
    
    @Test
    /**
     * Test substring count with a single identical match.
     */
    public void testSubstringSame()
    {
        testInstance.addWord("the");
        assertEquals(1, testInstance.getSubstringCount("the"));
    }
    
    @Test
    /**
     * Test substring count with a single different match.
     */
    public void testOneSubstring()
    {
        testInstance.addWord("there");
        assertEquals(1, testInstance.getSubstringCount("the"));
    }
    
    @Test
    /**
     * Test that substring is matching the right way around.
     */
    public void testSubstringReversed()
    {
        testInstance.addWord("the");
        assertEquals(0, testInstance.getSubstringCount("there"));
    }
    
    @Test
    /**
     * Test substring count with a single different match.
     */
    public void testMultipleSubstrings()
    {
        testInstance.addWord("the");
        testInstance.addWord("there");
        testInstance.addWord("therapy");
        assertEquals(3, testInstance.getSubstringCount("he"));
    }
}


