
/**
 * Superclass of Book and Journal
 *
 * @author Berk Cihat Yildizcan
 * @version 1.0
 */
public class Publication
{
    private String title; // Title of the publication
    private int year; //Year of the publication

    /**
     * Constructor for objects of class Publication
     */
    public Publication(String title, int year )
    {
        // initialise instance variables
        this.title = title;
        this.year = year;
    }
    
    /**
     * Get the title of the publication
     */
    public String getTitle(){
        return title;
    }
    
    /**
     * Gets the year of the publication
     */
    public int getYear(){
        return year;
    }
    
    /**
     * Returns the title and year in a certain format
     * that is used in the toString for Book and Journal classes
     */
    public String toString(){
        return getTitle() + ", " + getYear();
    }
}
