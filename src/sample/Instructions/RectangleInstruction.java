package sample.Instructions;

import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;

import java.util.List;

public class RectangleInstruction extends Shape {
    /**
     * Constructs a rectangle shape which takes a list of coordinates.
     * The list must contain 4 coordinates.
     * @param coordinates
     * @throws ShapeException
     */
    public RectangleInstruction(List<Double> coordinates) throws ShapeException {
        super(InstructionType.RECTANGLE, coordinates);
        if (coordinates.size() != 4) {
            throw new ShapeException(InstructionType.RECTANGLE + ": Incorrect number of co-ordinates");
        }
    }

    /**
     * Draws a rectangle to the canvas given the shapes coordinates.
     */
    @Override
    public void draw() {

        GraphicsContext brush = InstructionBufferProcessor.BUFFER_PROCESSOR.brush;

        double[] xCoords = {
                this.convertXCoord(this.getCoordinates().get(0)),
                this.convertXCoord(this.getCoordinates().get(2)),
                this.convertXCoord( this.getCoordinates().get(2)),
                this.convertXCoord( this.getCoordinates().get(0))
        },
                yCoords = {
                        this.convertYCoord(this.getCoordinates().get(1)),
                        this.convertYCoord(this.getCoordinates().get(1)),
                        this.convertYCoord(this.getCoordinates().get(3)),
                        this.convertYCoord(this.getCoordinates().get(3))
                };


        brush.setFill( InstructionBufferProcessor.BUFFER_PROCESSOR.fillColor);

        brush.setStroke( InstructionBufferProcessor.BUFFER_PROCESSOR.lineColor);

        brush.strokePolygon(xCoords, yCoords, 4);
        brush.fillPolygon(xCoords, yCoords, 4);
    }
}

