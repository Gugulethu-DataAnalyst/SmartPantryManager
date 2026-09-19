package com.example.smartpantrymanager;

import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class RecipeMatcher {

    public static List<Recipe> getSuggestedRecipes(DatabaseHelper dbHelper) {
        List<PantryItem> pantryItems = new ArrayList<>();

        // 1. Read pantry items from Cursor
        Cursor pantryCursor = dbHelper.getAllPantryItems();
        if (pantryCursor != null) {
            while (pantryCursor.moveToNext()) {
                int idIndex = pantryCursor.getColumnIndex(DatabaseHelper.COL_PANTRY_ID);
                int nameIndex = pantryCursor.getColumnIndex(DatabaseHelper.COL_PANTRY_NAME);
                int qtyIndex = pantryCursor.getColumnIndex(DatabaseHelper.COL_PANTRY_QTY);
                int unitIndex = pantryCursor.getColumnIndex(DatabaseHelper.COL_PANTRY_UNIT);
                int expiryIndex = pantryCursor.getColumnIndex(DatabaseHelper.COL_PANTRY_EXPIRY);

                if (idIndex != -1 && nameIndex != -1 && qtyIndex != -1 && unitIndex != -1 && expiryIndex != -1) {
                    pantryItems.add(new PantryItem(
                            pantryCursor.getInt(idIndex),
                            pantryCursor.getString(nameIndex),
                            pantryCursor.getDouble(qtyIndex),
                            pantryCursor.getString(unitIndex),
                            pantryCursor.getString(expiryIndex)
                    ));
                }
            }
            pantryCursor.close();
        }

        // 2. Read recipes from Cursor using exact DatabaseHelper column constants
        List<Recipe> allRecipes = new ArrayList<>();
        Cursor recipeCursor = dbHelper.getAllRecipes();
        if (recipeCursor != null) {
            while (recipeCursor.moveToNext()) {
                int idIndex = recipeCursor.getColumnIndex(DatabaseHelper.COL_RECIPE_ID);
                int titleIndex = recipeCursor.getColumnIndex(DatabaseHelper.COL_RECIPE_NAME);
                int ingIndex = recipeCursor.getColumnIndex(DatabaseHelper.COL_RECIPE_INGREDIENTS);
                int stepsIndex = recipeCursor.getColumnIndex(DatabaseHelper.COL_RECIPE_STEPS);

                if (idIndex != -1 && titleIndex != -1 && ingIndex != -1 && stepsIndex != -1) {
                    allRecipes.add(new Recipe(
                            recipeCursor.getInt(idIndex),
                            recipeCursor.getString(titleIndex),
                            recipeCursor.getString(ingIndex),
                            recipeCursor.getString(stepsIndex)
                    ));
                }
            }
            recipeCursor.close();
        }

        // 3. Match recipes against pantry ingredients
        List<Recipe> matchingRecipes = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            if (hasAllIngredients(pantryItems, recipe)) {
                matchingRecipes.add(recipe);
            }
        }
        return matchingRecipes;
    }

    private static boolean hasAllIngredients(List<PantryItem> pantryItems, Recipe recipe) {
        List<String> requiredIngredients = recipe.getRequiredIngredientsList();
        if (requiredIngredients == null || requiredIngredients.isEmpty()) {
            return false;
        }

        for (String required : requiredIngredients) {
            boolean found = false;
            for (PantryItem pantryItem : pantryItems) {
                if (pantryItem.getName().trim().equalsIgnoreCase(required.trim())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}