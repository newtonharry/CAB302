package sample.Instructions;

import sample.Exceptions.ShapeException;

import java.util.ArrayList;
import java.util.List;

public class Polygon extends Shape {

    private List<Double> coordinates;

    public Polygon(Instruction type, int pen, int fill, List<Double> coordinates) throws ShapeException {
        super(type,pen, fill,coordinates);
        if(coordinates.size() < 4){
           throw new ShapeException("Could not process coordinates properly");
        }


        this.coordinates = new ArrayList<>();
        this.coordinates.addAll(coordinates);
    }

    @Override
    public void draw() {

    }
}
