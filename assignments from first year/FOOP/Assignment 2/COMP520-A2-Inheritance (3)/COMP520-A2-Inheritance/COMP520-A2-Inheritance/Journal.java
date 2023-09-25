/**
 * A simple model of journals
 * 
 * @author  Berk Cihat Yildizcan
 * @version 1.0
 */
public class Journal extends Publication
{
    // The month when the journal was published          
    private int month;

    /**
     * Create a journal. 
     * 
     * @param title     The title of the journal
     * @param month     The month when the journal was published
     * @param year      The year when the journal was published
     */
    public Journal(String title, int month, int year)
    {
        super(title, year); // super-getting it from publication
        this.month = month;
    }
    
    /**
     * Get the month when the journal was published
     *  
     * @return  The month when the journal was published
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * Get the details of the journal
     * 
     * @return  The toString format in Publication class and the journal details (month)
     */
    public String toString()
    {
        return super.toString()+ " (" + getMonthName(month) + ")";      
    }             
    
    /**
     * Check if the journal is the same as the given one.
     * 
     * @param obj The given object.
     * 
     * @return true  if the journal and the given one have the
     *               same title, year and month;
     *         false otherwise
     */
    public boolean equals(Object obj) {        
        if (this == obj) return true;
        if ( !(obj instanceof Journal) ) return false;
        
        // calls the getTitle and getYear from Publication class
        var another = (Journal) obj;
        return this.getTitle().equals(another.getTitle()) && 
               this.month == another.month &&
               this.getYear() == another.getYear();
    }    
    
    /**
     * To get the name of a given month
     * 
     * @param month A given month
     * @return  The month's name
     */
    private String getMonthName(int month) {
        switch (month) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "Unknown";
        }
    }    
}
