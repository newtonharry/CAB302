package sample;


import java.util.ArrayList;

public class Ellipse extends Shape {

    private double x1, y1,
                   x2, y2;

    public Ellipse(Instruction type, int pen, int fill, ArrayList<Double> coordinates) {
        super(type, pen, fill, coordinates);

        x1 = coordinates.get(0);
        y1 = coordinates.get(1);
        x2 = coordinates.get(2);
        y2 = coordinates.get(3);
    }
}
