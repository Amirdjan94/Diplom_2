package ru.praktikum.data;

import java.util.List;

public class IngredientAll {
    private String success;
    private List<Ingredient> data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Ingredient> getData() {
        return data;
    }

    public void setData(List<Ingredient> data) {
        this.data = data;
    }
}
