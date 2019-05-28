package sample.Instructions;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;
import javafx.scene.paint.Color;

import java.util.List;

public class PlotInstruction extends Shape {


    public PlotInstruction(String pen, List<Double> coordinates) throws ShapeException {
        super(Instruction.PLOT,pen,coordinates);
        if(coordinates.size() > 2){
            throw new ShapeException(Instruction.PLOT + ": Incorrect number of coordinates");
        }
    }

    /**
     * Draws a plot when editing, parsing .vec files, or exporting
     *
     * @param pen    the hexadecimal representation of the rectangle's
     *               pen colour
     * @param coords a list of doubles representing the coordinates of
     *               the plot
     */
    @Override
    public void draw(Canvas canvas, GraphicsContext brush) {
        int x = this.convertXCoord(canvas,this.getCoordinates().get(0)),
                y = this.convertYCoord(canvas,this.getCoordinates().get(1));

        brush = canvas.getGraphicsContext2D();
        brush.setStroke(Color.web(this.getPen(), 1.0));

        brush.setLineWidth(1);
        brush.strokeLine(x, y, x, y);

    }
}
