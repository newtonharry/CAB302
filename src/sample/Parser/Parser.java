package sample.Parser;

import sample.Exceptions.ParserException;
import sample.Exceptions.ShapeException;
import sample.GUI.Controller;
import sample.Instructions.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser extends Controller{
    /*
     * A class which reads and writes to VEC files
     */

    private Path vecFile; // Buffer for writing and reading data to/from VEC files
    private Charset charset = Charset.forName("ISO-8859-1"); // Charset to identify file
    private ArrayList<VecInstruction> instructions = new ArrayList<>(); // ArrayList for storing Instructions and their co-ordinates

    // Two patters
    // One to match a shape
    // One to match a colour fill
    private Pattern shapeInstruction = Pattern
            .compile("(?<type>RECTANGLE|PLOT|LINE|ELLIPSE|POLYGON) (?<coordinates>(\\d+\\.?\\d+ ?){2,})");
    private Pattern colourInstruction = Pattern
            .compile("(?<type>FILL|PEN) #?(?<value>([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|(?<=FILL )OFF)");

    /*
    Parser to begin reading/writing from/to a file
     */
    public Parser(String file) throws IOException {
        vecFile = Paths.get(file);
    }

    // Potential algorithm update

    /*
    1. Check weather line is a PEN/FILL or Shape instruction
    2. Depending on type of instruction, cleanse the string and extract data
     */

    // IMPORTANT: need to check if VEC file has correct syntax whilst parsing, assert error is there is one
    // Perhaps introduce a string cleaning method (might have to use regex as will be easier for that)
    public void readInstructions() throws IOException, ParserException, ShapeException {
        List<String> lines = Files.readAllLines(this.vecFile, this.charset); // Lines from VEC file

        List<Double> coordinates; // Used to generate coordinates from instructions
        String value; // Used to store value of colour value (fill/pen)

        int pen = 0x000000; // no pen
        int fill = -0xFFFFFF; // no fill

        Instruction instruction; // Used in switch case, to identify instruction types

        Matcher colourMatcher;
        Matcher shapeMatcher;

        for (String line : lines) {
            if (line.isEmpty()) continue;

            colourMatcher = colourInstruction.matcher(line);
            shapeMatcher = shapeInstruction.matcher(line);

            if (shapeMatcher.find()) {
                instruction = Instruction.valueOf(shapeMatcher.group("type"));

                coordinates = Stream.of(shapeMatcher            // Generate coordinates
                        .group("coordinates")
                        .split(" ")).map(Double::parseDouble)
                        .collect(Collectors.toList());

                switch (instruction) {
                    case LINE:
                        instructions.add(new LineInstruction(pen, coordinates));
                        break;

                    case RECTANGLE:
                        instructions.add(new RectangleInstruction(pen, fill, coordinates));
                        break;

                    case PLOT:
                        instructions.add(new PlotInstruction(pen, coordinates));
                        break;

                    case ELLIPSE:
                        instructions.add(new EllipseInstruction(pen, fill, coordinates));
                        break;

                    case POLYGON:
                        instructions.add(new PolygonInstruction(pen, fill, coordinates));
                        break;

                    default:
                }

            } else if (colourMatcher.find()) {
                instruction = Instruction.valueOf(colourMatcher.group("type"));
                value = colourMatcher.group("value");
                switch (instruction) {
                    case PEN:
                        pen = Integer.parseInt(value, 16);
                        instructions.add(new PenInstruction(value));
                        break;

                    case FILL:
                        if (!value.equals("OFF"))
                            fill = Integer.parseInt(value, 16);

                        instructions.add(new FillInstruction(value));
                        break;

                    default:
                }
            } else {
                throw new ParserException("Could not read VEC file, incorrect syntax");
            }

        }
    }

    public void writeInstructions() throws IOException {
        // Need to convert the shapes ArrayList into proper format for the VEC file
        String instructions = this.instructions
                .stream()
                .map(VecInstruction::toString)
                .collect(Collectors.joining("\n")); // Need to work on the shape toString method

        Files.writeString(this.vecFile, instructions, this.charset); // Overwrites file with new instructions
    }

    public void addInstructions(ArrayList<VecInstruction> shapes) {
        this.instructions.addAll(shapes);
    }

    // Used for undo
    public void popInstruction() {
        this.instructions.remove(this.instructions.size() - 1);
    }
}