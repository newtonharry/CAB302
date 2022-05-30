package patt.ReactorMonitoring;

import java.util.Random;

public class RadiationSensor extends Subject {


    private String location;
    private int seed;
    private double radiation;
    private Random r;

    /**
     * Constructs a RadiationSensor object
     *
     * @param location An arbitrary location.
     * @param seed     A seed for the random number generator used to simulate radiation
     *                  readings.
     */
    public RadiationSensor(String location, int seed) {
        this.location = location;
        this.seed = seed;
        this.r = new Random(this.seed);
    }

    /**
     * Gets the location
     *
     * @return location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Gets the radiation.
     *
     * @return radiation
     */
    public double getRadiation() {
        return this.radiation;
    }

    /**
     * Updates radiation and notifies all observers of
     * the change.
     */
    public void readRadiation() {
        this.radiation = this.r.nextDouble() * 10;
        this.notifyObservers();
    }

}