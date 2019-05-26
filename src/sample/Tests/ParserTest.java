package sample.Tests;

import org.junit.Before;
import org.junit.Test;
import sample.Exceptions.ParserException;
import sample.Exceptions.ShapeException;
import sample.GUI.Model;
import sample.Instructions.*;
import sample.Parser.Parser;

import java.io.IOException;
import java.util.ArrayList;


public class ParserTest {

    private Parser parser;

    @Before
    public void setUpParser() throws ParserException {
        parser = new Parser("vec_files/test", new Model());
    }

    @Test
    public void testConstruction() throws ParserException {
        parser = new Parser("vec_files/test", new Model());
    }

    @Test
    public void testRead() throws IOException, ParserException,ShapeException {
        parser.readInstructions();
    }

    /*
    @Test
    public void testWrite() throws IOException, ShapeException {
        parser = new Parser("vec_files/test_write");

        ArrayList<VecInstruction> instructions = new ArrayList<>();

        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.5);
        coordinates.add(0.5);
        coordinates.add(0.5);

        instructions.add(new RectangleInstruction(50,50,coordinates));
        instructions.add(new LineInstruction(50,coordinates));
        instructions.add(new EllipseInstruction(50,50,coordinates));
        instructions.add(new PolygonInstruction(50,50,coordinates));
        instructions.add(new PlotInstruction(50,coordinates));
        instructions.add(new PenInstruction("FFFFFF"));
        parser.addInstructions(instructions);
        parser.writeInstructions();
    }
    */

    @Test
    public void testReadWrite() throws IOException, ParserException,ShapeException{
        parser.readInstructions();
        parser.writeInstructions();

    }


    @Test
    public void testIterator() throws ShapeException, ParserException, IOException {
        parser.readInstructions();
        for(VecInstruction shape: parser){
            System.out.println(shape.toString());
        }
    }

}