package sample;

import org.junit.Test;

import java.io.FileNotFoundException;


public class ParserTest {

   Parser parser;

   @Test
   public void testConstruction() throws FileNotFoundException {
     parser = new Parser("vec_files/test");
   }

}