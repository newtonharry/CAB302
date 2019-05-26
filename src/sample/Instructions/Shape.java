package sample.Instructions;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Shape implements VecInstruction {

    private Instruction type;
    private String pen;  // pen colour
    private String fill; // fill colour
    private List<Double> coordinates;

    // Constructor to change pen and fill values
    Shape(Instruction type, String pen, String fill, List<Double> coordinates) {
        this.type = type;
        this.pen = pen;
        this.fill = fill;
        this.coordinates = coordinates;
    }

    Shape(Instruction type, String pen, List<Double> coordinates) {
        this.type = type;
        this.pen = pen;
        this.coordinates = coordinates;
    }

    public List<Double> getCoordinates(){
        return this.coordinates;
    }

    public String getPen() {
        return this.pen;
    }

    public String getFill() { return this.fill; }

    public Instruction getType(){
        return this.type;
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s",
                this.type.toString(),
                this.coordinates
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(" ")));
    }
}
