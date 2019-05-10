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
    public Parser(String file) throws IOException {
        reader = new BufferedReader(new FileReader(file));
        shapes = new ArrayList<Shape>();
        this.readShapes(); // Read shapes into list
    }

    /*
    Parser to write to a file
     */
    public Parser(String file,ArrayList<Shape> shapes) throws IOException {
        writer = new BufferedWriter(new FileWriter(file));
        this.shapes = shapes;
        this.writeShapes(); // Write shapes to file
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


            // This seems pretty inefficent having this if statement,
            // but it stops us from having code duplication to generate the co-ordinates on each switch case
           // plz make better
            if(!(parts[0].equals("PEN") || parts[0].equals("FILL"))){
                // Generate co-cordinates here
            }

            switch(parts[0]){
                case "PEN":
                    pen = Integer.parseInt(parts[1],16);
                    break;
                case "FILL":
                    fill = Integer.parseInt(parts[1],16);
                    break;
                case "LINE":
                    shapes.add(new Line(pen,fill,coordinates)); // Load co-ordinates into Line object
                    break;
                case "RECTANGLE":
                    shapes.add(new Rectangle(pen,fill,coordinates)); // Load co-ordinates into Rectangle object
                    break;
                case "PLOT":
                    shapes.add(new Plot(pen,fill,coordinates)); // Load co-ordinates into Plot object
                    break;
                case "ELLIPSE":
                    shapes.add(new Ellipse(pen,fill,coordinates)); // Load co-ordinates into Ellipse object
                    break;
                case "POLYGON":
                    shapes.add(new Polygon(pen,fill,coordinates)); // Load co-ordinates into Polygon object
                    break;
                default:
                    continue;
            }

        }
    }

    public void writeShapes() throws IOException { }



}
