package sample.Instructions;


import javafx.scene.shape.Rectangle;
import sample.Exceptions.ShapeException;

import java.util.List;

public class RectangleInstruction extends Shape {

    public RectangleInstruction(int pen, int fill, List<Double> coordinates) throws ShapeException {
        super(Instruction.RECTANGLE, pen, fill, coordinates);
        if (coordinates.size() < 4) {
            throw new ShapeException(Instruction.RECTANGLE + ": Incorrect number of co-ordinates");
        }
    }
}

