package oop.University;

public class Staff extends Academic {


    private int hours;

    public Staff(Title title, int id, String name){
        super(title,id,name);
    }

    @Override
    public double getWeeklyPay() {
        switch (getTitle()){
            case LECTURER:
                return 75000.0 / 52.0;
            case TUTOR:
                return 35 * hours;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return String.format("Staff %s works as a %s",getID(),getTitle().toString());
    }

    public void setHours(int hours){
        this.hours = hours;
    }
}
