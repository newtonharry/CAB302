package patt.ReactorMonitoring;


import java.util.ArrayList;
import java.util.List;

public class ResearchCentre extends RadiationMonitor {

    private String location;
    private List<Double> observations = new ArrayList<>();

    /**
     * Constructs a ResearchCentre object, which observes reactor radiation readings
     * and constantly prints a report with the current moving average of the
     * recorded observations.
     *
     * @param location An arbitrary location.
     */
    public ResearchCentre(String location) {
        super(location);
    }

    /**
     * Updates the monitor with a new observation and prints a report.
     */
    @Override
    public void update(Subject subject) {
        observations.add(((RadiationSensor) subject).getRadiation());
        System.out.println(this.generateReport());
    }

    /**
     * Generates a report of the current moving average, updated by a new
     * observation. The moving average is calculated by summing all observations
     * made so far, and dividing by the quantity of observations so far.
     */
    public String generateReport() {
        double average = this.observations.stream().mapToDouble(d -> d).average().orElse(0.0);
        return String.format("%s :: moving average :: %.4f :: %s",this.now(),average,this.getLocation());
    }

}