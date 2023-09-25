import java.util.HashMap;
import java.util.ArrayList;

/**
 * Keep track of word counts and word pairs.
 * 
 * DO NOT CHANGE ANY OF THE METHOD NAMES, PARAMETERS
 * OR RETURN TYPES IN THIS FILE. IF YOU DO, IT WILL
 * NOT BE POSSIBLE TO MARK YOUR WORK.
 *
 * @author Berk Cihat Yildizcan
 * @version 19/01/2023
 */
public class WordTracker
{   
    private ArrayList<String> ara;
    private HashMap<String,Integer> hashy;
    /**
     * Constructor for objects of class WordTracker
     */
    public WordTracker()
    {
        hashy = new HashMap<>(); // creates a HashMap
        ara = new ArrayList<>(); // creates an ArrayList
    }
    
    /**
     * Add a word to the analyser.
     * @param word the word to be added.
     */
    public void addWord(String word)
    {
        ara.add(word); // adds word to the array
        
        if(hashy.containsKey(word)){ // if hashmap contains the given word
            hashy.put(word, hashy.get(word) + 1); // increment it by 1
        }
        else{
            hashy.put(word, 1); // else put the value as 1
        }
    }
    
    /**
     * Get the number of times the given word has been seen.
     * @param word The word to be looked up.
     * @return The number of times the word has been seen.
     */
    public int getCount(String word)
    {
        if(hashy.containsKey(word)){ // if hashmap contains the given word
            return hashy.get(word); // get it from the hashmap and return it
        }
        else{
            return 0; // else return 0
        }
    }
    
    /**
     * Return true if firstWord is immediately followed
     * by secondWord in the original text; false otherwise.
     * In other words, return true if, after a call to addWord(firstWord),
     * the next call to addWord was addWord(secondWord).
     * 
     * @param firstWord A possible word added immediately before secondWord.
     * @param secondWord A possible word added immediately after firstWord.
     */
    public boolean wordPair(String firstWord, String secondWord)
    {
        for(int i = 0; i<ara.size() - 1; i++){ // loops till its all right
            String firstPair = ara.get(i); // creates a string group, indexing it to the array
            String secondPair = ara.get(i+1); // same as above but adds 1 to the current index
            if(firstPair.equals(firstWord) && secondPair.equals(secondWord)){
                return true; // if they all equal to the correct string and come 
                             // after each other, return true
            }
        }
        return false; // if one does not equal or one does not come after the next
                      // return false
    }
    
    /**
     * Get the number of times the given string occurs
     * within one of the words in the analyser.
     * Note: the match must be *case insensitive*.
     * @param str The substring to be looked up.
     * @return The number of times the substring occurs.
     */
    public int getSubstringCount(String str)
    {
        int counter = 0; // set the counter as 0
        for(String key: hashy.keySet()){ // keySet iterate through HashMap
            if(key.contains(str)){ 
                counter++; // if the key contains str, then add 1 to the counter
            }
        }
        return counter; // return the number of times string occurs
    }
}



