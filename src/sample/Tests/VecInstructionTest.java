package sample.Tests;

import org.junit.jupiter.api.Test;
import sample.Exceptions.ShapeException;
import sample.Instructions.*;

import java.util.ArrayList;

public class VecInstructionTest {

    private Shape shape;


    @Test
    public void testPlotConstruction() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);

        shape = new PlotInstruction(coordinates);
    }

    @Test
    public void testLineConstruction() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.8);
        coordinates.add(0.9);

        shape = new LineInstruction(coordinates);
    }

    @Test
    public void testRectangleConstruction() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.9);
        coordinates.add(0.2);

        shape = new RectangleInstruction(coordinates);
    }

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

    @Test
    public void testEllipseConstruction() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.5);
        coordinates.add(0.5);

        shape = new EllipseInstruction(coordinates);
    }

    @Test
    public void testFillConstruct() {
    }
}