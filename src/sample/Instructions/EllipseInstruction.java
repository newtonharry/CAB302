package sample.Instructions;


import sample.Exceptions.ShapeException;
import javafx.scene.paint.Color;

import java.util.List;

public class EllipseInstruction extends Shape {


    public EllipseInstruction(String pen, String fill, List<Double> coordinates) throws ShapeException {
        super(InstructionType.ELLIPSE, pen, fill, coordinates);
        if(coordinates.size() < 4){
            throw new ShapeException(InstructionType.ELLIPSE + ": Incorrect number of co-ordinates");
        }
    }

    /**
     * Draws an ellipse when editing, parsing .vec files, or exporting
     */
    @Override
    public void draw() {

        int x = convertXCoord(this.getCoordinates().get(0)),
                y = convertYCoord(this.getCoordinates().get(1)),
                width = convertWidth(this.getCoordinates().get(0), this.getCoordinates().get(2)),
                height = convertHeight(this.getCoordinates().get(1), this.getCoordinates().get(3));

        if (this.getFill().equals("OFF"))
            InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setFill(Color.web("000000", 0.0));
        else
            InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setFill(Color.web(this.getFill(), 1.0));

        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setStroke(Color.web(this.getPen(), 1.0));
        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setLineWidth(3);
        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.strokeOval(Math.min(x,getCoordinates().get(2)), Math.min(y,getCoordinates().get(3)), width, height);
        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.fillOval(Math.min(x,getCoordinates().get(2)), Math.min(y,getCoordinates().get(3)), width, height);
    }
}
