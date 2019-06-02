package sample.Instructions;


import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;

import java.util.List;

public class EllipseInstruction extends Shape {

    /**
     * Constructs an ellipse shape which takes a list of coordinates.
     * The list must contain 4 coordinates.
     * @param coordinates
     * @throws ShapeException
     */
    public EllipseInstruction(List<Double> coordinates) throws ShapeException {
        super(InstructionType.ELLIPSE,coordinates);
        if(coordinates.size() != 4){
            throw new ShapeException(InstructionType.ELLIPSE + ": Incorrect number of co-ordinates");
        }
    }

    /**
     * Draws an ellipse to the canvas given the shapes coordinates.
     */
    @Override
    public void draw() {

        GraphicsContext brush = InstructionBufferProcessor.BUFFER_PROCESSOR.brush;

        int x = convertXCoord(this.getCoordinates().get(0)),
                y = convertYCoord(this.getCoordinates().get(1)),
                width = convertWidth(this.getCoordinates().get(0), this.getCoordinates().get(2)),
                height = convertHeight(this.getCoordinates().get(1), this.getCoordinates().get(3));

        brush.setFill(InstructionBufferProcessor.BUFFER_PROCESSOR.fillColor);

        brush.setStroke(InstructionBufferProcessor.BUFFER_PROCESSOR.lineColor);
        brush.setLineWidth(3);
        brush.strokeOval(x, y, width, height);
        brush.fillOval(x, y, width, height);
    }
}
