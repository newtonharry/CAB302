package patt.Coffee;

import java.util.ArrayList;

public class Coffee {
    CoffeeFactory.Type type;
    double cost;
    ArrayList<CoffeeFactory.Ingredient> ingredients;

    public Coffee(ArrayList<CoffeeFactory.Ingredient> ingredients, CoffeeFactory.Type type) {
        this.type = type;

        this.ingredients = ingredients;

        double sum = 0;
        for (CoffeeFactory.Ingredient ingredient : ingredients) {
            if (ingredient == CoffeeFactory.Ingredient.ESPRESSO) {
                sum += 0.5;
            } else if (ingredient == CoffeeFactory.Ingredient.MILK) {
                sum += 1.0;
            } else if (ingredient == CoffeeFactory.Ingredient.CHOCOLATE) {
                sum += 1.5;
            } else {
                sum += 0;
            }
        }
        this.cost = sum;

    }

    public double getCost() {
        return ingredients.stream().map(CoffeeFactory.Ingredient::getCost).mapToDouble(d -> d).sum();
    }

    public double getPrice() {
        return this.type.getPrice();

       /*
        if (this.type.equals(CoffeeFactory.Type.LONG_BLACK)) {
            return 4.0;
        } else if (this.type.equals(CoffeeFactory.Type.FLAT_WHITE)) {
            return 5.0;
        } else if (this.type.equals(CoffeeFactory.Type.MOCHA)) {
            return 6.0;
        }
        return 0;
        */
    }

    public String listIngredients() {
        String string = "";
        for (CoffeeFactory.Ingredient ingredient : ingredients) {
            string += ingredient.toString();
            string += "\n";
        }
        return string;
    }
}