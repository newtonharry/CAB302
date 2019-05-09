package sample;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShapeTest {

    Shape shape;

    @Test
    public void testConstructor(){
       shape = new Rectangle(1,1,1,1);
    }

}