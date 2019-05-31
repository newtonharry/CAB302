package sample.Instructions;

import sample.Exceptions.ShapeException;
import javafx.scene.paint.Color;
import sample.GUI.Controller;

import java.util.List;

public class LineInstruction extends Shape {


    public LineInstruction(String pen, List<Double> coordinates) throws ShapeException {
        super(Instruction.LINE, pen, coordinates);
        if (coordinates.size() < 4) {
            throw new ShapeException(Instruction.LINE + "Incorrect number of co-ordinates");
        }
    }

    /**
     * Draws a line when editing, parsing .vec files, or exporting
     */
    @Override
    public void draw() {

        int x1 = convertXCoord(this.getCoordinates().get(0)),
                y1 = convertYCoord(this.getCoordinates().get(1)),
                x2 = convertXCoord(this.getCoordinates().get(2)),
                y2 = convertYCoord(this.getCoordinates().get(3));

        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setStroke(Color.web(this.getPen(), 1.0));

        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setLineWidth(1);
        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.strokeLine(x1, y1, x2, y2);

    }
}
