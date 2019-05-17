package sample.Instructions;

public class PenInstruction implements VecInstruction {


    private int colour;
    private Instruction type = Instruction.PEN;

    public PenInstruction(String colour) {
        this.colour = Integer.parseInt(colour, 16);
    }

    public int getColour() {
        return this.colour;
    }

    @Override
    public String toString() {
        return String.format("%s %s",this.type.toString(),Integer.toHexString(this.colour).toUpperCase());
    }
}
