package sample.Instructions;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;
import javafx.scene.paint.Color;

import java.util.List;

public class EllipseInstruction extends Shape {


    public EllipseInstruction(String pen, String fill, List<Double> coordinates) throws ShapeException {
        super(Instruction.ELLIPSE, pen, fill, coordinates);
        if(coordinates.size() < 4){
            throw new ShapeException(Instruction.ELLIPSE + ": Incorrect number of co-ordinates");
        }
    }

    /**
     * Draws an ellipse when editing, parsing .vec files, or exporting
     *
     * @param fill   the hexadecimal representation of the rectangle's
     *               fill colour
     * @param pen    the hexadecimal representation of the rectangle's
     *               pen colour
     * @param coords a list of doubles representing the coordinates for
     *               the bounds of the ellipse
     */
    @Override
    public void draw(Canvas canvas, GraphicsContext brush) {

        int x = convertXCoord(canvas, this.getCoordinates().get(0)),
                y = convertYCoord(canvas,this.getCoordinates().get(1)),
                width = convertWidth(canvas, this.getCoordinates().get(0), this.getCoordinates().get(2)),
                height = convertHeight(canvas,this.getCoordinates().get(1), this.getCoordinates().get(3));

        //brush = canvas.getGraphicsContext2D();

        if (this.getFill().equals("OFF"))
            brush.setFill(Color.web("000000", 0.0));
        else
            brush.setFill(Color.web(this.getFill(), 1.0));

        brush.setStroke(Color.web(this.getPen(), 1.0));
        brush.setLineWidth(3);
        brush.strokeOval(x, y, width, height);
        brush.fillOval(x, y, width, height);
    }
}
