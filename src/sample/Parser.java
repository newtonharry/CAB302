package sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Parser {
    /*
    1. Will begin reading data from a file
    2. Will identify different shapes in file based on name
    3. Using a hashmap, with each key being a shape, add co-ordinates to that shape
     */

    private BufferedReader buffer; // Buffer for reading data from VEC files
    private ArrayList shapes; // HashMap for storing drawing co-ordinates

    public Parser(String file) throws FileNotFoundException {
        buffer = new BufferedReader(new FileReader(file));
        shapes = new ArrayList<Shape>();
    }



}
