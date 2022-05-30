package oop.Shapes;

public class Cone implements   Shape {

    private double radius;
    private double height;

    public Cone(double radius, double height){
        this.radius = radius;
        this.height = height;
    }

    public double volume(){
        return Math.PI * Math.pow(radius,2) * (height / 3);
    }

    public double surfaceArea(){
        return (Math.PI * radius) * (radius + Math.sqrt((Math.pow(height,2) + Math.pow(radius,2))));
    }
}
