package sample;

import java.util.ArrayList;

public class Polygon extends Shape {

    private ArrayList<Double> coordinates;

    public Polygon(Instruction type,int pen, int fill, ArrayList<Double> coordinates) {
        super(type,pen, fill,coordinates);

        this.coordinates = new ArrayList<>();
        this.coordinates.addAll(coordinates);
    }
}
