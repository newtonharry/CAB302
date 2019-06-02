package sample.Instructions;

import sample.Exceptions.ShapeException;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Shape implements VecInstruction {

    private InstructionType type; // InstructionType type
    private List<Double> coordinates; // coordinates

    /**
     *  A base class for all the shapes which are drawn to the canvas.
     * @param type
     * @param coordinates
     */
    Shape(InstructionType type, List<Double> coordinates) throws ShapeException {
        for(Double coordinate: coordinates){
            if(coordinate < 0){
                throw new ShapeException("Coordinates cannot be negative or go over the canvas edge");
            }else if(coordinate > 1.0){
                throw new ShapeException("Coordinates cannot be greater than 1.0 or go over canvas edge");
            }
        }
        this.type = type;
        this.coordinates = coordinates;
    }

    /**
     * Returns the shapes current list of coordinates
     * @return Returns a list of doubles which contain coordinates
     * for where the shape needs to be drawn.
     */
    public List<Double> getCoordinates(){
        return this.coordinates;
    }

    /**
     * Returns the shapes current InstructionType
     * @return Returns an InstructionType enum
     */
    public InstructionType getType(){
        return this.type;
    }

    /**
     * Gets the current shapes type and coordinates and converts it into a string
     * @return A string representation of the object, in order for it
     * to be written into a VEC file.
     */
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
}
