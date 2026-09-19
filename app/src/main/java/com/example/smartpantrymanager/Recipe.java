package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipe {
    private int id;
    private String title;
    private String ingredients;
    private String instructions;

    public Recipe(int id, String title, String ingredients, String instructions) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe(String title, String ingredients, String instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    // Converts comma-separated ingredients string into a List<String>
    public List<String> getRequiredIngredientsList() {
        List<String> list = new ArrayList<>();
        if (ingredients != null && !ingredients.trim().isEmpty()) {
            String[] split = ingredients.split(",");
            for (String item : split) {
                if (!item.trim().isEmpty()) {
                    list.add(item.trim());
                }
            }
        }
        return list;
    }
}