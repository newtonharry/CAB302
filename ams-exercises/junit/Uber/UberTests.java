package junit.Uber;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class UberTests {

    // Set up inputs.
    private static Random random = new Random();
    private static String[] names = new String[]{
            "Brittney Threet",
            "Ludivina Hensley",
            "Charlsie Beckstead",
            "Khadijah Geter",
            "Candelaria Truax",
            "Denice Lucero",
            "Abdul Creegan",
            "Jess Krebbs",
            "Eladia Duty",
            "Leanna Kyles",
            "Lester Anchondo",
    };

    private static String[] carModels = new String[]{
            "Dodge",
            "Audi",
            "Holden",
            "Suzuki",
            "BMW",
            "Aston Martin",
    };
    /*
     * Test 0: Declaring Uber objects.
     * [This test obliges you to create a class called "Uber".]
     */
    Uber uber;

    private static String randName() {
        return names[random.nextInt(names.length)];
    }

    private static String randCarModel() {
        return carModels[random.nextInt(carModels.length)];
    }

    private static long randWaitTime() {
        long scale = 500;
        return (long) (1000 + random.nextDouble() * scale);
    }

    // Clear the uber object before each test.
    @BeforeEach
    public void setUpUber() {
        uber = null;
    }

    /*
     * Test 1: Constructing a basic Uber object.
     * [This test obliges you to add a constructor with two parameters]
     *
     */
    @Test
    public void testConstruction() {
        String driverName = randName();
        String carModel = randCarModel();
        uber = new Uber(carModel, driverName);
    }

    /*
     * Test 2: Get the driver's name
     * [This test obliges you to add a getter method for the driver's name]
     */
    @Test
    public void tesDriverName() {
        String driverName = randName();
        String carModel = randCarModel();
        uber = new Uber(carModel, driverName);
        assertEquals(driverName, uber.getDriverName());
    }

    /*
     * Test 3: Get the driver's car model.
     * [This test obliges you to aadd a getter method for the driver's car model]
     */
    @Test
    public void testCarModel() {
        String driverName = randName();
        String carModel = randCarModel();
        uber = new Uber(carModel, driverName);
        assertEquals(carModel, uber.getCarModel());
    }

    /*
     * Test 4: Update the global fare rate.
     * [This test obliges you to add a static field, static setter, and static getter for the fare rate]
     */
    @Test
    public void testSetFareRate() {
        double newFareRate = random.nextDouble() * 10;
        Uber.setRate(newFareRate);
        assertEquals(newFareRate, Uber.getFareRate(), 0.0);
    }

    /*
     * Test 5: A simple job.
     * [This test obliges you to add two methods for controlling jobs. The "dropOff" method should
     * return the cost of the job based on the fareRate and the time between pickup and setdown.]
     */
    @Test
    public void testSimpleJob() throws InterruptedException {
        double fareRate = random.nextDouble() * 10;
        long waitTime = randWaitTime();
        double expectedFare = fareRate * ((double) waitTime / 1000);

        // Run test
        Uber.setRate(fareRate);
        uber = new Uber(randCarModel(), randName());
        uber.pickup();
        Thread.sleep(waitTime);
        double actualFare = uber.setdownPassenger();

        // Check result
        assertEquals(expectedFare, actualFare, 0.5);
    }


    /*
     * Test 6: A complex job.
     * [This test obliges you to also consider a surge multiplier]
     */
    @Test
    public void testComplexJob() throws InterruptedException {
        double surgeMultiplier = 1 + (random.nextDouble() * 10);
        double fareRate = random.nextDouble() * 10;
        long waitTime = randWaitTime();
        double expectedFare = fareRate * ((double) waitTime / 1000) * surgeMultiplier;

        // Run test
        Uber.setRate(fareRate);
        uber = new Uber(randCarModel(), randName());
        uber.setSurge(surgeMultiplier);
        uber.pickup();
        Thread.sleep(waitTime);
        double actualFare = uber.setdownPassenger();

        // Check result
        assertEquals(expectedFare, actualFare, 1.0);
    }
}
