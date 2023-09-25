import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * The test class EventTest.
 * Assumptions:
 *     + The constructor's parameters are in the order: eventName, roomName, capacity.
 *     + The setters are called: getEventName, getRoomName, getCapacity and getRegistrations.
 *
 * @author  David J. Barnes
 * @version 2022.10.20
 */
public class EventTest
{
    // Catch the output from System.out and System.err.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    // Error report, if any.
    private String report;
    private static ReflectionTester reflect;

    // The name of the class being tested.
    private static final String CLASS_NAME = "Event";
    // The fields: name and type.
    private static final String[] fields = {
            "F:eventName:java.lang.String",
            "F:roomName:java.lang.String",
            "F:capacity:int",
            "F:registrations:int",
        };

    // The methods: name, return type, params.
    private static final String[] methods = {
            "M:getEventName:java.lang.String:",
            "M:getRoomName:java.lang.String:",
            "M:getCapacity:int:",
            "M:getRegistrations:int:",
            "M:register:void:",
            "M:deregister:void:",
            "M:changeRoom:void:java.lang.String:int",
            "M:isSpace:boolean:int",
            "M:printEventDetails:void:",
        };

    // Randomise data.
    private static final Random rand = new Random();
    // Place holders for generated details.
    private String eventName, roomName;
    private int capacity;

    @BeforeAll
    /**
     * Check for the correct class name.
     */
    public static void reflectionTest()
    {
        try {
            reflect = new ReflectionTester(CLASS_NAME);
        }
        catch(ClassNotFoundException ex) {
            // Class name not found.
            assertTrue(false, "The class must be called " + CLASS_NAME + ".");
        }
    }

    /**
     * Default constructor for test class EventTest
     */
    public EventTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        report = null;
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        if(report != null) {
            //System.err.println(report);
            report = null;
        }

        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    /**
     * Test the initial state of the Event.
     */
    public void testInitialState()
    {
        Event ev = createEvent();
        assertEquals(ev.getEventName(), eventName, "The event name should be " + eventName);
        assertEquals(ev.getRoomName(), roomName, "The room name should be " + roomName);
        assertEquals(ev.getCapacity(), capacity, "The capacity should be " + capacity);
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be " + 0);
        cleanOutput();
    }

    @Test
    /**
     * Test registering one person.
     */
    public void testRegister1()
    {
        Event ev = createEvent();
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        ev.register();
        assertEquals(ev.getRegistrations(), 1, "The registrations should be 1.");
        cleanOutput();
    }

    @Test
    /**
     * Test filling the event.
     */
    public void testRegisterFill()
    {
        int capacity = 5;
        Event ev = createEvent(capacity);
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        cleanOutput();
    }

    @Test
    /**
     * Test overfilling the event.
     */
    public void testRegisterOverfill()
    {
        int capacity = 5;
        Event ev = createEvent(capacity);
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        // One more.
        ev.register();
        assertEquals(ev.getRegistrations(), capacity, "The registrations should be " + capacity);
        String expectedOutput = "The room is full.";
        assertEquals(expectedOutput, outContent.toString().trim(), "The output should be: " + expectedOutput);
        assertEquals("", errContent.toString().trim(), "Nothing should be printed to System.err");
    }
    
    @Test
    /**
     * Test registering and deregistering one person.
     */
    public void testDeregister1()
    {
        Event ev = createEvent();
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        ev.register();
        assertEquals(ev.getRegistrations(), 1, "The registrations should be 1.");
        ev.deregister();
        assertEquals(ev.getRegistrations(), 0, "The registrations should be 0.");
        cleanOutput();
    }
    
    @Test
    /**
     * Test registering and deregistering one person.
     */
    public void testDeregister2()
    {
        Event ev = createEvent();
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        ev.register();
        assertEquals(ev.getRegistrations(), 1, "The registrations should be 1.");
        ev.register();
        assertEquals(ev.getRegistrations(), 2, "The registrations should be 2.");
        ev.deregister();
        assertEquals(ev.getRegistrations(), 1, "The registrations should be 1.");
        cleanOutput();
    }

