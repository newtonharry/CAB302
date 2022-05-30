package oop.Pasta;

public class Pasta {
    // Private properties
    private int price;
    private String type;

    // Constructs a Pasta object with a price and a type
    public Pasta(String type, int price) {
        this.type = type;
        this.price = price;
    }

    // Gets the price of the pasta
    public int price() {
        return price;
    }

    // Gets the type of the pasta
    public String type() {
        return type;
    }
}