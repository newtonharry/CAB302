package sample;


public class Shape {

    private int pen;  // pen colour
    private int fill; // fill colour

    // Constructor to change pen and fill values
    public Shape(int pen, int fill){
        this.pen = pen;
        this.fill = fill;
    }

    // Constructor to change pen value
    public Shape(int pen){
        this.pen = pen;
    }

    public int getPen(){
        return this.pen;
    }

    public int getFill(){
        return this.fill;
    }

}