    @Test
    /**
     * Test filling the event and then deregistering everybody.
     */
    public void testFillAndEmpty()
    {
        int capacity = 5;
        Event ev = createEvent(capacity);
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        for(int r = capacity - 1; r >= 0; r--) {
            ev.deregister();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                (capacity - r) + " registrations.");
        }
        cleanOutput();
    }
    
    @Test
    /**
     * Try deregistering an empty event.
     */
    public void testDeregisterEmptyEvent()
    {
        Event ev = createEvent();
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        ev.deregister();
        assertEquals(ev.getRegistrations(), 0, "The registrations should be 0.");
        String expectedOutput = "There are no registrations.";
        assertEquals(expectedOutput, outContent.toString().trim(), "The output should be: " + expectedOutput);
        assertEquals("", errContent.toString().trim(), "Nothing should be printed to System.err");
    }
    
    /**
     * Test changing for a bigger room.
     */
    @Test
    public void testBiggerRoom1()
    {
        Event ev = createEvent();
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity/2; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        String biggerRoomName = "Bigger room";
        int largerCapacity = 2 * capacity;
        ev.changeRoom(biggerRoomName, largerCapacity);
        assertEquals(ev.getRoomName(), biggerRoomName, "The room name should now be " + biggerRoomName);
        assertEquals(ev.getCapacity(), largerCapacity, "The capacity should now be " + largerCapacity);
        cleanOutput();
    }
    
    /**
     * Test changing for another room that has enough space.
     */
    @Test
    public void testOtherRoom1()
    {
        Event ev = createEvent();
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity/2; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        String otherRoomName = "Another room";
        int otherRoomSize = capacity / 2 + 1;
        ev.changeRoom(otherRoomName, otherRoomSize);
        assertEquals(ev.getRoomName(), otherRoomName, "The room name should now be " + otherRoomName);
        assertEquals(ev.getCapacity(), otherRoomSize, "The capacity should now be " + otherRoomSize);
        cleanOutput();
    }
    
    /**
     * Test changing for another room that has enough space.
     */
    @Test
    public void testOtherRoom2()
    {
        Event ev = createEvent();
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity/2; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        String otherRoomName = "Another room";
        int otherRoomSize = capacity / 2;
        ev.changeRoom(otherRoomName, otherRoomSize);
        assertEquals(ev.getRoomName(), otherRoomName, "The room name should now be " + otherRoomName);
        assertEquals(ev.getCapacity(), otherRoomSize, "The capacity should now be " + otherRoomSize);
        cleanOutput();
    }
    
    /**
     * Test changing for another room that does not have enough space.
     */
    @Test
    public void testSmallerRoom()
    {
        int capacity = 10;
        Event ev = createEvent(capacity);
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity/2; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        String otherRoomName = "Another room";
        int otherRoomSize = capacity / 2 - 1;
        ev.changeRoom(otherRoomName, otherRoomSize);
        assertEquals(ev.getRoomName(), roomName, "The room name should still be " + roomName);
        assertEquals(ev.getCapacity(), capacity, "The capacity should still be " + capacity);
        String expectedOutput = "Unable to change the room.";
        assertEquals(expectedOutput, outContent.toString().trim(), "The output should be: " + expectedOutput);
        assertEquals("", errContent.toString().trim(), "Nothing should be printed to System.err");
    }
    
    @Test
    /**
     * Test when there is sufficient space.
     */
    public void testIsSpace1()
    {
        int capacity = 10;
        Event ev = createEvent(capacity);
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity/2; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        int groupSize = 1;
        assertTrue(ev.isSpace(groupSize), "isSpace should return true for " + groupSize + " more registration.");
        cleanOutput();
    }
    
    @Test
    /**
     * Test when there is just sufficient space.
     */
    public void testIsSpace2()
    {
        int capacity = 10;
        Event ev = createEvent(capacity);
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity/2; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        int groupSize = capacity - (capacity / 2);
        assertTrue(ev.isSpace(groupSize), "isSpace should return true for " + groupSize + " more registrations.");
        cleanOutput();
    }
    
    @Test
    /**
     * Test when there is insufficient space.
     */
    public void testIsSpace3()
    {
        int capacity = 10;
        Event ev = createEvent(capacity);
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity/2; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        int groupSize = capacity - (capacity / 2) + 1;
        assertFalse(ev.isSpace(groupSize), "isSpace should return false for " + groupSize + " more registrations.");
        cleanOutput();
    }
    
    @Test
    /**
     * Test when the group size is 0.
     */
    public void testIsSpaceGroupSize0()
    {
        int capacity = 10;
        Event ev = createEvent(capacity);
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity/2; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        int groupSize = 0;
        assertFalse(ev.isSpace(groupSize), "isSpace should return false for " + groupSize + " more registrations.");
        cleanOutput();
    }
    
    @Test
    /**
     * Test when the group size is 0.
     */
    public void testIsSpaceGroupSizeNegative()
    {
        int capacity = 10;
        Event ev = createEvent(capacity);
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        for(int r = 1; r <= capacity/2; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        int groupSize = -1;
        assertFalse(ev.isSpace(groupSize), "isSpace should return false for " + groupSize + " more registrations.");
        cleanOutput();
    }
    
    @Test
    /**
     * Test the output of printEventDetails().
     */
    public void testPrintEventDetails()
    {
        int capacity = 10;
        Event ev = createEvent(capacity);
        assertEquals(ev.getRegistrations(), 0, "The initial registrations should be 0.");
        int registrations = capacity / 2;
        for(int r = 1; r <= registrations; r++) {
            ev.register();
            assertEquals(ev.getRegistrations(), r, 
                "The registrations should be " + r + " after " + 
                r + " registrations.");
        }
        ev.printEventDetails();
        String expectedOutput = eventName + " in " + roomName + ". " +
                                    registrations + " registered. Capacity " +
                                    capacity + ".";
        assertEquals(expectedOutput, outContent.toString().trim(), "The output should be: " + expectedOutput);
        assertEquals("", errContent.toString().trim(), "Nothing should be printed to System.err");
    }
    

    /**
     * Check that nothing was output.
     */
    private void cleanOutput()
    {
        assertEquals("", outContent.toString().trim(), "Nothing should be printed to System.out");
        assertEquals("", errContent.toString().trim(), "Nothing should be printed to System.err");
    }

    /**
     * Create an event with random elements.
     * NB: The assumption is that the constructor's arguments
     * are in the order: event name, room name, room capacity.
     * @return the event.
     */
    private Event createEvent()
    {
        return createEvent(10 + rand.nextInt(50));
    }

    /**
     * Create an event in a room with the given capacity.
     * @param capacity The room's capacity.
     * @return the event.
     */
    private Event createEvent(int capacity)
    {
        eventName = "Event " + rand.nextInt(100);
        roomName = "Room " + rand.nextInt(100);
        this.capacity = capacity;
        return new Event(eventName, roomName, capacity);
    }

    /**
     * Test for the correct class name.
     */
    @Test
    public void testClassName()
    {
        assertTrue(reflect != null, "The class must be called " + CLASS_NAME);
    }

    /**
     * Test for the correct fields.
     */
    //@Test
    public void testFields()
    {
        if(reflect != null) {
            if(fields.length > 0) {
                String fieldReport = reflect.testFields(fields);
                if(! fieldReport.isEmpty()) {
                    fail(fieldReport);
                }
            }
        }
        else {
            fail("Requirements for the fields cannot be checked until the name of the class is correct.");
        }
    }

    /**
     * Test for the correct methods.
     */
    //@Test
    public void testMethods()
    {
        if(reflect != null) {
            String methodReport = reflect.testMethods(methods);
            if(! methodReport.isEmpty()) {
                fail(methodReport);
            }
        }
        else {
            fail("Requirements for the methods cannot be checked until the name of the class is correct.");
        }
    }

    /**
     * Driver for Java assignment analyser.
     * Requirements for fields and methods are defined here.
     * 
     * @author djb
     */
    private static class ReflectionTester {
        private Reflector reflector;

        /**
         * Create a tester for the given class.
         * @param subject The name of the class to be tested.
         */
        public ReflectionTester(String subject)
        throws ClassNotFoundException
        {
            reflector = new Reflector(subject);
        }

        /**
         * Test the fields given their descriptions.
         * Return a report of any issues found.
         * @param fields Descriptions of the required fields.
         * @return A report string, or blank if there are no issues.
         */
        public String testFields(String[] fields)
        {
            try {
                RequiredField[] requiredFields = setupFields(fields);
                return reflector.checkFields(requiredFields);
            }
            catch(ClassNotFoundException ex) {
                return null;
            }
        }

        /**
         * Test the public methods given their descriptions.
         * Return a report of any issues found.
         * @param methods Descriptions of the public methods.
         * @return A report string, or blank if there are no issues.
         */
        public String testMethods(String[] methods)
        {
            try {
                RequiredMethod[] requiredMethods = setupMethods(methods);
                return reflector.checkMethods(requiredMethods);
            }
            catch(ClassNotFoundException ex) {
                return null;
            }

        }

        /**
         * Set up the required fields from the name and type info.
         * @param fields F:name:type for each field.
         * @return an array of RequiredField details.
         * @throws ClassNotFoundException if a type cannot be found.
         */
        private static RequiredField[] setupFields(String[] fields) throws ClassNotFoundException
        {
            RequiredField[] requiredFields = new RequiredField[fields.length];
            for(int i = 0; i < fields.length; i++) {
                String f = fields[i];
                String[] parts = f.split(":");
                assert parts[0].equals("F");
                assert parts.length == 3;
                requiredFields[i] = new RequiredField(parts[1], getClass(parts[2]));
            }
            return requiredFields;
        }

        /**
         * Set up the required methods from the given info.
         * @param fields M:name:return-type:param-types for each field.
         * @return an array of RequiredMethod details.
         * @throws ClassNotFoundException if a type cannot be found.
         */
        private static RequiredMethod[] setupMethods(String[] methods) 
        throws ClassNotFoundException
        {
            RequiredMethod[] requiredMethods = new RequiredMethod[methods.length];
            for(int i = 0; i < methods.length; i++) {
                String m = methods[i];
                String[] parts = m.split(":");
                assert parts[0].equals("M");
                assert parts.length >= 3 : m + " " + parts.length;
                Class[] params = new Class[parts.length - 3];
                for(int p = 3; p < parts.length; p++) {
                    params[p - 3] = getClass(parts[p]);
                }
                requiredMethods[i] = 
                new RequiredMethod(parts[1],
                    getClass(parts[2]), 
                    params);
            }
            return requiredMethods;

        }

        /**
         * Get the Class object corresponding to the given type name.
         * @param typeName The type to be found.
         * @return The Class object for the type.
         * @throws ClassNotFoundException if the Class cannot be found.
         */
        private static Class getClass(String typeName) throws ClassNotFoundException
        {
            switch(typeName) {
                case "boolean": return boolean.class;
                case "byte": return byte.class;
                case "double": return double.class;
                case "float": return float.class;
                case "int": return int.class;
                case "long": return long.class;
                case "short": return short.class;
                case "void": return void.class;
                default:
                    return Class.forName(typeName);
            }
        }
        /**
         * Details of a required field: its name and type.
         * @author djb
         */
        private static class RequiredField {
            private final String name;
            private final Class type;

            /**
             * Store details of a required field.
             * @param name The name of the field.
             * @param type The type of the field.
             */
            public RequiredField(String name, Class type) {
                this.name = name;
                this.type = type;
            }

            /**
             * Get the field's name.
             *
             * @return The field's name.
             */
            public String getName() {
                return name;
            }

            /**
             * Get the field's type.
             *
             * @return The field's type.
             */
            public Class getType() {
                return type;
            }

        }
        /**
         * Details of a required method: its name, return type and parameters.
         * @author djb
         */
        private static class RequiredMethod {
            private final String name;
            private final Class returnType;
            private final Class params[];

            /**
             * Store details of a required public method.
             * @param name The method's name.
             * @param returnType The return type.
             * @param params The method's parameters.
             */
            public RequiredMethod(String name, Class returnType, Class[] params) {
                this.name = name;
                this.returnType = returnType;
                this.params = params;
            }

            /**
             * Get the method's name.
             * @return The method's name.
             */
            public String getName() {
                return name;
            }

            /**
             * Get the method's return type.
             * @return The method's return type.
             */
            public Class getReturnType() {
                return returnType;
            }

            /**
             * Get the method's parameters.
             * @return The method's parameters.
             */
            public Class[] getParams() {
                return params;
            }

        }
        /**
         * Check that the required fields and methods in a class
         * are present.
         * @author djb
         */
        public class Reflector {
            private final Field[] fields;
            private final Method[] methods;
            private final Class targetClass;  

            /**
             * Create a Reflector for the given class.
             * @param className the class to be analysed.
             * @throws ClassNotFoundException if the class cannot be found.
             */
            public Reflector(String className) throws ClassNotFoundException {
                this.targetClass = Class.forName(className);
                this.fields = targetClass.getDeclaredFields();
                this.methods = targetClass.getDeclaredMethods();
            }

            /**
             * Check the required fields.
             * @param requiredFields The required fields.
             */
            public String checkFields(RequiredField[] requiredFields)
            {
                StringBuilder builder = new StringBuilder();
                boolean allOk = true;
                // Whether each has been found in the class.
                boolean[] matched = new boolean[fields.length];

                for(RequiredField f : requiredFields) {
                    // Look for a matching name.
                    String name = f.getName();
                    int index = -1;
                    for(int i = 0; i < matched.length && index < 0; i++) {
                        if(name.equals(fields[i].getName())) {
                            matched[i] = true;
                            index = i;
                        }
                    }
                    if(index >= 0) {
                        // Check that the field is private and
                        // has the correct type.
                        Field field = fields[index];
                        int modifiers = field.getModifiers();
                        if((modifiers & Modifier.PRIVATE) != Modifier.PRIVATE) {
                            builder.append(name + " has not been defined as private.")
                            .append(' ');
                            allOk = false;
                        }
                        if(field.getType() != f.getType()) {
                            builder.append(name + " does not have the correct type.")
                            .append(' ');
                            allOk = false;
                        }
                    }
                    else {
                        builder.append(name + " not defined as a field.")
                        .append(' ');
                        allOk = false;
                    }
                }
                // Report on any missing fields.
                for(int i = 0; i < matched.length; i++) {
                    if(! matched[i]) {
                        builder.append(fields[i].getName() + 
                            " does not match a required field.")
                        .append(' ');
                        allOk = false;
                    }
                }
                return builder.toString().trim();
            }

            /**
             * Check the required methods.
             * @param requiredMethods The required methods.
             */
            public String checkMethods(RequiredMethod[] requiredMethods)
            {
                StringBuilder builder = new StringBuilder();
                boolean allOk = true;
                // Whether each has been found in the class.
                boolean[] matched = new boolean[methods.length];

                for(RequiredMethod m : requiredMethods) {
                    // Look for a matching name.
                    String name = m.getName();
                    int index = -1;
                    for(int i = 0; i < matched.length && index < 0; i++) {
                        if(name.equals(methods[i].getName())) {
                            matched[i] = true;
                            index = i;
                        }
                    }
                    if(index >= 0) {
                        // Check that the method is public,
                        // has the correct return type and params.
                        Method method = methods[index];
                        int modifiers = method.getModifiers();
                        if((modifiers & Modifier.PUBLIC) != Modifier.PUBLIC) {
                            builder.append(name + " has not been defined as public.")
                            .append(' ');
                        }
                        if(method.getReturnType() != m.getReturnType()) {
                            builder.append(name + " does not have the correct return type.")
                            .append(' ');
                            allOk = false;
                        }
                        Class[] params = method.getParameterTypes();
                        Class[] requiredParams = m.getParams();
                        if(params.length == requiredParams.length) {
                            boolean ok = true;
                            for(int p = 0; p < requiredParams.length; p++) {
                                if(params[p] != requiredParams[p]) {
                                    ok = false;
                                }
                            }
                            if(!ok) {
                                builder.append("The parameters of " + name + 
                                    " do not all have the correct type.")
                                .append(' ');
                                allOk = false;
                            }
                        }
                        else {
                            builder.append(name + " should have " +
                                requiredParams.length + " instead of " +
                                params.length + " parameters.")
                            .append(' ');
                            allOk = false;
                        }
                    }
                    else {
                        builder.append(name + " not defined as a method.")
                        .append(' ');
                        allOk = false;
                    }
                }
                // Report on any additional methods.
                for(int i = 0; i < matched.length; i++) {
                    if(! matched[i]) {
                        Method method = methods[i];
                        int modifiers = method.getModifiers();
                        String name = methods[i].getName();
                        if(name.equals("toString") || name.equals("equals") ||
                        name.equals("hashCode")) {
                            // Let those pass.
                        }
                        else if((modifiers & Modifier.PUBLIC) == Modifier.PUBLIC) {
                            builder.append(name + 
                                " does not match a required public method.")
                            .append(' ');
                            allOk = false;
                        }
                    }
                }
                return builder.toString().trim();
            }

        }
    }
}
