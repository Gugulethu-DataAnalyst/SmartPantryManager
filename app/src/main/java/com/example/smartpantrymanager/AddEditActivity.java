//initializing the smartpantry package source
package com.example.smartpantrymanager;

//importing the libaries and module
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    //creating private accessible variable functions for the systems
    // edittext, button,text view and database helper
    private EditText edtName, edtQty, edtUnit, edtExpiry;
    private Button btnSave;
    private TextView txtHeader;
    private DatabaseHelper dbHelper;
    private int itemId = -1;

    //overriding the oncreate function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        dbHelper = new DatabaseHelper(this);

        txtHeader = findViewById(R.id.txtHeader);
        edtName = findViewById(R.id.edtItemName);
        edtQty = findViewById(R.id.edtQuantity);
        edtUnit = findViewById(R.id.edtUnit);
        edtExpiry = findViewById(R.id.edtExpiry);
        btnSave = findViewById(R.id.btnSave);

        // Check if editing an existing item via Intent extras
        if (getIntent().hasExtra("ITEM_ID")) {
            itemId = getIntent().getIntExtra("ITEM_ID", -1);
            if (txtHeader != null) txtHeader.setText("Edit Pantry Item");
            edtName.setText(getIntent().getStringExtra("ITEM_NAME"));
            edtQty.setText(String.valueOf(getIntent().getDoubleExtra("ITEM_QTY", 0)));
            edtUnit.setText(getIntent().getStringExtra("ITEM_UNIT"));
            edtExpiry.setText(getIntent().getStringExtra("ITEM_EXPIRY"));
        }

        btnSave.setOnClickListener(v -> saveItem());
    }

    private void saveItem() {
        String name = edtName.getText().toString().trim();
        String qtyStr = edtQty.getText().toString().trim();
        String unit = edtUnit.getText().toString().trim();
        String expiry = edtExpiry.getText().toString().trim();

        // Input validation sequence
        if (name.isEmpty() || qtyStr.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double qty;
        try {
            qty = Double.parseDouble(qtyStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid numeric quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success;
        if (itemId == -1) {
            success = dbHelper.addPantryItem(name, qty, unit, expiry);
        } else {
            success = dbHelper.updatePantryItem(itemId, name, qty, unit, expiry);
        }

        //if success then the pantry will update, else error
        if (success) {
            Toast.makeText(this, "Pantry updated successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving pantry item", Toast.LENGTH_SHORT).show();
        }
    }
}