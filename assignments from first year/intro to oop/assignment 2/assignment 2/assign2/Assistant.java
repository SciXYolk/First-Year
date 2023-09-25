/**
 * Model an assistant who is available to help people.
 * @author David J. Barnes (d.j.barnes@kent.ac.uk)
 * @version 2022.11.05
 */
public class Assistant
{
    // The assistant's ID.
    private final int identity;
    // The location the assistant is currently in.
    private int location;
    // Whether the assistant is available or not.
    private boolean available;

    /**
     * Create an Assistant object.
     * @param identity The assistant's ID.
     * @param location The location the assistant is currently in.
     */
    public Assistant(int identity, int location)
    {
        this.identity = identity;
        this.location = location;
        this.available = true;
    }
    
    /**
     * Get the ID.
     * @return The ID.
     */
    public int getIdentity()
    {
        return identity;
    }

    /**
     * Get the location.
     * @return the location.
     */
    public int getLocation()
    {
        return location;
    }
    
    /**
     * Return whether the assistant is available or not.
     * @return true if available, false otherwise.
     */
    public boolean isAvailable()
    {
        return available;
    }
    
    /**
     * Set the assistant's status to being available.
     */
    public void setAvailable()
    {
        available = true;
    }
    
    /**
     * Set the assistant's status to being occupied.
     */
    public void setOccupied()
    {
        available = false;
    }
    
    /**
     * Move to the given location.
     */
    public void moveTo(int location)
    {
        if(location > 0) {
            this.location = location;
        }
    }

    /**
     * Get the formatted details.
     * @return the formatted details.
     */
    public String getDetails()
    {
        return String.format("Assistant %d is in location %d and is %savailable.",             
                             identity, location, available ? "" : "not ");
    }    
    
    /**
     * Return the formatted details.
     * @return the formatted details.
     */
    public String toString()
    {
        return getDetails();
    }
}
