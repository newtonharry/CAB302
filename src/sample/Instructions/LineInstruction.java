package sample.Instructions;

import javafx.scene.canvas.GraphicsContext;
import sample.Exceptions.ShapeException;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

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
     *
     * @param canvas A reference to the current canvas
     *
     * @param brush A reference to the current brush
     *
     */
    @Override
    public void draw(Canvas canvas, GraphicsContext brush) {


        int x1 = this.convertXCoord(canvas, this.getCoordinates().get(0)),
                y1 = this.convertYCoord(canvas, this.getCoordinates().get(1)),
                x2 = this.convertXCoord(canvas, this.getCoordinates().get(2)),
                y2 = this.convertYCoord(canvas, this.getCoordinates().get(3));

        brush = canvas.getGraphicsContext2D();
        brush.setStroke(Color.web(this.getPen(), 1.0));

        brush.setLineWidth(1);
        brush.strokeLine(x1, y1, x2, y2);

    }
}
