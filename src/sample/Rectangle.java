package sample;


import java.util.ArrayList;

public class Rectangle extends Shape {

    private double x1, y1,
                   x2, y2;

    public Rectangle(Instruction type,int pen, int fill, ArrayList<Double> coordinates){
        super(type,pen,fill,coordinates);

    }


    // In each of these shape classes, can potentially specify how they are each drawn to the canvas
}
