package sample.Instructions;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;
import java.util.List;
import javafx.scene.paint.Color;

public class PolygonInstruction extends Shape {

    public PolygonInstruction(String pen, String fill, List<Double> coordinates) throws ShapeException {
        super(Instruction.POLYGON, pen, fill, coordinates);
        if (coordinates.size() < 4) {
            throw new ShapeException(Instruction.POLYGON + ": Incorrect number of coordinates");
        }
    }

    /**
     * Draws a polygon when editing, parsing .vec files, or exporting
     *
     * @param fill   the hexadecimal representation of the rectangle's
     *               fill colour
     * @param pen    the hexadecimal representation of the rectangle's
     *               pen colour
     * @param coords a list of doubles representing the coordinates along
     *               the polygon's perimeter
     */
    @Override
    public void draw(Canvas canvas, GraphicsContext brush) {
        double[] xCoords = new double[this.getCoordinates().size() / 2],
                yCoords = new double[this.getCoordinates().size() / 2];

        brush = canvas.getGraphicsContext2D();
        if (this.getFill().equals("OFF"))
            brush.setFill(Color.web("000000", 0.0));
        else
            brush.setFill(Color.web(this.getFill(), 1.0));
        brush.setStroke(Color.web(this.getPen(), 1.0));

        for (int i = 0; i < this.getCoordinates().size(); i++)
            if (i % 2 == 0)
                xCoords[i / 2] = convertXCoord(canvas,this.getCoordinates().get(i));
            else
                yCoords[(i - 1) / 2] = convertYCoord(canvas,this.getCoordinates().get(i));

        brush.setLineWidth(1);
        brush.strokePolygon(xCoords, yCoords, this.getCoordinates().size() / 2);
        brush.fillPolygon(xCoords, yCoords, this.getCoordinates().size() / 2);

    }
}
