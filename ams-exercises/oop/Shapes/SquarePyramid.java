package oop.Shapes;

public class SquarePyramid implements Shape {

    private double edge;
    private double height;

    public SquarePyramid(double edge, double height){
        this.edge = edge;
        this.height = height;
    }

    public double volume(){
        return Math.pow(edge,2) * (height/3);
    }

    public double surfaceArea(){
        return Math.pow(edge,2) + (2 * edge) * Math.sqrt((Math.pow(edge,2)/4) + Math.pow(height,2)) ;
    }
}