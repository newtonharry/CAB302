package sample.Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import sample.Exceptions.ParserException;
import sample.Exceptions.ShapeException;
import sample.Instructions.*;
import sample.Parser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class ParserTest {

    private Parser parser;

    @AfterEach
    public void clearInstructionList(){
        InstructionBufferProcessor.BUFFER_PROCESSOR.clearInstructions();
    }


    @Test
    public void testConstructionSuccess() throws IOException {
        parser = new Parser("resources/example1_correct.vec");
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
    public void testReadExample1Failure() throws IOException {
        parser = new Parser("resources/example1_incorrect.vec");
        assertThrows(ParserException.class, () -> {
                    parser.readInstructions();
                }
        );
    }

    @Test
    public void testReadExample2Failure() throws IOException {
        parser = new Parser("resources/example2_incorrect.vec");
        assertThrows(ParserException.class, () -> {
                    parser.readInstructions();
                }
        );
    }

    @Test
    public void testReadExample3Failure() throws IOException {
        parser = new Parser("resources/example3_incorrect.vec");
        assertThrows(ParserException.class, () -> {
                    parser.readInstructions();
                }
        );

    }

    @Test
    public void testWriteSuccess() throws ShapeException, IOException {
        List<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.8);
        coordinates.add(0.4);

        List<Double> coordinates2 = new ArrayList<>();
        coordinates2.add(0.5);
        coordinates2.add(0.6);


        InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(new RectangleInstruction(coordinates));
        InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(new EllipseInstruction(coordinates));
        InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(new RectangleInstruction(coordinates));
        InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(new PolygonInstruction(coordinates));
        InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(new LineInstruction(coordinates));
        InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(new PlotInstruction(coordinates2));

        parser = new Parser("resources/test_write_1.vec");
        parser.writeInstructions();


    }
}

