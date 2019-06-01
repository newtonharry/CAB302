package sample.Instructions;

import sample.Exceptions.ShapeException;
import java.util.List;
import javafx.scene.paint.Color;

public class PolygonInstruction extends Shape {

    public PolygonInstruction(String pen, String fill, List<Double> coordinates) throws ShapeException {
        super(InstructionType.POLYGON, pen, fill, coordinates);
        if (coordinates.size() < 4) {
            throw new ShapeException(InstructionType.POLYGON + ": Incorrect number of coordinates");
        }
    }

    /**
     * Draws a polygon when editing, parsing .vec files, or exporting
     */
    @Override
    public void draw() {
        double[] xCoords = new double[this.getCoordinates().size() / 2],
                yCoords = new double[this.getCoordinates().size() / 2];

        if (this.getFill().equals("OFF"))
            InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setFill(Color.web("000000", 0.0));
        else
            InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setFill(Color.web(this.getFill(), 1.0));
        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setStroke(Color.web(this.getPen(), 1.0));

        for (int i = 0; i < this.getCoordinates().size(); i++)
            if (i % 2 == 0)
                xCoords[i / 2] = convertXCoord(this.getCoordinates().get(i));
            else
                yCoords[(i - 1) / 2] = convertYCoord(this.getCoordinates().get(i));

        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.setLineWidth(1);
        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.strokePolygon(xCoords, yCoords, this.getCoordinates().size() / 2);
        InstructionBufferProcessor.BUFFER_PROCESSOR.brush.fillPolygon(xCoords, yCoords, this.getCoordinates().size() / 2);

    }
}
