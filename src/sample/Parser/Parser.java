package sample.Parser;

import sample.Exceptions.ParserException;
import sample.Exceptions.ShapeException;
import sample.Instructions.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private InstructionType instruction; // Used in switch case, to identify instruction types

    /*
    private PenInstruction pen = new PenInstruction("000000"); // black pen
    private FillInstruction fill = new FillInstruction("OFF"); // no fill

     */

    // Two patterns
    // One to match a shape
    // One to match a colour pen/fill
    private Pattern shapeInstruction = Pattern
            .compile("(?<type>RECTANGLE|PLOT|LINE|ELLIPSE|POLYGON) (?<coordinates>(\\d+\\.?\\d+ ?){2,})");
    private Pattern colourInstruction = Pattern
            .compile("(?<type>FILL|PEN) #?(?<value>([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|(?<=FILL )OFF)");

    /**
     * Creates a parser constructor. The filename must not me empty or blank.
     * @param file
     * @throws IOException
     */
    public Parser(String file) throws IOException{
        if (file.isEmpty() || file.isBlank())
            throw new IOException("Filename cannot be blank or empty");

        this.vecFile = Paths.get(file);
    }

    /**
     * Reads instructions from a vec file into the quedInstructions list.
     * @throws IOException
     * @throws ParserException
     * @throws ShapeException
     */
    public void readInstructions() throws IOException, ParserException, ShapeException {
        InstructionBufferProcessor.BUFFER_PROCESSOR.clearInstructions();

        List<String> lines = Files.readAllLines(this.vecFile, this.charset); // Lines from VEC file

        Matcher colourMatcher;
        Matcher shapeMatcher;

        for (String line : lines) {
            if (line.isEmpty() || line.isBlank()) continue;

            colourMatcher = colourInstruction.matcher(line);
            shapeMatcher = shapeInstruction.matcher(line);

            if (shapeMatcher.find()) {
                matchShape(shapeMatcher);
            } else if (colourMatcher.find()) {
                matchColour(colourMatcher);
            } else {
                InstructionBufferProcessor.BUFFER_PROCESSOR.clearInstructions();
                throw new ParserException("Could not read VEC file, incorrect syntax");
            }

        }
    }

    /**
     * Writes instructions from the quedInstructions list into a VEC file.
     * @throws IOException
     */
    public void writeInstructions() throws IOException {
        String instructions = InstructionBufferProcessor.BUFFER_PROCESSOR.getInstructions()
                .stream()
                .map(VecInstruction::toString)
                .collect(Collectors.joining("\n")); // Convert instructions to strings

        Files.writeString(this.vecFile, instructions, this.charset); // Overwrites file with new instructions
    }

    /**
     * Helper method which uses regex to check the syntax of the instructions to see if it is
     * correct. If so, it adds the instruction to the quedInstructions list
     * @param shapeMatcher
     * @throws ShapeException
     */
    private void matchShape(Matcher shapeMatcher) throws ShapeException {
        instruction = InstructionType.valueOf(shapeMatcher.group("type"));
        List<Double> coordinates; // Used to generate coordinates from instructions

        // Generate coordinates
        coordinates = Stream.of(shapeMatcher
                .group("coordinates")
                .split(" ")).map(Double::parseDouble)
                .collect(Collectors.toList());
        switch (instruction) {
            case LINE:
                LineInstruction lineInst = new LineInstruction(coordinates);
                InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(lineInst);
                break;

            case RECTANGLE:
                RectangleInstruction rectInst = new RectangleInstruction( coordinates);
                InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(rectInst);
                break;

            case PLOT:
                PlotInstruction plotInst = new PlotInstruction(coordinates);
                InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(plotInst);
                break;

            case ELLIPSE:
                EllipseInstruction ellipseInst = new EllipseInstruction(coordinates);
                InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(ellipseInst);
                break;

            case POLYGON:
                PolygonInstruction polyInst = new PolygonInstruction(coordinates);
                InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(polyInst);
                break;

            default:
        }
    }

    /**
     * Helper method which uses regex to check the syntax of the instructions to see if it is
     * correct. If so, it adds the instruction to the quedInstructions list
     * @param colourMatcher
     */
    private void matchColour(Matcher colourMatcher){
        instruction = InstructionType.valueOf(colourMatcher.group("type"));
        String value = colourMatcher.group("value");
        switch (instruction) {
            case PEN:
                PenInstruction penInst = new PenInstruction(value);
                InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(penInst);
                break;

            case FILL:
                FillInstruction fillInst = new FillInstruction(value);
                InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(fillInst);
                break;

            default:
        }
    }

    /**
     * Gets the current filename of the VEC file the parser is currently using
     * @return A string containing the filename.
     */
    public String getFileName(){
        return this.vecFile.getFileName().toString();
    }
}

