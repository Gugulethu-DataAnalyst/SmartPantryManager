package com.example.smartpantrymanager;

//importing the module needed
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView txtTitle = findViewById(R.id.txtRecipeTitle);
        TextView txtIngredients = findViewById(R.id.txtRecipeIngredients);
        TextView txtSteps = findViewById(R.id.txtRecipeSteps);

        String title = getIntent().getStringExtra("RECIPE_TITLE");
        String ingredients = getIntent().getStringExtra("RECIPE_INGREDIENTS");
        String steps = getIntent().getStringExtra("RECIPE_STEPS");

        if (title != null) txtTitle.setText(title);
        if (ingredients != null) txtIngredients.setText(ingredients);
        if (steps != null) txtSteps.setText(steps);
    }
}