package sample.Instructions;

import sample.Exceptions.ParserException;
import sample.Exceptions.ShapeException;
import sample.Parser.Parser;

import java.util.List;

public class PlotInstruction extends Shape {

    private double x1, y1,
                   x2, y2;

    public PlotInstruction(int pen, List<Double> coordinates) throws ShapeException {
        super(Instruction.PLOT,pen,coordinates);
        if(coordinates.size() > 2){
            throw new ShapeException("Could not process coordinates correctly");
        }
    }

    @Override
    public void draw() {

    }
}
