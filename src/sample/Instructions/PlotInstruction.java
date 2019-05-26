package sample.Instructions;

import sample.Exceptions.ShapeException;

import java.util.List;

public class PlotInstruction extends Shape {

    private double x1, y1,
                   x2, y2;

    public PlotInstruction(String pen, List<Double> coordinates) throws ShapeException {
        super(Instruction.PLOT,pen,coordinates);
        if(coordinates.size() > 2){
            throw new ShapeException(Instruction.PLOT + ": Incorrect number of coordinates");
        }
    }
}
