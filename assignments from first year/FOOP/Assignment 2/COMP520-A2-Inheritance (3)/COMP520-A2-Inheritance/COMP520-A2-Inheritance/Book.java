/**
 * A simple model of books
 * 
 * @author  Berk Cihat Yildizcan
 * @version 1.0
 */
public class Book extends Publication
{    
    private String author;      // The author of the book

    /**
     * Create a book. 
     * 
     * @param title     The title of the book.
     * @param author    The author of the book.
     * @param year      The year when the book was published.
     */
    public Book(String title, String author, int year)
    {
        super(title, year); // super-getting it from publication
        this.author = author;
    }
    
    /**
     * Get the author of the book
     *  
     * @return  The author of the book
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * Get the details of the book
     *  
     * @return  The toString format in Publication class and the book details (author)
     */
    public String toString ()
    {
        return super.toString() + ", by " + author;
    }         

    /**
     * Check if the book is the same as the given one.
     * 
     * @param obj The given object.
     * 
     * @return true  if the book and the given object have
     *               the same title, author and year
     *         false otherwise
     */
    public boolean equals(Object obj) {        
        if (obj == this) return true;
        if ( !(obj instanceof Book) )  return false;
        
        // calls the getTitle and getYear from Publication class
        var another = (Book) obj;        
        return getTitle().equals(another.getTitle()) && 
               this.author.equals(another.author) &&
               this.getYear() == another.getYear();
    }            
}
