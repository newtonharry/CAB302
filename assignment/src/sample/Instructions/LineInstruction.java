package sample.Instructions;

import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;

import java.util.List;

public class LineInstruction extends Shape {


    /**
     * Constructs an ellipse shape which takes a list of coordinates.
     * The list must contain 4 coordinates.
     * @param coordinates
     * @throws ShapeException
     */
    public LineInstruction( List<Double> coordinates) throws ShapeException {
        super(InstructionType.LINE,  coordinates);
        if (coordinates.size() != 4) {
            throw new ShapeException(InstructionType.LINE + ": Incorrect number of co-ordinates");
        }
    }

    /**
     * Draws a line to the canvas given the shapes coordinates.
     */
    @Override
    public void draw() {
        GraphicsContext brush = InstructionBufferProcessor.BUFFER_PROCESSOR.brush;

        int x1 = convertXCoord(this.getCoordinates().get(0)),
                y1 = convertYCoord(this.getCoordinates().get(1)),
                x2 = convertXCoord(this.getCoordinates().get(2)),
                y2 = convertYCoord(this.getCoordinates().get(3));

        brush.setStroke(InstructionBufferProcessor.BUFFER_PROCESSOR.lineColor);
        brush.setLineWidth(1);
        brush.strokeLine(x1, y1, x2, y2);

    }
}
