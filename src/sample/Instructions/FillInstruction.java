package sample.Instructions;

import javafx.scene.paint.Color;

public class FillInstruction implements VecInstruction {
    private String colour;
    private InstructionType type = InstructionType.FILL;


    public FillInstruction(String colour) {
        this.colour = colour;
    }

    public String getColour() { return colour; }

    @Override
    public String toString() {
        if(colour.equals("OFF")){
            return String.format("%s %s", this.type.toString(), colour);
        }else{
            return String.format("%s #%s", this.type.toString(), colour);
        }
    }

    @Override
    public InstructionType getType() {
        return this.type;
    }

    public void draw(){
        if(colour.equals("OFF")){
            InstructionBufferProcessor.BUFFER_PROCESSOR.fillColor = Color.web("0x00000000");
        }else{
            InstructionBufferProcessor.BUFFER_PROCESSOR.fillColor = Color.web(colour);
        }
    }
}
