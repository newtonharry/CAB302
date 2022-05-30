package oop.Shapes;

public class Tetrahedron implements Shape {

    private double edge;

    public Tetrahedron(double edge){
        this.edge = edge;
    }

    public double volume(){
        return Math.pow(edge,3) / (6 * Math.sqrt(2));
    }

    public double surfaceArea(){
        return Math.sqrt(3) * Math.pow(edge,2);
    }
}
