import java.util.ArrayList;
import java.util.Iterator;

/**
 * Keep track of multiple assistants.
 * @author Berk Yildizcan
 * @version 05/11/2022
 */
public class AssistantCentre
{
    private ArrayList<Assistant> assistants;

    /**
     * Create a AssistantCentre object.
     */
    public AssistantCentre()
    {
        assistants = new ArrayList<>();
    }

    /**
     * Add a new assistant to the collection.
     * @param assistant The assistant.
     */
    public void addAssistant(Assistant assistant)
    {
        assistants.add(assistant);
    }

    /**
     * Get the number of assistants.
     * @return the number of assistants in the collection.
     */
    public int getNumberOfAssistants()
    {
        return assistants.size(); 
    }

    /**
     * Get the number of assistants in the given location.
     * @param location The location of the assistants.
     * @return the number of assistants in the collection
     *         who in the given location.
     */
    public int getNumberOfAssistants(int location)
    {
        int numberOfAssistants = 0;
        for(Assistant a: assistants) {   //loop assistance in array list
            if(a.getLocation() == location){  //check if locations are equal
                numberOfAssistants++; //increment number of assistants
            }
        }
        return numberOfAssistants;
    }

    /**
     * List all of the assistants, one per line.
     * This uses the Assistant objects’ getDetails
     * method to format the details.
     */
    public void list()
    {
        for(Assistant a: assistants){
            System.out.println(a.getDetails()); //prints details of assistant
        }
    }

    /**
     * Set the availability of the assistant whose identity is given.
     * @param id The assistant's identity.
     * @param location The assistant's new location.
     * @param available Whether they are now available or not.
     */
    public void setAvailability(int identity, int location, boolean available)
    {
        for(Assistant a: assistants){
            if(a.getIdentity() == identity){ //check if identity is equal
                a.moveTo(location); //changes the location of the identity
                if(available == true){ //check if availability equals true
                    a.setAvailable(); //sets the availability as true
                }
                else{ //checks if the availability equals false 
                    a.setOccupied(); //sets the availability as false
                }
            }
        }
    }

    /**
     * Remove the assistant with the given identity.
     * @param day The id of the assistant to be removed.
     * @return true if an assistant was found and removed,
     *         false otherwise.
     */
    public boolean removeAssistant(int identity){
        for(int i = 0; i < assistants.size(); i++){ //loop incrementing by 1 each time
            //index the assistants list by i
            //check if the identity is equal to identity passed in method
            if(assistants.get(i).getIdentity()== identity){ 
                assistants.remove(i); //removes the assistant
                return true; //returns true
            }
        }    
        return false; //if not matching identity is found, returns as false
    }

    /**
     * Find the nearest available assistant to the given location.
     * Distance is measured by the absolute value of the
     * difference between two locations.
     * For instance, the distance between locations 1 and 5 is 4,
     * and the distance between locations 6 and 4 is 2.
     * If more than one assistant is available and nearest to the given
     * location then the one with the lowest identity must be returned.
     * @param location Look for the assistant nearest to this location.
     * @return the nearest assistant, or null if there are no available assistants.
     */
    public Assistant findNearestAvailable(int location)
    {
        Assistant closest = null;
        double closestDistance = Double.MAX_VALUE; // make sure every value replaces distance
        for(int l = 0; l < assistants.size(); l++){
            Assistant assistant = assistants.get(l);
            if(assistant.isAvailable()){
                //calculate the distance with the Math.abs method to ensure it is always a positive value
                double distance = Math.abs(assistant.getLocation() - location);
                // update values if assistant is closer or same distance with a lower identity
                if(distance < closestDistance || (distance == closestDistance && closest.getIdentity() > assistant.getIdentity())){
                    closest = assistant;
                    closestDistance = distance;
                }
            }
        }
        return closest;
    }
    
}
