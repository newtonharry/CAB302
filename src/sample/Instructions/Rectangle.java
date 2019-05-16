package sample.Instructions;


import sample.Exceptions.ShapeException;

import java.util.List;

public class Rectangle extends Shape {

    private double x1, y1,
                   x2, y2;

    public Rectangle(Instruction type, int pen, int fill, List<Double> coordinates) throws ShapeException {
        super(type,pen,fill,coordinates);
        if(coordinates.size() < 4){
            throw new ShapeException("Could not process coordinates correctly");
        }

    }

    @Override
    public void draw() {

    }
}
