package sample.Instructions;

import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;

import java.util.List;

import javafx.scene.paint.Color;

public class PolygonInstruction extends Shape {

    /**
     * Constructs a polygon shape which takes a list of coordinates.
     * The list must contain 4 or more coordinates.
     * @param coordinates
     * @throws ShapeException
     */
    public PolygonInstruction(List<Double> coordinates) throws ShapeException {
        super(InstructionType.POLYGON, coordinates);
        if (coordinates.size() < 4) {
            throw new ShapeException(InstructionType.POLYGON + ": Incorrect number of coordinates");
        }
    }

    /**
     * Draws a polygon to the canvas given the shapes coordinates.
     */
    @Override
    public void draw() {
        GraphicsContext brush = InstructionBufferProcessor.BUFFER_PROCESSOR.brush;

        double[] xCoords = new double[this.getCoordinates().size() / 2],
                yCoords = new double[this.getCoordinates().size() / 2];


        int startingPoint = 0;
        for(int x=0; x<Math.ceil(this.getCoordinates().size()); x++){
            if(x == Math.ceil(this.getCoordinates().size()/2)) {startingPoint = x;}
            if(x < Math.ceil(this.getCoordinates().size()/2)){
                xCoords[x] = convertXCoord(this.getCoordinates().get(x));
            } else {
                yCoords[x-startingPoint] = convertYCoord(this.getCoordinates().get(x));
            }
        }

        brush.setFill(InstructionBufferProcessor.BUFFER_PROCESSOR.fillColor);
        brush.setStroke(InstructionBufferProcessor.BUFFER_PROCESSOR.lineColor);

        brush.setLineWidth(1);
        brush.strokePolygon(xCoords, yCoords, this.getCoordinates().size() / 2);
        brush.fillPolygon(xCoords, yCoords, this.getCoordinates().size() / 2);

    }
}
