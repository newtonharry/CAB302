package sample.Tests;

import org.junit.jupiter.api.Test;
import sample.Exceptions.ShapeException;
import sample.Instructions.*;
import sample.Instructions.Shape;

import java.awt.*;
import java.util.ArrayList;

public class VecInstructionTest {

    private Shape shape;

    /**
     * Test 1: Constructing basic Plot object.
     * [This test requires valid coordinates]
     * @throws ShapeException
     */
    @Test
    public void testPlotConstruction() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);

        shape = new PlotInstruction(coordinates);
    }

    /**
     * Test 2: Constructing basic Line object.
     * @throws ShapeException
     */
    @Test
    public void testLineConstruction() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.8);
        coordinates.add(0.9);

        shape = new LineInstruction(coordinates);
    }

    /**
     * Test 3: Constructing basic rectangle object.
     * @throws ShapeException
     */
    @Test
    public void testRectangleConstruction() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.9);
        coordinates.add(0.2);

        shape = new RectangleInstruction(coordinates);
    }

    /**
     * Test 4: Constructing Polygon object.
     * @throws ShapeException
     */
    @Test
    public void testPolygonConstruction() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.8);
        coordinates.add(0.5);
        coordinates.add(0.7);
        coordinates.add(0.5);
        coordinates.add(0.2);
        coordinates.add(0.5);

        shape = new PolygonInstruction(coordinates);
    }

    /**
     * Test 5: Constructing Ellipse object.
     * @throws ShapeException
     */
    @Test
    public void testEllipseConstruction() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.5);
        coordinates.add(0.5);

        shape = new EllipseInstruction(coordinates);
    }


    /**
     * Test 6: Constructing Line object with an invalid amount of coordinates
     * @throws ShapeException
     */
    @Test
    public void testInvalidLineCoordinates() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.8);
        coordinates.add(0.9);
        coordinates.add(0.1);

        shape = new LineInstruction(coordinates);
    }

    /**
     * Test 7: Constructing Rectangle object with invalid coordinate parameters.
     * @throws ShapeException
     */
    @Test
    public void testInvalidRectangleCoordinates() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.8);

        shape = new RectangleInstruction(coordinates);
    }

    /**
     * Test 8: Constructing Ellipse object with invalid coordinate parameters.
     * @throws ShapeException
     */
    @Test
    public void testInvalidEllipseCoordinates() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.8);
        coordinates.add(-0.9);

        shape = new EllipseInstruction(coordinates);
    }

    /**
     * Test 9: Constructing Rectangle object with invalid coordinate parameters.
     * @throws ShapeException
     */
    @Test
    public void testInvalidPolygonCoordinates() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();

        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(-0.8);
        coordinates.add(-0.5);
        coordinates.add(0.7);
        coordinates.add(0.5);
        coordinates.add(-0.2);
        coordinates.add(-0.5);

        shape = new EllipseInstruction(coordinates);
    }

    /**
     * Test 10: Constructing Rectangle object with invalid coordinate parameters.
     * @throws ShapeException
     */
    @Test
    public void testInvalidPlotCoordinates() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();

        coordinates.add(1.0);
        coordinates.add(1.5);
        coordinates.add(2.0);
        coordinates.add(2.5);

        shape = new PlotInstruction(coordinates);

    }

    @Test
    public void testFillConstruct() throws ShapeException {

    }

}