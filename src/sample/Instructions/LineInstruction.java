package sample.Instructions;

import javafx.scene.canvas.Canvas;
import sample.Exceptions.ShapeException;

import java.util.List;

import javafx.scene.shape.Line;

public class LineInstruction extends Shape {

    private double x1, y1,
            x2, y2;

    public LineInstruction(int pen, List<Double> coordinates) throws ShapeException {
        super(Instruction.LINE, pen, coordinates);
        if (coordinates.size() < 4) {
            throw new ShapeException(Instruction.ELLIPSE + "Incorrect number of co-ordinates");
        }

        x1 = coordinates.get(0);
        y1 = coordinates.get(1);
        x2 = coordinates.get(2);
        y2 = coordinates.get(3);
    }

}
