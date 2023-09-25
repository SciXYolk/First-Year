import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * A simple 'driver' for the WordTracker class.
 * 
 * Split a 'document' into individual words and pass them
 * to a WordTracker to be analysed.
 * 
 * In this example, the 'document' is a String literal in
 * the main method, but this could easily be changed to an
 * external file, if desired (e.g., use a Scanner).
 * 
 * Print a few sample outputs.
 *
 * @author David J. Barnes
 * @version 2022.12.14
 */
public class WordTrackerMain
{
    // The tracker. It must be static to be usable from
    // static methods.
    private static final WordTracker tracker = new WordTracker();
    
    public static void main(String[] args)
        throws IOException
    {
        String originalText = null;
        if(args.length == 1) {
            String filename = args[0];
            File inputFile = new File(filename);
            if (inputFile.exists()) {
                List<String> lines = Files.readAllLines(Paths.get(filename));
                originalText = "";
                for(String line : lines) {
                    originalText += line + "\n";
                }
            }   
            else {
                System.err.println(filename + " not found.\n");
                System.exit(1);
            }
        }
        if(originalText == null) {
            originalText = 
                "If only there were a way to find the number of artificial trees\n" +
                "and weeds and seeds in a box of tea leaves.";
        }
        
        simpleDemo(originalText);
    }
    
    /**
     * A simple demonstration of each method.
     * @param The original text to be tracked.
     */
    private static void simpleDemo(String originalText)
    {
        String[] words = originalText.split("[ ,.;:?\n]+");
        for(String word : words) {
            if(! word.isEmpty()) {
                tracker.addWord(word);
            }
        }
        
        // Simple demonstration of getCount.
        for(String word : List.of("If", "and")) {
            System.out.println("Count of " + word + " " + tracker.getCount(word));
        }
        
        // Simple demonstration of getSubstringCount.
        String substring = "ee";
        System.out.println("Substrings of " + substring + " " + 
                            tracker.getSubstringCount(substring));
        
        // Simple demonstration of wordPair.
        String[][] wordPairs = {
            {"trees", "weeds"}, 
            {"tea", "leaves"},
        };
        for(String[] pair : wordPairs) {
            String firstWord = pair[0];
            String secondWord = pair[1];
            if(tracker.wordPair(firstWord, secondWord)) {
                System.out.println(firstWord + " was followed by " + secondWord);
            }
            else {
                System.out.println(firstWord + " was not followed by " + secondWord);
            }
        }
    }
        
}
