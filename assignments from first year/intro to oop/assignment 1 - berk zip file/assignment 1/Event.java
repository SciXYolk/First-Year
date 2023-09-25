
/**
 * An event system that stores details of an event.
 *
 * @author Berk Cihat Yildizcan
 * @version 1.0
 */
public class Event
{
    // The capacity of the event room.
    private int capacity;
    // The amount of people that register into the event.
    private int numOfRegistration;
    // Allow to name the event name.
    private String event;
    // Allow to name the room name.
    private String room;

    /**
     * Creates a machine that will allow the user
     * to enter the details of an event.
     */
    public Event(String eventName,String roomName,int capacityOfRoom)
    {
        // initialise instance variables
        event = eventName;
        room = roomName;
        capacity = capacityOfRoom;
        numOfRegistration = 0;
    }

    /**
     * Returns the capacity of the room.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Return the amount of registrations that has been registered.
     */
    public int getRegistrations() {
        return numOfRegistration;
    }

    /**
     * Returns the name of the event.
     */
    public String getEventName() {
        return event;
    }

    /**
     * Returns the name of the room.
     */
    public String getRoomName() {
        return room;
    }

    /**
     * Allows the user to add more registrations.
     * Check if more registrations can be added.
     * Returns the new amount of registrations.
     */
    public void register()
    {
        if (numOfRegistration < capacity) {
            numOfRegistration = numOfRegistration + 1;
        }
        else {
            System.out.println("The room is full.");
        }
    }

    /**
     * Allows the user get rid of registrations.
     * Check if there are any registrations to get rid of.
     * Returns the new amount of registrations.
     */
    public void deregister()
    {
        if (numOfRegistration > 0 ) {
            numOfRegistration = numOfRegistration - 1;
        }
        else {
            System.out.println("There are no registrations.");
        }
    }

    /**
     * Allows user to change the room names and capacity of room.
     * Check if there are more spaces available from the registered amount.
     */
    public void changeRoom(String roomName, int capacityOfRoom){
        if (capacityOfRoom >= numOfRegistration){
            room=roomName;
            capacity=capacityOfRoom;
        }
        else {
            System.out.println("Unable to change the room.");
        }
    }

    /**
     * Check if theres space left in the room.
     */
    public boolean isSpace(int capacityOfRoom){
        int spaceLeft= capacity - numOfRegistration;
        if (capacityOfRoom <= 0){
            return false;
        }
        if (capacityOfRoom <= spaceLeft){
            return true;
        }  
        else {
            return false;
        }

    }

    /**
     * Prints the details of the event that has been inserted.
     */
    public void printEventDetails(){
        System.out.println(event + " in " + room + ". " + numOfRegistration + " registered. " + "Capacity " + capacity + ".");
    }

}
