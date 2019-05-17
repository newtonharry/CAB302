package sample.Tests;

import org.junit.Before;
import org.junit.Test;
import sample.Exceptions.ParserException;
import sample.Exceptions.ShapeException;
import sample.Instructions.*;
import sample.Parser.Parser;

import java.io.IOException;
import java.util.ArrayList;


public class ParserTest {

    Parser parser;

    @Before
    public void setUpParser() throws IOException {
        parser = new Parser("vec_files/test");
    }

    @Test
    public void testConstruction() throws IOException {
        parser = new Parser("vec_files/test");
    }

    @Test
    public void testRead() throws IOException, ParserException,ShapeException {
        parser.readInstructions();
    }

    @Test
    public void testWrite() throws IOException, ShapeException {
        parser = new Parser("vec_files/test_write");

        ArrayList<VecInstruction> instructions = new ArrayList<>();

        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.5);
        coordinates.add(0.5);
        coordinates.add(0.5);

        instructions.add(new RectangleInstruction(Instruction.RECTANGLE,50,50,coordinates));
        instructions.add(new LineInstruction(Instruction.LINE,50,coordinates));
        instructions.add(new EllipseInstruction(Instruction.ELLIPSE,50,50,coordinates));
        instructions.add(new PolygonInstruction(Instruction.POLYGON,50,50,coordinates));
        instructions.add(new PlotInstruction(Instruction.PLOT,50,coordinates));
        instructions.add(new PenInstruction(Instruction.PEN,"FFFFFF"));
        parser.addInstructions(instructions);
        parser.writeInstructions();
    }

    @Test
    public void testReadWrite() throws IOException, ParserException,ShapeException{
        parser.readInstructions();
        parser.writeInstructions();

    }

}