package sample.Tests;

import org.junit.jupiter.api.Test;
import sample.Exceptions.ParserException;
import sample.Exceptions.ShapeException;
import sample.Parser.Parser;

import java.io.IOException;


public class ParserTest {

    private Parser parser;

    /*
    @BeforeEach
    public void setUpParser() throws ParserException {
        parser = new Parser("resources/vectest.vec");
    }
     */

    @Test
    public void testConstruction() throws ParserException {
        parser = new Parser("vec_files/example1.vec");
    }

    @Test
    public void testRead() throws IOException, ParserException,ShapeException {
        parser.readInstructions();
    }

    @Test
    public void testWrite() throws IOException, ShapeException {
    }

    @Test
    public void testReadWrite() throws IOException, ParserException,ShapeException{
        parser.readInstructions();
        parser.writeInstructions();
    }
}

