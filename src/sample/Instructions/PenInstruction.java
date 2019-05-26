package sample.Instructions;

public class PenInstruction implements VecInstruction {

    private String colour;
    private Instruction type = Instruction.PEN;

    public PenInstruction(String colour) { this.colour = colour; }

    public String getColour() {
        return this.colour;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.type.toString(), colour);
    }

    @Override
    public Instruction getType() {
        return this.type;
    }
}
