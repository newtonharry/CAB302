package sample.Instructions;

import sample.Exceptions.ShapeException;
import javafx.scene.paint.Color;

import java.util.List;

public class PlotInstruction extends Shape {


    public PlotInstruction(String pen, List<Double> coordinates) throws ShapeException {
        super(InstructionType.PLOT,pen,coordinates);

        if(coordinates.size() > 2){
            throw new ShapeException(this.getType() + ": Incorrect number of coordinates");
        }
    }

    /**
     * Draws a plot when editing, parsing .vec files, or exporting
     */
    @Override
    public void draw() {
        int x = this.convertXCoord(this.getCoordinates().get(0)),
                y = this.convertYCoord(this.getCoordinates().get(1));

        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setStroke(Color.web(this.getPen(), 1.0));

        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setLineWidth(1);
        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.strokeLine(x, y, x, y);

    }
}
