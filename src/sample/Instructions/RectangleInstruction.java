package sample.Instructions;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;
import javafx.scene.paint.Color;

import java.util.List;

public class RectangleInstruction extends Shape {

    public RectangleInstruction(String pen, String fill, List<Double> coordinates) throws ShapeException {
        super(Instruction.RECTANGLE, pen, fill, coordinates);
        if (coordinates.size() < 4) {
            throw new ShapeException(Instruction.RECTANGLE + ": Incorrect number of co-ordinates");
        }
    }

    /**
     * Draws a rectangle when editing, parsing .vec files, or exporting
     *
     * @param fill   the hexadecimal representation of the rectangle's
     *               fill colour
     * @param pen    the hexadecimal representation of the rectangle's
     *               pen colour
     * @param coords a list of doubles representing the coordinates of the
     *               bounds of the rectangle
     */
    @Override
    public void draw(Canvas canvas, GraphicsContext brush) {

        double[] xCoords = {
                this.convertXCoord(canvas,this.getCoordinates().get(0)),
                this.convertXCoord(canvas,this.getCoordinates().get(2)),
                this.convertXCoord(canvas, this.getCoordinates().get(2)),
                this.convertXCoord(canvas, this.getCoordinates().get(0))
        },
                yCoords = {
                        this.convertYCoord(canvas,this.getCoordinates().get(1)),
                        this.convertYCoord(canvas, this.getCoordinates().get(1)),
                        this.convertYCoord(canvas, this.getCoordinates().get(3)),
                        this.convertYCoord(canvas,this.getCoordinates().get(3))
                };

        brush = canvas.getGraphicsContext2D();

        if (this.getFill().equals("OFF"))
            brush.setFill(Color.web("000000", 0.0));
        else
            brush.setFill(Color.web(this.getFill(), 1.0));

        brush.setStroke(Color.web(this.getPen(), 1.0));
        brush.strokePolygon(xCoords, yCoords, 4);
        brush.fillPolygon(xCoords, yCoords, 4);
    }
}

