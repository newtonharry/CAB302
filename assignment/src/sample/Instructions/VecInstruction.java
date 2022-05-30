package sample.Instructions;

public interface VecInstruction {

    /**
     * Allows the object to get a string representation of itself
     * @return A string, containing the values of the object.
     */
    @Override
    String toString();

    /**
     * Object to retrieve its current InstructionType.
     * @return The current InstructionType of the object.
     */
    InstructionType getType();

    /**
     * Draw's a shape to the canvas.
     */
    void draw();

}
