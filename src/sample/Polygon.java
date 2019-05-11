package sample;

import java.util.ArrayList;

public class Polygon extends Shape {

    private ArrayList<Double> coordinates;

    public Polygon(int pen, int fill, ArrayList<Double> coordinates) {
        super(pen, fill);

        this.coordinates = new ArrayList<>();
        this.coordinates.addAll(coordinates);
    }
}
