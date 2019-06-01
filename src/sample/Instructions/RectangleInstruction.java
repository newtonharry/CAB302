package sample.Instructions;

import sample.Exceptions.ShapeException;
import javafx.scene.paint.Color;

import java.util.List;

public class RectangleInstruction extends Shape {

    public RectangleInstruction(String pen, String fill, List<Double> coordinates) throws ShapeException {
        super(InstructionType.RECTANGLE, pen, fill, coordinates);
        if (coordinates.size() < 4) {
            throw new ShapeException(InstructionType.RECTANGLE + ": Incorrect number of co-ordinates");
        }
    }

    /**
     * Draws a rectangle when editing, parsing .vec files, or exporting
     */
    @Override
    public void draw() {

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

        //brush = canvas.getGraphicsContext2D();

        if (this.getFill().equals("OFF"))
            InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setFill(Color.web("000000", 0.0));
        else
            InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setFill(Color.web(this.getFill(), 1.0));

        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setStroke(Color.web(this.getPen(), 1.0));
        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.strokePolygon(xCoords, yCoords, 4);
        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.fillPolygon(xCoords, yCoords, 4);
    }
}

