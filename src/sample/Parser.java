package sample;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    /*
    * A class which reads and writes to VEC files
     */

    private Path vecFile; // Buffer for writing and reading data to/from VEC files
    private Charset charset =  Charset.forName("ISO-8859-1"); // Charset to identify file
    private ArrayList<Shape> shapes; // ArrayList for storing Shapes and their co-ordinates

    /*
    Parser to begin reading/writing from/to a file
     */
    public Parser(String file) throws IOException {
        vecFile = Paths.get(file);
    }

    private void genCoordinates(ArrayList<Double> coordinates, String[] points){
        for (int i = 1; i < 5; i++)
            coordinates.add(Double.parseDouble(points[i]));
    }

    public void readShapes() throws IOException {
        List<String> lines = Files.readAllLines(this.vecFile,this.charset);
        ArrayList<Double> coordinates = new ArrayList<>();
        int pen  =  0x000000, // no pen
            fill = -0xFFFFFF; // no fill

        for(String line: lines)
         {
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

                    genCoordinates(coordinates,params);
                    shapes.add(new Line(pen,coordinates));
                    break;

                case "RECTANGLE":

                    genCoordinates(coordinates,params);
                    shapes.add(new Rectangle(pen,fill,coordinates));
                    break;

                case "PLOT":

                    genCoordinates(coordinates,params);
                    shapes.add(new Plot(pen,coordinates));
                    break;

                case "ELLIPSE":

                    genCoordinates(coordinates,params);
                    shapes.add(new Ellipse(pen,fill,coordinates));
                    break;

                case "POLYGON":

                    genCoordinates(coordinates,params);
                    shapes.add(new Polygon(pen,fill,coordinates));
                    break;

                default:
            }
        }

        /*
        * Potentially draw shapes here
        for (Shape shape : shapes)
            ;// shape.draw();

         */
    }


    public void writeShapes() throws IOException {
        // Need to convert the shapes ArrayList into proper format for the VEC file
        Files.writeString(this.vecFile,this.shapes.toString(),this.charset); // Overwrites file with new instructions
    }


    public void addShapes(ArrayList<Shape> shapes){
        this.shapes.addAll(shapes);
    }
}
