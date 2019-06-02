package sample.Tests;

import org.junit.jupiter.api.Test;
import sample.Exceptions.ParserException;
import sample.Exceptions.ShapeException;
import sample.Parser.Parser;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class ParserTest {

    private Parser parser;

    @Test
    public void testConstructionSuccess() throws IOException {
        parser = new Parser("resources/example1_correct.vec");
    }

    @Test
    public void testConstructionFail1() {
        assertThrows(IOException.class, () -> {
                    parser = new Parser("resources/file_that_doesnt_exist.vec");
                }
        );
    }

    @Test
    public void testConstructionFail2() {
        assertThrows(IOException.class, () -> {
                    parser = new Parser("");
                }
        );
    }

    @Test
    public void testConstructionFail3() {
        assertThrows(IOException.class, () -> {
                    parser = new Parser("        ");
                }
        );
    }

    @Test
    public void testReadExample1Success() throws IOException, ParserException, ShapeException {
        parser = new Parser("resources/example1_correct.vec");
        parser.readInstructions();
    }

    @Test
    public void testReadExample2Success() throws IOException, ParserException, ShapeException {
        parser = new Parser("resources/example2_correct.vec");
        parser.readInstructions();
    }

    @Test
    public void testReadExample3Success() throws IOException, ParserException, ShapeException {
        parser = new Parser("resources/example3_correct.vec");
        parser.readInstructions();
    }

    @Test
    public void testReadExample1Failure() throws IOException, ParserException, ShapeException {
        parser = new Parser("resources/example1_incorrect.vec");
        assertThrows(ParserException.class, () -> {
                    parser.readInstructions();
                }
        );
    }

    @Test
    public void testReadExample2Failure() throws IOException, ShapeException {
        parser = new Parser("resources/example2_incorrect.vec");
        assertThrows(ParserException.class, () -> {
                    parser.readInstructions();
                }
        );
    }

    @Test
    public void testReadExample3Failure() throws IOException, ParserException, ShapeException {
        parser = new Parser("resources/example3_incorrect.vec");
        assertThrows(ParserException.class, () -> {
                    parser.readInstructions();
                }
        );
    }

    @Test
    public void testWrite() throws IOException, ShapeException, ParserException {
        parser = new Parser("resources/example3_correct.vec");
    }

    @Test
    public void testReadWriteSuccess() throws IOException, ParserException, ShapeException {
        parser = new Parser("resources/example3_correct.vec");
        parser.readInstructions();
        parser.writeInstructions();
    }

    @Test
    public void testReadWriteFail() throws IOException, ParserException, ShapeException {
        parser = new Parser("resources/example3_correct.vec");
        parser.readInstructions();
        parser.writeInstructions();
    }
}

