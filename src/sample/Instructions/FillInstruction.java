package sample.Instructions;

public class FillInstruction implements VecInstruction {
    private String colour;
    private Instruction type = Instruction.FILL;


    public FillInstruction(String colour) {
        this.colour = colour;
    }

    public String getColour() { return colour; }

    @Override
    public String toString() {
        return String.format("%s %s", this.type.toString(), colour);
    }

    @Override
    public Instruction getType() {
        return this.type;
    }
}
