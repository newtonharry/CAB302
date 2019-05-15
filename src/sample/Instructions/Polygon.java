package sample.Instructions;

import java.util.ArrayList;
import java.util.List;

public class Polygon extends Shape {

    private List<Double> coordinates;

    public Polygon(Instruction type, int pen, int fill, List<Double> coordinates) {
        super(type,pen, fill,coordinates);

        this.coordinates = new ArrayList<>();
        this.coordinates.addAll(coordinates);
    }
}
