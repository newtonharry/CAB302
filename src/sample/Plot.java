package sample;

import java.util.ArrayList;

public class Plot extends Shape {

    private double x1, y1,
                   x2, y2;

    public Plot(Instruction type, int pen, ArrayList<Double> coordinates) {
        super(type,pen,coordinates);

        x1 = coordinates.get(0);
        y1 = coordinates.get(1);
        x2 = coordinates.get(2);
        y2 = coordinates.get(3);
    }
}
