package sample.Tests;

import org.junit.jupiter.api.Test;
import sample.Exceptions.ShapeException;
import sample.Instructions.*;
import sample.Instructions.Shape;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void testInvalidLineCoordinates() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.8);
        coordinates.add(0.9);
        coordinates.add(0.1);

        assertThrows(ShapeException.class, () -> {
                    shape = new LineInstruction(coordinates);
                }
        );
    }

    @Test
    public void testInvalidRectangleCoordinates() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.8);

        assertThrows(ShapeException.class, () -> {
                    shape = new RectangleInstruction(coordinates);
                }
        );

    }

    @Test
    public void testInvalidEllipseCoordinates() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(0.5);
        coordinates.add(0.6);
        coordinates.add(0.8);
        coordinates.add(-0.9);

        assertThrows(ShapeException.class, () -> {
                    shape = new EllipseInstruction(coordinates);
                }
        );
    }

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

        assertThrows(ShapeException.class, () -> {
                    shape = new EllipseInstruction(coordinates);
                }
        );
    }

    @Test
    public void testInvalidPlotCoordinates() throws ShapeException {
        ArrayList<Double> coordinates = new ArrayList<>();

        coordinates.add(1.0);
        coordinates.add(1.5);
        coordinates.add(2.0);
        coordinates.add(2.5);

        assertThrows(ShapeException.class, () -> {
                    shape = new PlotInstruction(coordinates);
                }
        );

    }

    @Test
    public void testFillConstructor() throws ShapeException {
            VecInstruction fill = new FillInstruction("000000");
    }

    @Test
    public void testPenConstructor() throws ShapeException {
        VecInstruction pen = new FillInstruction("000000");
    }

}