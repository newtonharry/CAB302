package sample.Instructions;

import sample.Exceptions.ShapeException;

import java.util.ArrayList;
import java.util.List;

public class PolygonInstruction extends Shape {

    private List<Double> coordinates;

    public PolygonInstruction(int pen, int fill, List<Double> coordinates) throws ShapeException {
        super(Instruction.POLYGON,pen, fill,coordinates);
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
