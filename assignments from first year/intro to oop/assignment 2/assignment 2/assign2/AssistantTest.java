import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

/**
 * Basic tests of the Assistant class.
 *
 * @author  David J. Barnes
 * @version 2022.11.05
 */
public class AssistantTest
{
    // A generator of random values.
    private Random rand;
    // The attributes of an Assistant.
    private int identity;
    private int location;
    private boolean available;
    // The Assistant to be tested.
    private Assistant assistant;

    /**
     * Default constructor for test class AssistantTest
     */
    public AssistantTest()
    {
        rand = new Random();
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        identity = 100 + rand.nextInt(100);
        location = 1 + rand.nextInt(6);
        available = rand.nextBoolean();
        assistant = new Assistant(identity, location);
        if(available) {
            assistant.setAvailable();
        }
        else {
            assistant.setOccupied();
        }
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    /**
     * Test the location accessor.
     */
    public void testLocation()
    {
        assertEquals(location, assistant.getLocation());
    }

    @Test
    /**
     * Test the available accessor.
     */
    public void testAvailable()
    {
        assertEquals(available, assistant.isAvailable());
    }

    @Test
    /**
     * Test the identity accessor.
     */
    public void testIdentity()
    {
        assertEquals(identity, assistant.getIdentity());
    }

    @Test
    /**
     * Test the getDetails method.
     */
    public void testDetails()
    {
        assertEquals(String.format("Assistant %d is in location %d and is %savailable.",             
                             identity, location, available ? "" : "not "), 
                     assistant.getDetails());
    }
}




