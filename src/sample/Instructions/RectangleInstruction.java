package sample.Instructions;

import sample.Exceptions.ShapeException;

import java.util.List;

public class RectangleInstruction extends Shape {

    public RectangleInstruction(String pen, String fill, List<Double> coordinates) throws ShapeException {
        super(Instruction.RECTANGLE, pen, fill, coordinates);
        if (coordinates.size() < 4) {
            throw new ShapeException(Instruction.RECTANGLE + ": Incorrect number of co-ordinates");
        }
    }
}

