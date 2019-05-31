package sample.Instructions;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Control;
import sample.GUI.Controller;

public abstract class Shape implements VecInstruction {

    private Instruction type; // Instruction type
    private String pen;  // pen colour
    private String fill; // fill colour
    private List<Double> coordinates; // Instruction coordinates

    // Constructor to change pen and fill values
    Shape(Instruction type, String pen, String fill, List<Double> coordinates) {
        this.type = type;
        this.pen = pen;
        this.fill = fill;
        this.coordinates = coordinates;
    }

    Shape(Instruction type, String pen, List<Double> coordinates) {
        this(type,pen,"",coordinates);
    }

    public List<Double> getCoordinates(){
        return this.coordinates;
    }

    public String getPen() {
        return this.pen;
    }

    public String getFill() { return this.fill; }

    public Instruction getType(){
        return this.type;
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s",
                this.type.toString(),
                this.coordinates
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(" ")));
    }

    /**
     * Converts a fractional x-coordinate into an integer based on the
     * current width of the canvas
     *
     * @param coord the x-coordinate to be converted as a double
     * @return the integer version of the coordinate
     */
    public int convertXCoord(double coord) {
        return (int) Math.round(coord * InstructionBufferProcessor.BUFFER_PROCESSOR.canvas.getWidth());
    }

    /**
     * Converts a fractional y-coordinate into an integer based on the
     * current height of the canvas
     *
     * @param coord the y-coordinate to be converted as a double
     * @return the integer version of the coordinate
     */
    public int convertYCoord(double coord) {
        return (int) Math.round(coord * InstructionBufferProcessor.BUFFER_PROCESSOR.canvas.getHeight());
    }

    /**
     * Converts a pair of fractional x-coordinates into an integer width
     * based on the current width of the canvas
     *
     * @param coord1 the first x-coordinate as a double
     * @param coord2 the second x-coordinate as a double
     * @return the width between them as an integer
     */
    public int convertWidth(double coord1, double coord2) {
        return (int) Math.round(Math.abs(coord1 - coord2) * InstructionBufferProcessor.BUFFER_PROCESSOR.canvas.getWidth());
    }

    /**
     * Converts a pair of fractional y-coordinates into an integer height
     * based on the current height of the canvas
     *
     * @param coord1 the first y-coordinate as a double
     * @param coord2 the second y-coordinate as a double
     * @return the height between them as an integer
     */
    public int convertHeight(double coord1, double coord2) {
        return (int) Math.round(Math.abs(coord1 - coord2) * InstructionBufferProcessor.BUFFER_PROCESSOR.canvas.getHeight());
    }


    public abstract void draw();

}
