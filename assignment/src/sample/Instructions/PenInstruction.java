package sample.Instructions;

import javafx.scene.paint.Color;

public class PenInstruction implements VecInstruction {

    // The colour of the fill for the shape
    private String colour;
    // The type of instruction
    private InstructionType type = InstructionType.PEN;

    /**
     * Constructs a new PenInstruction
     * @param colour
     */
    public PenInstruction(String colour) { this.colour = colour; }

    /**
     * Gets the current type and colour value and formats it into a string
     * @return A string representation of the object, in order for it
     * to be written into a VEC file.
     */
    @Override
    public String toString() {
        colour = colour.replaceAll("0x","");
        return String.format("%s #%s", this.type.toString(), colour.toUpperCase());
    }

    /**
     * Able to get the current type of instruction
     * @return the current instruction type
     */
    @Override
    public InstructionType getType() {
        return this.type;
    }

    /**
     * Re-assigns the current lineColor value to the objects colour value
     */
    public void draw(){
       InstructionBufferProcessor.BUFFER_PROCESSOR.lineColor = Color.web(colour);
    }
}
