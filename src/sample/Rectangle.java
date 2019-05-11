package sample;


import java.util.ArrayList;

public class Rectangle extends Shape {

    private double x1, y1,
                   x2, y2;

    public Rectangle(int pen, int fill, ArrayList<Double> coordinates){
        super(pen,fill);

        x1 = coordinates.get(0);
        y1 = coordinates.get(1);
        x2 = coordinates.get(2);
        y2 = coordinates.get(3);
    }
}
