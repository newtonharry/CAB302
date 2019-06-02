package sample.Instructions;

import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;

import java.util.List;

public class PolygonInstruction extends Shape {

    /**
     * Constructs a polygon shape which takes a list of coordinates.
     * The list must contain 4 or more coordinates.
     * @param coordinates
     * @throws ShapeException
     */
    public PolygonInstruction(List<Double> coordinates) throws ShapeException {
        super(InstructionType.POLYGON, coordinates);
        if (coordinates.size() < 4) {
            throw new ShapeException(InstructionType.POLYGON + ": Incorrect number of coordinates");
        }
    }

    /**
     * Draws a polygon to the canvas given the shape's coordinates.
     */
    @Override
    public void draw() {
        GraphicsContext brush = InstructionBufferProcessor.BUFFER_PROCESSOR.brush;

        double[] xCoords = new double[this.getCoordinates().size() / 2],
                 yCoords = new double[this.getCoordinates().size() / 2];

        for (int i = 0; i < this.getCoordinates().size(); i += 2) {
            xCoords[i / 2] = convertXCoord(this.getCoordinates().get(i));
            yCoords[i / 2] = convertXCoord(this.getCoordinates().get(i + 1));
        }

        brush.setFill(InstructionBufferProcessor.BUFFER_PROCESSOR.fillColor);
        brush.setStroke(InstructionBufferProcessor.BUFFER_PROCESSOR.lineColor);

        brush.setLineWidth(1);
        brush.strokePolygon(xCoords, yCoords, this.getCoordinates().size() / 2);
        brush.fillPolygon(xCoords, yCoords, this.getCoordinates().size() / 2);
    }
}
