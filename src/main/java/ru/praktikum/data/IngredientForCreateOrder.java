package ru.praktikum.data;

import java.util.ArrayList;

public class IngredientForCreateOrder {
    private String[] ingredients;

    public IngredientForCreateOrder() {
    }

    public IngredientForCreateOrder(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
