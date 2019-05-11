package sample;

import java.io.*;
import java.util.ArrayList;

public class Parser {
    /*
    * A class which reads and writes to VEC files
     */

    private BufferedReader reader; // Buffer for reading data from VEC files
    private BufferedWriter writer; // Buffer for writing data to VEC files
    private ArrayList<Shape> shapes; // ArrayList for storing Shapes and their co-ordinates

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


    public void readShapes() throws IOException {
        String line;
        ArrayList<Double> coordinates = new ArrayList<>();
        int pen  =  0x000000,
            fill = -0xFFFFFF; // no fill

        while((line = reader.readLine()) != null) {
            String[] params = line.split(" ");

            switch (params[0]) {

                case "PEN":

                    pen = Integer.parseInt(params[1],16);
                    break;

                case "FILL":

                    if (params[1].equals("OFF"))
                        fill = -0xFFFFFF;
                    else
                        fill = Integer.parseInt(params[1],16);
                    break;

                case "LINE":

                    for (int i = 1; i < 5; i++)
                        coordinates.add(Double.parseDouble(params[i]));

                    shapes.add(new Line(pen,coordinates));
                    break;

                case "RECTANGLE":

                    for (int i = 1; i < 5; i++)
                        coordinates.add(Double.parseDouble(params[i]));

                    shapes.add(new Rectangle(pen,fill,coordinates));
                    break;

                case "PLOT":

                    for (int i = 1; i < 3; i++)
                        coordinates.add(Double.parseDouble(params[i]));

                    shapes.add(new Plot(pen,coordinates));
                    break;

                case "ELLIPSE":

                    for (int i = 1; i < 5; i++)
                        coordinates.add(Double.parseDouble(params[i]));

                    shapes.add(new Ellipse(pen,fill,coordinates));
                    break;

                case "POLYGON":

                    for (int i = 1; i < coordinates.size(); i++)
                        coordinates.add(Double.parseDouble(params[i]));

                    shapes.add(new Polygon(pen,fill,coordinates));
                    break;

                default:
            }
        }


        for (Shape shape : shapes)
            ;// shape.draw();
    }

    public void writeShapes() throws IOException {
    }


    /*
     * Return the list of shapes
     * @return An ArrayList of shapes
     */
    public ArrayList<Shape> getShapes(){
        return this.shapes;
    }
}
