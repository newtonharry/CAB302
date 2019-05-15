package sample;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShapeTest {

    private Shape shape;

    // This is not how the Shape objects are used, Pen and Fill have their own classes
    @Test
    public void testPenConstruct() {
        //shape = new Shape(0x000000, -0xFFFFFF);
        //assertEquals(shape.getPen(), 0x000000);
    }

    @Test
    public void testFillConstruct() {
        //shape = new Shape(0x000000, -0xFFFFFF);
        //assertEquals(shape.getFill(), -0xFFFFFF);
    }
}