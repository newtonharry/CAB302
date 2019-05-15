package sample;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    /*
     * A class which reads and writes to VEC files
     */

    private Path vecFile; // Buffer for writing and reading data to/from VEC files
    private Charset charset = Charset.forName("ISO-8859-1"); // Charset to identify file
    private ArrayList<VecInstruction> instructions = new ArrayList<>(); // ArrayList for storing Shapes and their co-ordinates

    /*
    Parser to begin reading/writing from/to a file
     */
    public Parser(String file) throws IOException {
        vecFile = Paths.get(file);
    }

    // IMPORTANT: need to check if VEC file has correct syntax whilst parsing, assert error is there is one
    // Perhaps introduce a string cleaning method (might have to use regex as will be easier for that)
    public void readShapes() throws IOException {
        List<String> lines = Files.readAllLines(this.vecFile, this.charset);
        ArrayList<Double> coordinates = new ArrayList<>();

        int pen = 0x000000, // no pen
                fill = -0xFFFFFF; // no fill

        Instruction instruction;

        for (String line : lines) {
            String[] params = line.split(" ");
            instruction = Instruction.valueOf(params[0]); // Convert instruction to enum
            switch (instruction) {

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
        }
    }


    public void writeShapes() throws IOException {
        // Need to convert the shapes ArrayList into proper format for the VEC file
        String instructions =  this.instructions
                .stream()
                .map(instruction -> instruction.toString())
                .collect(Collectors.joining("\n")); // Need to work on the shape toString method

        Files.writeString(this.vecFile, instructions, this.charset); // Overwrites file with new instructions
    }

    private void parseCoordinates(ArrayList<Double> coordinates, String[] points) {
        String[] new_points = Arrays.copyOfRange(points, 1, points.length);
        for(String coordinate : new_points)
            coordinates.add(Double.parseDouble(coordinate));
    }


    public void addInstructions(ArrayList<Shape> shapes) {
        this.instructions.addAll(shapes);
    }

    // Used for undo
    public void popInstruction() {
        this.instructions.remove(this.instructions.size() - 1);
    }
}
