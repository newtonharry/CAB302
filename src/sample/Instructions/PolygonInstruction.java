package sample.Instructions;

import sample.Exceptions.ShapeException;

import java.util.ArrayList;
import java.util.List;

public class PolygonInstruction extends Shape {


    public PolygonInstruction(int pen, int fill, List<Double> coordinates) throws ShapeException {
        super(Instruction.POLYGON,pen, fill,coordinates);
        if(coordinates.size() < 4){
           throw new ShapeException(Instruction.POLYGON + ": Incorrect number of coordinates");
        }
    }
}
