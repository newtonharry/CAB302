package sample;

import java.util.ArrayList;

public class Shape {

    private int pen; // Pen colour
    private int fill = 0; // Fill colour
    private ArrayList<int[]> coordinates; // Coordinates for shape

    // Constructor to change pen and fill values
    public Shape(int pen, int fill,ArrayList<int[]> coordinates){
        this.pen = pen;
        this.fill = fill;
        this.coordinates = coordinates;
    }

    public int getPen(){
        return this.pen;
    }

    public int getFill(){
        return this.fill;
    }

    public ArrayList<int[]> getCoordinates(){
        return this.coordinates;
    }


    // Add potential draw method
    // Can just call "Shape".Draw() and it draws the shape to the canvas

}
