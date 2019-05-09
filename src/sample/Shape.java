package sample;

public class Shape {

    // Define pen and fill default values
    private int pen = 0;
    private int fill = 0;

    // Overloading constructor so we can have "default" values
    public Shape(){}

    // Constructor to change pen and fill values
    public Shape(int pen, int fill){
        this.pen = pen;
        this.fill = fill;
    }

    public int getPen(){
        return this.pen;
    }

    public int getFill(){
        return this.fill;
    }

}
