import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

/**
 * The test class AssistantCentreTest.
 * 
 * A sample of tests to check the basic functionality of the
 * AssistantCentre class.
 * However, note that a more thorough set of tests will likely
 * be used when your work is assessed.
 *
 * @author David J. Barnes (d.j.barnes@kent.ac.uk)
 * @version 2022.11.05
 */
public class AssistantCentreTest
{
    // The centre to be tested.
    private AssistantCentre centre;
    // A generator of random values.
    private Random rand;
    // Shadow copy of the assistants.
    private Vector<Assistant> shadow;
    private int numAssistants;
    // The number of locations.
    private int numLocations;
    // The chosen location for testing.
    private int chosenLocation;
    // The number of assistants in the chosen location.
    private int numberInChosenLocation;
    
    /**
     * Constructor for test class AssistantCentreTest
     */
    public AssistantCentreTest()
    {
        rand = new Random();
        // The remaining fields will be initialised properly
        // in the setup method before each test.
        centre = null;
        shadow = new Vector<>();
    }

    /**
     * Sets up the test fixture.
     * Generates random values for the assistants.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        centre = new AssistantCentre();
        numLocations = 4 + rand.nextInt(5);
        numAssistants = numLocations * 5;
        chosenLocation = 1 + rand.nextInt(numLocations);
        numberInChosenLocation = 0;
        for(int t = 1; t <= numAssistants; t++) { 
            int location = 1 + rand.nextInt(numLocations);
            shadow.add(makeAssistant(100 + t, location, rand.nextBoolean()));
            if(location == chosenLocation) {
                numberInChosenLocation++;
            }
        }
        while(numberInChosenLocation < 2) {
            // Ensure sufficient, for testing purposes.
            numAssistants++;
            boolean available = rand.nextBoolean();
            shadow.add(makeAssistant(100 + numAssistants, chosenLocation, available));
            numberInChosenLocation++;
       }

    }
    
    /**
     * Make a new assistant.
     */
    private Assistant makeAssistant(int id, int location, boolean available)
    {
        Assistant assistant = new Assistant(id, location);
        if(available) {
            assistant.setAvailable();
        }
        else {
            assistant.setOccupied();
        }
        centre.addAssistant(assistant);
        return assistant;
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        shadow.clear();
    }

    @Test
    /**
     * Test that the number of assistants in the centre is correct.
     */
    public void size()
    {
        assertEquals(String.format("The number of assistants should be %d", numAssistants),
                     numAssistants,centre.getNumberOfAssistants());
    }

    @Test
    /**
     * Test that there are no assistants for an invalid location.
     */
    public void sizeLocation0()
    {
        assertEquals(String.format("The number of assistants should be %d", 0),
                     0, centre.getNumberOfAssistants(0));
    }

    @Test
    /**
     * Test the number of assistants for location 1.
     */
    public void sizeChosenLocation()
    {
        assertEquals(String.format("The number of assistants should be %d", numberInChosenLocation),
                     numberInChosenLocation, 
                     centre.getNumberOfAssistants(chosenLocation));
    }
    
    /**
     * Test the list method.
     * Note that this method does not check the
     * format of the output.
     */
    public void list()
    {
        centre = new AssistantCentre();
        makeAssistant(1, 2, false);
        makeAssistant(5, 1, true);
        centre.list();
    }

    @Test
    /**
     * Test that removing assistants for an invalid id
     * works ok.
     */
    public void remove0()
    {
        assertEquals(numAssistants, centre.getNumberOfAssistants());
        assert(!centre.removeAssistant(rand.nextInt(100)));
        assertEquals(numAssistants, centre.getNumberOfAssistants());
    }

    @Test
    /**
     * Test removal of assistants from the chosen location.
     */
    public void removeAssistant()
    {
        // Select a random Assistant to be removed.
        Assistant selected = shadow.get(rand.nextInt(shadow.size()));
        assert(centre.removeAssistant(selected.getIdentity()));
        // Make sure it cannot be removed twice.
        assert(!centre.removeAssistant(selected.getIdentity()));
    }
    
