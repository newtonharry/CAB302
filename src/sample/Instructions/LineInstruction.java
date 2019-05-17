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
            throw new ShapeException("Could not process coordinates correctly");
        }

        x1 = coordinates.get(0);
        y1 = coordinates.get(1);
        x2 = coordinates.get(2);
        y2 = coordinates.get(3);
    }

    @Override
    public void draw() throws ShapeException {
        // Need to get canvas width and height
        Line line = new Line();


    }
}
