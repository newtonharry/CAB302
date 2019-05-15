package sample.Instructions;


import java.util.List;
import java.util.stream.Collectors;

public class Shape implements VecInstruction {

    private Instruction type;
    private int pen;  // pen colour
    private int fill; // fill colour
    private List<Double> coordinates;

    // Constructor to change pen and fill values
    public Shape(Instruction type, int pen, int fill, List<Double> coordinates) {
        this.type = type;
        this.pen = pen;
        this.fill = fill;
        // Could potentially generate co-ordinates here instead of doing it multiple times in parser class
        this.coordinates = coordinates;
    }

    public Shape(Instruction type, int pen, List<Double> coordinates) {
        this.type = type;
        this.pen = pen;
        this.coordinates = coordinates;
    }

    public int getPen() {
        return this.pen;
    }

    public int getFill() {
        return this.fill;
    }

    public List<Double> getCoordinates() {
        return this.coordinates;
    }

    @Override
    public String toString() {
        // Should return "INSTRUCTION_TYPE VALUES"
        return String.format(
                "%s %s",
                this.type.toString(),
                this.coordinates
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(" ")));
    }


}
