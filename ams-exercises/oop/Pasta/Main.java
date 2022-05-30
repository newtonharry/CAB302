package oop.Pasta;

public class Main {
    public static void main(String[] args) {

        // Instantiate a $12 Linguine pasta called p1
        Pasta p1 = new Pasta("Linguine",12);

        // Instantiate a $14 Spaghetti pasta called p2
        Pasta p2 = new Pasta("Spaghetti",14);

        // Fix the below print statements.
        System.out.println("p1 has price: " + p1.price());
        System.out.println("p2 has type: " + p2.type());
    }
}