    @Test
    /**
     * Test movement of an assistant to another location.
     */
    public void moveAssistant()
    {
        // Select a random Assistant to be moved.
        Assistant selected = shadow.get(rand.nextInt(shadow.size()));
        int oldLocation = selected.getLocation();
        boolean oldStatus = selected.isAvailable();
        centre.setAvailability(selected.getIdentity(), oldLocation + 1, !oldStatus);
        assertEquals(oldLocation + 1, selected.getLocation());
        assertEquals(!oldStatus, selected.isAvailable());
    }
    
    @Test
    /**
     * Find the nearest assistant when there are none.
     */
    public void findNoAssistants()
    {
        shadow.clear();
        centre = new AssistantCentre();
        Assistant assistant = centre.findNearestAvailable(1);
        assertEquals(null, assistant);
    }
    
    @Test
    /**
     * Find the nearest assistant when there are none available.
     */
    public void findAssistantNoneAvailable()
    {
        shadow.clear();
        centre = new AssistantCentre();
        makeAssistant(1, 1, false);
        assertEquals(null, centre.findNearestAvailable(1));
    }

    
    @Test
    /**
     * Find the nearest assistant when there is one available.
     */
    public void findAssistantOneAvailable()
    {
        shadow.clear();
        centre = new AssistantCentre();
        makeAssistant(1, 1, false);
        Assistant assistant = makeAssistant(2, 1, true);
        assertEquals(assistant, centre.findNearestAvailable(1));
    }

    
    @Test
    /**
     * Find the nearest assistant when there is more than one available.
     */
    public void findAssistantLowest()
    {
        shadow.clear();
        centre = new AssistantCentre();
        makeAssistant(2, 1, true);
        Assistant assistant = makeAssistant(1, 1, true);
        assertEquals(assistant, centre.findNearestAvailable(1));
    }
    
    @Test
    /**
     * Find the nearest assistant when there are none.
     */
    public void findAssistantLowestMultiple()
    {
        shadow.clear();
        centre = new AssistantCentre();
        for(int id = 5; id >= 2; id--) {
            makeAssistant(id, 1, true);
        }
        Assistant assistant = makeAssistant(1, 1, true);
        assertEquals(assistant, centre.findNearestAvailable(1));
    }
    
    @Test
    /**
     * Find the nearest assistant in neighbouring locations.
     */
    public void findAssistantNeighbours()
    {
        shadow.clear();
        centre = new AssistantCentre();
        Assistant assistant = makeAssistant(1, 1, true);
        makeAssistant(2, 3, true);
        assertEquals(assistant, centre.findNearestAvailable(2));
    }
    
    @Test
    /**
     * Find the nearest assistant in neighbouring locations
     * when only one is available.
     */
    public void findAssistantNeighboursOneAvailable()
    {
        shadow.clear();
        centre = new AssistantCentre();
        makeAssistant(1, 1, false);
        Assistant assistant = makeAssistant(2, 3, true);
        assertEquals(assistant, centre.findNearestAvailable(2));
    }
    
    @Test
    /**
     * Find the nearest assistant in neighbouring locations.
     */
    public void findAssistantNeighboursFurther()
    {
        shadow.clear();
        centre = new AssistantCentre();
        makeAssistant(1, 2, false);
        Assistant assistant = makeAssistant(5, 1, true);
        assertEquals(assistant, centre.findNearestAvailable(3));
    }
    
    @Test
    /**
     * Find the nearest assistant in neighbouring locations.
     */
    public void findAssistantMultipleNeighbours()
    {
        Collections.shuffle(shadow);
        Assistant assistant = shadow.get(0);
        assistant.setAvailable();
        for(int i = 1; i < shadow.size(); i++) {
            shadow.get(i).setOccupied();
        }
        assertEquals(assistant, centre.findNearestAvailable(assistant.getLocation()));
    }
}




