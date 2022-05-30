package junit.Uber;

import java.time.Duration;
import java.time.Instant;

public class Uber {

    private String driver;
    private String model;
    private  static double rateOfCar;
    private  static double surge = 1.0;
    private Instant start, end;


    public Uber(String model, String driver){
        this.driver = driver;
        this.model = model;
    }

    public String getDriverName(){
        return this.driver;
    }

    public String getCarModel(){
        return this.model;
    }

    public static void setRate(double rate){
        rateOfCar = rate;
    }

    public static double getFareRate(){
        return rateOfCar;
    }

    public void pickup(){
        start = Instant.now();
    }

    public static void setSurge(double multiply){
        surge = multiply;
    }

    public double setdownPassenger(){
        end = Instant.now();
        double dur = Duration.between(start,end).toMillis();
        double fare = rateOfCar * (dur / 1000) * surge;
        return  fare;
    }

}
