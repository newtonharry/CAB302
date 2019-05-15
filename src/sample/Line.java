package sample;

import java.util.ArrayList;

public class Line extends Shape {

    private double x1, y1,
                   x2, y2;

    public Line(Instruction type, int pen, ArrayList<Double> coordinates) {
        super(type,pen,coordinates);
    }
}
