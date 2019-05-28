package sample.Parser;

import sample.Exceptions.ParserException;
import sample.Exceptions.ShapeException;
import sample.GUI.Model;
import sample.Instructions.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    /*
     * A class which reads and writes to VEC files
     */
    private Path vecFile; // Buffer for writing and reading data to/from VEC files, with default filename
    private Charset charset = Charset.forName("ISO-8859-1"); // Charset to identify file
    private List<VecInstruction> instructions = new ArrayList<>(); // ArrayList for storing Instructions and their co-ordinates
    private Instruction instruction; // Used in switch case, to identify instruction types
    private Model model;

    private PenInstruction pen = new PenInstruction("000000"); // black pen
    private FillInstruction fill = new FillInstruction("OFF"); // no fill

    // Two patterns
    // One to match a shape
    // One to match a colour fill
    private Pattern shapeInstruction = Pattern
            .compile("(?<type>RECTANGLE|PLOT|LINE|ELLIPSE|POLYGON) (?<coordinates>(\\d+\\.?\\d+ ?){2,})");
    private Pattern colourInstruction = Pattern
            .compile("(?<type>FILL|PEN) #?(?<value>([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|(?<=FILL )OFF)");

    /*
    Parser to begin reading/writing from/to a file
     */
    public Parser(String file, Model model) throws ParserException {
        if (file.isEmpty() || file.isBlank())
            throw new ParserException("Filename cannot be blank or empty");

        this.vecFile = Paths.get(file);
        this.model = model;
        model.addPenInstruction(pen);
    }

    public void readInstructions() throws IOException, ParserException, ShapeException {

        List<String> lines = Files.readAllLines(this.vecFile, this.charset); // Lines from VEC file

        Matcher colourMatcher;
        Matcher shapeMatcher;

        for (String line : lines) {
            if (line.isEmpty()) continue;

            colourMatcher = colourInstruction.matcher(line);
            shapeMatcher = shapeInstruction.matcher(line);

            if (shapeMatcher.find()) {
                matchShape(shapeMatcher);
            } else if (colourMatcher.find()) {
                matchColour(colourMatcher);
            } else {
                throw new ParserException("Could not read VEC file, incorrect syntax");
            }

        }
    }

    /*
     */
    public void writeInstructions() throws IOException {
        String instructions = this.instructions
                .stream()
                .map(VecInstruction::toString)
                .collect(Collectors.joining("\n")); // Convert instructions to strings

        Files.writeString(this.vecFile, instructions, this.charset); // Overwrites file with new instructions
    }

    public void addInstruction(VecInstruction shape) {
        this.instructions.add(shape);
    }

    // Used for undo
    public void popInstruction() {
        this.instructions.remove(this.instructions.size() - 1);
    }

    public void setFileName(String file) {
        this.vecFile = Paths.get(file);
    }

    public String getFileName() {
        return this.vecFile.getFileName().toString();
    }

    /*
    @Override
    public Iterator<VecInstruction> iterator() {
        // Filter out the PEN and FILL instructions - kind of a hacky way of doing this
        return this.instructions.stream().filter(instruction -> !(instruction.getType().equals(Instruction.PEN) ||
                instruction.getType().equals(Instruction.FILL))).iterator();
    }
    */

    private void matchShape(Matcher shapeMatcher) throws ShapeException {
        instruction = Instruction.valueOf(shapeMatcher.group("type"));
        List<Double> coordinates; // Used to generate coordinates from instructions

        // Generate coordinates
        coordinates = Stream.of(shapeMatcher
                .group("coordinates")
                .split(" ")).map(Double::parseDouble)
                .collect(Collectors.toList());

        switch (instruction) {
            case LINE:
                LineInstruction lineInst = new LineInstruction(pen.getColour(), coordinates);
                model.addLineInstruction(lineInst);
                instructions.add(lineInst);
                break;

            case RECTANGLE:
                RectangleInstruction rectInst = new RectangleInstruction(pen.getColour(), fill.getColour(), coordinates);
                model.addRectangleInstruction(rectInst);
                instructions.add(rectInst);
                break;

            case PLOT:
                PlotInstruction plotInst = new PlotInstruction(pen.getColour(), coordinates);
                model.addPlotInstruction(plotInst);
                instructions.add(plotInst);
                break;

            case ELLIPSE:
                EllipseInstruction ellipseInst = new EllipseInstruction(pen.getColour(), fill.getColour(), coordinates);
                model.addEllipseInstruction(ellipseInst);
                instructions.add(ellipseInst);
                break;

            case POLYGON:
                PolygonInstruction polyInst = new PolygonInstruction(pen.getColour(), fill.getColour(), coordinates);
                model.addPolygonInstruction(polyInst);
                instructions.add(polyInst);
                break;

            default:
        }
    }

    private void matchColour(Matcher colourMatcher) {
        instruction = Instruction.valueOf(colourMatcher.group("type"));
        String value = colourMatcher.group("value");
        switch (instruction) {
            case PEN:
                PenInstruction penInst = new PenInstruction(value);
                model.addPenInstruction(penInst);
                instructions.add(penInst);
                break;

            case FILL:
                FillInstruction fillInst = new FillInstruction(value);
                model.addFillInstruction(fillInst);
                instructions.add(fillInst);
                break;

            default:
        }
    }
}
