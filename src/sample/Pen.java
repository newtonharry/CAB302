package sample;

public class Pen implements VecInstruction {


    private int colour;

    public Pen(String colour) {
        this.colour = Integer.parseInt(colour, 16);
    }

    public int getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return null;
    }
}
