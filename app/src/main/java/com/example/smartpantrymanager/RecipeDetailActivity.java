package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView txtTitle = findViewById(R.id.txtRecipeTitle);
        TextView txtIngredients = findViewById(R.id.txtIngredientsList);
        TextView txtInstructions = findViewById(R.id.txtInstructions);

        String name = getIntent().getStringExtra("RECIPE_NAME");
        String rawIngredients = getIntent().getStringExtra("RECIPE_INGREDIENTS");
        String steps = getIntent().getStringExtra("RECIPE_STEPS");

        if (txtTitle != null) txtTitle.setText(name);

        // Format pipe-separated ingredients into a bulleted list
        if (txtIngredients != null && rawIngredients != null) {
            String formattedIngredients = "• " + rawIngredients.replace("|", "\n• ");
            txtIngredients.setText(formattedIngredients);
        }

        if (txtInstructions != null) {
            txtInstructions.setText(steps);
        }
    }
}