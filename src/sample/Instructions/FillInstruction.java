package sample.Instructions;

public class FillInstruction implements VecInstruction {
    private int colour;
    private Instruction type = Instruction.FILL;


    public FillInstruction(String value) {
        if (value.equals("OFF")) {
            this.colour = -0xFFFFFF;
        } else {
            this.colour = Integer.parseInt(value, 16);
        }
    }

    public int getColour() {
        return colour;
    }

    @Override
    public String toString() {
        String value;

        if (this.colour == -0xFFFFFF) {
            value = "OFF";
        } else {
            value = Integer.toHexString(this.colour);
        }

        return String.format("%s %s", this.type.toString(), value);
    }
}