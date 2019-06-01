package sample.Instructions;

import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;

import java.util.List;

import javafx.scene.paint.Color;

public class PolygonInstruction extends Shape {

    public PolygonInstruction(List<Double> coordinates) throws ShapeException {
        super(InstructionType.POLYGON, coordinates);
        if (coordinates.size() < 4) {
            throw new ShapeException(InstructionType.POLYGON + ": Incorrect number of coordinates");
        }
    }

    /**
     * Draws a polygon when editing, parsing .vec files, or exporting
     */
    @Override
    public void draw() {
        GraphicsContext brush = InstructionBufferProcessor.BUFFER_PROCESSOR.brush;

        double[] xCoords = new double[this.getCoordinates().size() / 2],
                yCoords = new double[this.getCoordinates().size() / 2];

        brush.setFill(InstructionBufferProcessor.BUFFER_PROCESSOR.fillColor);
        brush.setStroke(InstructionBufferProcessor.BUFFER_PROCESSOR.lineColor);

        for (int i = 0; i < this.getCoordinates().size(); i++)
            if (i % 2 == 0)
                xCoords[i / 2] = convertXCoord(this.getCoordinates().get(i));
            else
                yCoords[(i - 1) / 2] = convertYCoord(this.getCoordinates().get(i));

        brush.setLineWidth(1);
        brush.strokePolygon(xCoords, yCoords, this.getCoordinates().size() / 2);
        brush.fillPolygon(xCoords, yCoords, this.getCoordinates().size() / 2);

    }
}
