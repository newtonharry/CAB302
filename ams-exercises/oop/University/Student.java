package oop.University;

public class Student extends Academic {

    private double stipend;

    public Student(Title title,int id,String name,double stipend){
        super(title, id, name);
        this.stipend = stipend;
    }


    @Override
    public double getWeeklyPay() {
        return stipend;
    }

    @Override
    public String toString() {
        return String.format("Student %s studies a %s",getID(),getTitle().toString());
    }
}
