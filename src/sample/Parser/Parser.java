package sample.Parser;

import sample.Instructions.Fill;
import sample.Instructions.Pen;
import sample.Instructions.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    /*
     * A class which reads and writes to VEC files
     */

    private Path vecFile; // Buffer for writing and reading data to/from VEC files
    private Charset charset = Charset.forName("ISO-8859-1"); // Charset to identify file
    private ArrayList<VecInstruction> instructions = new ArrayList<>(); // ArrayList for storing Instructions and their co-ordinates

    // Two patters
    // One to match a shape
    // One to match a colour fill
    private Pattern shapeInstruction = Pattern.compile("(?<type>\\w+) (?<coordinates>(\\d+\\.?\\d+ ?){2,})");
    private Pattern colourInstruction = Pattern.compile("(?<type>\\w+) #?(?<value>([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|(?<=FILL )OFF)");

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
    public void readInstructions() throws IOException {
        List<String> lines = Files.readAllLines(this.vecFile, this.charset);
        List<Double> coordinates;

        int pen = 0x000000, // no pen
                fill = -0xFFFFFF; // no fill

        Instruction instruction;

        Matcher colourMatcher;
        Matcher shapeMatcher;

        for (String line : lines) {
            if(line.isEmpty()) continue;

            colourMatcher = colourInstruction.matcher(line);
            shapeMatcher = shapeInstruction.matcher(line);

            if (shapeMatcher.find()) {
                instruction = Instruction.valueOf(shapeMatcher.group("type"));
                coordinates = Stream.of(shapeMatcher            // Generate coordinates
                        .group("coordinates")
                        .split(" ")).map(Double::parseDouble).collect(Collectors.toList());
                switch (instruction) {
                    case LINE:
                        instructions.add(new Line(Instruction.LINE, pen, coordinates));
                        break;

                    case RECTANGLE:
                        instructions.add(new Rectangle(Instruction.RECTANGLE, pen, fill, coordinates));
                        break;

                    case PLOT:
                        instructions.add(new Plot(Instruction.PLOT, pen, coordinates));
                        break;

                    case ELLIPSE:
                        instructions.add(new Ellipse(Instruction.ELLIPSE, pen, fill, coordinates));
                        break;

                    case POLYGON:
                        instructions.add(new Polygon(Instruction.POLYGON, pen, fill, coordinates));
                        break;

                    default:
                }

            } else if (colourMatcher.find()) {
                instruction = Instruction.valueOf(colourMatcher.group("type"));
                switch (instruction) {
                    case PEN:
                        pen = Integer.parseInt(colourMatcher.group("value"),16);
                        instructions.add(new Pen(Instruction.PEN, colourMatcher.group("value")));
                        break;

                    case FILL:
                        fill = Integer.parseInt(colourMatcher.group("value"),16);
                        instructions.add(new Fill(Instruction.FILL, colourMatcher.group("value")));
                        break;

                    default:
                }
            } else {
                // Raise exception here
                System.out.println("NO MATCH");
            }





            /*
            String[] params = line.split(" ");
            instruction = Instruction.valueOf(params[0]); // Convert instruction to enum
            switch (instruction) {
                // Clean strings in the case clause,just before adding the instruction to the list,
                // or implement it in the shape class??? can't make my mind up

                case PEN:

                    instructions.add(new Pen(Instruction.PEN,params[1].replace("#","")));
                    break;

                case FILL:

                    instructions.add(new Fill(Instruction.FILL,params[1].replace("#","")));
                    break;

                case LINE:

                    parseCoordinates(coordinates, params);
                    instructions.add(new Line(Instruction.LINE,pen, coordinates));
                    break;

                case RECTANGLE:

                    parseCoordinates(coordinates, params);
                    instructions.add(new Rectangle(Instruction.RECTANGLE,pen, fill, coordinates));
                    break;

                case PLOT:

                    parseCoordinates(coordinates, params);
                    instructions.add(new Plot(Instruction.PLOT,pen, coordinates));
                    break;

                case ELLIPSE:

                    parseCoordinates(coordinates, params);
                    instructions.add(new Ellipse(Instruction.ELLIPSE,pen, fill, coordinates));
                    break;

                case POLYGON:

                    parseCoordinates(coordinates, params);
                    instructions.add(new Polygon(Instruction.POLYGON ,pen, fill, coordinates));
                    break;

                default:
            }
             */
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

    private void parseCoordinates(ArrayList<Double> coordinates, String[] points) {
        String[] new_points = Arrays.copyOfRange(points, 1, points.length);
        for (String coordinate : new_points)
            coordinates.add(Double.parseDouble(coordinate));
    }


    public void addInstructions(ArrayList<VecInstruction> shapes) {
        this.instructions.addAll(shapes);
    }

    // Used for undo
    public void popInstruction() {
        this.instructions.remove(this.instructions.size() - 1);
    }
}
