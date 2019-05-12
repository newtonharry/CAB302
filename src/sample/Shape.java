package sample;


import java.util.ArrayList;

public class Shape implements VecInstruction {

    private int pen;  // pen colour
    private int fill; // fill colour
    private ArrayList<Double> coordinates;

    // Constructor to change pen and fill values
    public Shape(int pen, int fill,ArrayList<Double> coordinates){
        this.pen = pen;
        this.fill = fill;
        // Could potentially generate co-ordinates here instead of doing it multiple times in parser class
        this.coordinates = coordinates;
    }

    public Shape(int pen){
        this.pen = pen;
    }

    public int getPen(){
        return this.pen;
    }

    public int getFill(){
        return this.fill;
    }

    public ArrayList<Double> getCoordinates(){
        return this.coordinates;
    }

    @Override
    public String toString(){
        // Should return "INSTRUCTION_TYPE VALUES"
        return "test";
    }

}
