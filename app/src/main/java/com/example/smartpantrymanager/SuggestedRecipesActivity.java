//initializing the package
package com.example.smartpantrymanager;

//importing the libararies and modules
import andriod.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


//
public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtEmptyState;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerSuggested);
        txtEmptyState = findViewById(R.id.txtEmptyState);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestions();
    }

    private void loadSuggestions() {
        List<Recipe> matchingRecipes = RecipeMatcher.getSuggestedRecipes(dbHelper);

        if (matchingRecipes.isEmpty()) {
            txtEmptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            txtEmptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            RecipeAdapter adapter = new RecipeAdapter(matchingRecipes, recipe -> {
                Intent intent = new Intent(SuggestedRecipesActivity.this, RecipeDetailActivity.class);
                intent.putExtra("RECIPE_NAME", recipe.getName());
                intent.putExtra("RECIPE_INGREDIENTS", recipe.getIngredients());
                intent.putExtra("RECIPE_STEPS", recipe.getSteps());
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        }
    }
}