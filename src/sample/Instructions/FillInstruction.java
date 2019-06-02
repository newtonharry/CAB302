package sample.Instructions;

import javafx.scene.paint.Color;

public class FillInstruction implements VecInstruction {
    // The colour of the fill for the shape
    private String colour;
    // The type of instruction
    private InstructionType type = InstructionType.FILL;

    /**
     * Constructs a new VecInstruction
     * @param colour
     */
    public FillInstruction(String colour) {
        this.colour = colour;
    }


    /**
     * Gets the current type and colour value and formats it into a string
     * @return A string representation of the object, in order for it
     * to be written into a VEC file.
     */
    @Override
    public String toString() {
        if(colour.equals("OFF")){
            return String.format("%s %s", this.type.toString(), colour);
        }else{
            return String.format("%s #%s", this.type.toString(), colour.replaceAll("0x","").toUpperCase());
        }
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
     * Re-assigns the current fillColor value to the objects colour value
     */
    public void draw(){
        if(colour.equals("OFF")){
            InstructionBufferProcessor.BUFFER_PROCESSOR.fillColor = Color.web("0x00000000");
        }else{
            InstructionBufferProcessor.BUFFER_PROCESSOR.fillColor = Color.web(colour);
        }
    }
}
