import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/**
 * A database for a collection of publications,
 * e.g. books, journals, etc.
 *
 * @author Berk Cihat Yildizcan
 * @version 1.0
 */
public class Database {
    private ArrayList<Publication> publicationList; //A collection of publications

    /**
     * Create a new database
     */
    public Database() {
        publicationList = new ArrayList<Publication>();
    }

    /**
     * Adds publication to list of publciations
     * adds only if it doesnt already exist
     * 
     */
    public void addPublication(Publication publication) {
        boolean exists = false; // exists as false
        for(Publication p : publicationList){ //iterates over publicationList
            if(p.equals(publication)){ // if found already existing
                exists = true; //sets as true
                System.out.println("This already exists in the list: " + p);
                //print out it already exists, shows the it in the list
            }
        }
        
        if (!exists) { //if not in list
            publicationList.add(publication); // adds to list
        }
    }

    /**
     * Get the total number of publications
     *
     * @return The total number of publications
     */
    public int getTotal() {
        return publicationList.size(); //gets the total number
    }
    
    /**
     * Gets list of books by said author
     * 
     * @return ArrayList of Book objects by said author
     */
    public ArrayList<Book> getListOfBooks(String author){
        // empty array list to store matching books
        ArrayList<Book> bookList = new ArrayList<Book>();
        for(Publication p : publicationList){ //iterates over publicationList
            if(p instanceof Book){ // if current publication object is an instance of book class
                Book book = (Book) p; // casts Publication object to a Book object
                                      // assigns new variable called book
                if(book.getAuthor().equals(author)){ // check if author matches autor in parameter
                    bookList.add(book); // book added to bookList
                }
            }
        }
        return bookList;
    }
    
    /**
     * Gets number of published journals
     * 
     * @return the amount of published journals
     */
    public int numPublishedJournals(int month, int year){
        int counter = 0; // counter set to 0
        for(Publication p : publicationList){ // iterates over publicationList
            if(p instanceof Journal){ //if current publication object is an instance of Journal class
                Journal journal = (Journal) p; //casts publication object to journal object
                if(journal.getMonth() == month && journal.getYear() == year){
                    counter++;
                    //if the month and year is equal to parameter
                    // increments by 1
                }
            }
        }
        return counter;
    }
    

    /**
     * Print the details of all publications in alphabetical order
     */
    public void printList() {
        //sort publicationList in alphabetical order
        //creates new comparator
        Collections.sort(publicationList, new Comparator<Publication>(){
            public int compare(Publication p1, Publication p2){
                    return p1.getTitle().compareTo(p2.getTitle());
                    //compare publication objects based on title and returns it
            }
        });
        for (var thePublications: publicationList) { //iterates over sorted publicationList
            System.out.println(thePublications); //prints each Publication object 
        }
        System.out.println("Total number of publications: " + getTotal()); 
        // print total number of publication 
    }
}
