package sample;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

                    instructions.add(new Pen(params[1]));
                    break;

                case FILL:

                    instructions.add(new Fill(params[1]));
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
                    instructions.add(new Plot(Instruction.RECTANGLE,pen, coordinates));
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
        String instructions = String.join("\n", this.instructions
                .stream()
                .map(instruction -> instruction.toString())
                .collect(Collectors.toList())); // Need to work on the shape toString method

        Files.writeString(this.vecFile, instructions, this.charset); // Overwrites file with new instructions
    }

    private void parseCoordinates(ArrayList<Double> coordinates, String[] points) {
        for (int i = 1; i < 5; i++)
            coordinates.add(Double.parseDouble(points[i]));
    }


    public void addInstructions(ArrayList<Shape> shapes) {
        this.instructions.addAll(shapes);
    }

    public void popInstruction() {
        this.instructions.remove(this.instructions.size() - 1);
    }
}
