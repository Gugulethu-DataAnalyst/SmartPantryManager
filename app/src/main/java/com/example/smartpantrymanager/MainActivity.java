//initializingthe source smartpantry manager as package
package com.example.smartpantrymanager;

//importing all modules and libraries
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

//initializing the public main class extending the appcomtActivity
public class MainActivity extends AppCompatActivity {
    //initializing the variables
    private RecyclerView recyclerView;
    private TextView txtEmptyPantry;
    private Button btnAddItem, btnSuggestions, btnSettings;
    private DatabaseHelper dbHelper;

    //overriding the onCreate function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerPantry);
        txtEmptyPantry = findViewById(R.id.txtEmptyPantry);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnSuggestions = findViewById(R.id.btnSuggestions);
        btnSettings = findViewById(R.id.btnSettings);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Navigation Click Listeners
        btnAddItem.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddEditActivity.class))
        );

        btnSuggestions.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SuggestedRecipesActivity.class))
        );

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class))
        );
    }

    //overriding the onResume function
    @Override
    protected void onResume() {
        super.onResume();
        loadPantryData(); // Refresh list every time activity becomes visible
    }

    //loading pantry data
    private void loadPantryData() {
        List<PantryItem> itemList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllPantryItems();

        //if conditional statement for moving data
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_NAME));
                double qty = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_QTY));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_UNIT));
                String expiry = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_EXPIRY));

                itemList.add(new PantryItem(id, name, qty, unit, expiry));
            } while (cursor.moveToNext());
        }
        cursor.close();

        //creating a condition that will look at the item list
        // if empty the settting the visibility to View.Gone
        //else view.Visible
        if (itemList.isEmpty()) {
            txtEmptyPantry.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        } else {
            txtEmptyPantry.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            PantryAdapter adapter = new PantryAdapter(itemList, new PantryAdapter.OnItemClickListener() {
                //overriding the edit click button to get the variables from the databaste
                @Override
                public void onEditClick(PantryItem item) {
                    Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                    intent.putExtra("ITEM_ID", item.getId());
                    intent.putExtra("ITEM_NAME", item.getName());
                    intent.putExtra("ITEM_QTY", item.getQuantity());
                    intent.putExtra("ITEM_UNIT", item.getUnit());
                    intent.putExtra("ITEM_EXPIRY", item.getExpiryDate());
                    startActivity(intent);
                }

                //overriding the click delete function
                @Override
                public void onDeleteClick(PantryItem item) {
                    boolean deleted = dbHelper.deletePantryItem(item.getId());
                    if (deleted) {
                        Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                        loadPantryData(); // Refresh view after deletion
                    } else {
                        Toast.makeText(MainActivity.this, "Error deleting item", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}