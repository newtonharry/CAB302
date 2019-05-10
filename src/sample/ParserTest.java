package sample;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;


public class ParserTest {

   Parser parser;

   @Before
   public void setUpParser() throws FileNotFoundException {
      parser = new Parser("vec_files/test");
   }

   @Test
   public void testConstruction() throws FileNotFoundException {
     parser = new Parser("vec_files/test");
   }

   @Test
    public void testRead() throws IOException {
       parser.readShapes();
   }

}