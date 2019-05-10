package sample;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    /*
    * A class which reads and writes to VEC files
     */

    private BufferedReader reader; // Buffer for reading data from VEC files
    private BufferedWriter writer; // Buffer for writing data to VEC files
    private ArrayList shapes; // ArrayList for storing Shapes and their co-ordinates

    /*
    Parser to read from a file
     */
    public Parser(String file) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file));
        shapes = new ArrayList<Shape>();
    }

    /*
    Parser to write to a file
     */
    public Parser(String file,ArrayList<Shape> shapes) throws IOException {
        writer = new BufferedWriter(new FileWriter(file));
        this.shapes = shapes;
    }


    /*
    * Return the list of shapes
    * @return An ArrayList of shapes
     */
    public ArrayList<Shape> getShapes(){
        return this.shapes;
    }


    public void readShapes() throws IOException {
        String line;
        int pen, fill;
        pen = fill = 0;
        ArrayList<int[]> coordinates;
        while((line = reader.readLine()) != null){
            String[] parts = line.split(" ");
            switch(parts[0]){
                case "LINE":
                    // Generate coordinates here
                    shapes.add(new Line(pen,fill,coordinates)); // Load co-ordinates into Line object
                    break;
                case "RECTANGLE":
                    // Generate coordinates here
                    shapes.add(new Rectangle(pen,fill,coordinates)); // Load co-ordinates into Rectangle object
                    break;
                case "PLOT":
                    // Generate coordinates here
                    shapes.add(new Plot(pen,fill,coordinates)); // Load co-ordinates into Plot object
                    break;
                case "ELLIPSE":
                    // Generate coordinates here
                    shapes.add(new Ellipse(pen,fill,coordinates)); // Load co-ordinates into Ellipse object
                    break;
                case "POLYGON":
                    // Generate coordinates here
                    shapes.add(new Polygon(pen,fill,coordinates)); // Load co-ordinates into Polygon object
                    break;
                case "PEN":
                    pen = Integer.parseInt(parts[1],16);
                    break;
                case "FILL":
                    fill = Integer.parseInt(parts[1],16);
                default:
                    continue;
            }

        }
    }

    public void writeShapes() throws IOException { }



}
