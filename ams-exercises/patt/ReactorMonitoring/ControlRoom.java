package patt.ReactorMonitoring;

public class ControlRoom extends RadiationMonitor {


    private double warningThreshold;
    private double observedRadiation;

    /**
     * Constructs a ControlRoom object, which observes reactor radiation readings
     * and prints reports if the radiation exceeds a threshold.
     *
     * @param location         An arbitrary location.
     * @param warningThreshold The radiation threshold for when reports should be printed.
     */
    public ControlRoom(String location, double warningThreshold) {
        super(location);
        this.warningThreshold = warningThreshold;
    }

    /**
     * Updates the monitor with a new observation and prints a report if and only if
     * the observation is equal to or greater than the warning threshold.
     */
    @Override
    public void update(Subject subject) {
        if(((RadiationSensor) subject).getRadiation() >= this.warningThreshold){
            this.observedRadiation =  ((RadiationSensor) subject).getRadiation();
            System.out.println(this.generateReport());
        }
    }

    /**
     * Generates a report based on the current observation.
     */
    @Override
    public String generateReport() {
        return String.format("%s :: WARNING :: %.4f :: %s",this.now(),this.observedRadiation,this.getLocation());
    }
}