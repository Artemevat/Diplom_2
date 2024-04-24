package object;

import java.util.List;

public class Order {
    private List<String> ingredients;

    //конструктор с параметрами
    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    //конструктор без параметров
    public Order() {
    }

    //геттеры и сеттеры
    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
