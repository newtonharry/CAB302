package sample.Instructions;

import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;

import java.util.List;

public class PlotInstruction extends Shape {

    /**
    * A Child object of the Shape class. Take coordinates which must
    * have a size greater than 2.
    * @param coordinates
    */
    public PlotInstruction(List<Double> coordinates) throws ShapeException {
        super(InstructionType.PLOT,coordinates);

        if(coordinates.size() > 2){
            throw new ShapeException(this.getType() + ": Incorrect number of coordinates");
        }
    }

    /**
     * Inherited draw function which draws a plot to the canvas.
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
