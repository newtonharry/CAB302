package sample.Instructions;


import sample.Exceptions.ShapeException;

import java.util.List;

public class EllipseInstruction extends Shape {

    private double x1, y1,
                   x2, y2;

    public EllipseInstruction(int pen, int fill, List<Double> coordinates) throws ShapeException {
        super(Instruction.ELLIPSE, pen, fill, coordinates);
        if(coordinates.size() < 4){
            throw new ShapeException(Instruction.ELLIPSE + ": Incorrect number of co-ordinates");
        }

        x1 = coordinates.get(0);
        y1 = coordinates.get(1);
        x2 = coordinates.get(2);
        y2 = coordinates.get(3);
    }

}
