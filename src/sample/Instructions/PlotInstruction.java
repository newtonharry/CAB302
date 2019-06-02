package sample.Instructions;

import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;

import java.util.List;

public class PlotInstruction extends Shape {

    /**
     * Constructs a plot shape which takes a list of coordinates.
     * The list must contain 2 coordinates.
     * @param coordinates
     * @throws ShapeException
    */
    public PlotInstruction(List<Double> coordinates) throws ShapeException {
        super(InstructionType.PLOT,coordinates);

        if(coordinates.size() > 2){
            throw new ShapeException(this.getType() + ": Incorrect number of coordinates");
        }
    }

    /**
     * Draws a plot to the canvas given the shapes coordinates
     */
    @Override
    public void draw() {
        GraphicsContext brush = InstructionBufferProcessor.BUFFER_PROCESSOR.brush;
        int x = this.convertXCoord(this.getCoordinates().get(0)),
                y = this.convertYCoord(this.getCoordinates().get(1));

        brush.setStroke(InstructionBufferProcessor.BUFFER_PROCESSOR.lineColor);
        brush.setLineWidth(3);
        brush.strokeLine(x, y, x, y);

    }
}
