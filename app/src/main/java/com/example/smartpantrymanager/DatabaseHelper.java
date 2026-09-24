package com.example.smartpantrymanager;

//importing the modules
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//initializing public class extending to the SQLiteOpenHelper
public class DatabaseHelper extends SQLiteOpenHelper {

    //initializibg the database
    private static final String DATABASE_NAME = "SmartPantry.db";
    //Bumped to version 2 so existing databases will trigger the onUpgrade()
    // and re-seed recipes with commas
    private static final int DATABASE_VERSION = 2;

    //Pantry table attributes
    public static final String TABLE_PANTRY = "pantry";
    public static final String COL_PANTRY_ID = "id";
    public static final String COL_PANTRY_NAME = "name";
    public static final String COL_PANTRY_QTY = "quantity";
    public static final String COL_PANTRY_UNIT = "unit";
    public static final String COL_PANTRY_EXPIRY = "expiry_date";

    //Recipes table attributes
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_RECIPE_ID = "id";
    public static final String COL_RECIPE_NAME = "name";
    public static final String COL_RECIPE_INGREDIENTS = "ingredients";
    public static final String COL_RECIPE_STEPS = "steps";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPantry = "CREATE TABLE " + TABLE_PANTRY + " (" +
                COL_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PANTRY_NAME + " TEXT, " +
                COL_PANTRY_QTY + " REAL, " +
                COL_PANTRY_UNIT + " TEXT, " +
                COL_PANTRY_EXPIRY + " TEXT)";

        String createRecipes = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPE_NAME + " TEXT, " +
                COL_RECIPE_INGREDIENTS + " TEXT, " +
                COL_RECIPE_STEPS + " TEXT)";

        db.execSQL(createPantry);
        db.execSQL(createRecipes);

        seedInitialRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    private void seedInitialRecipes(SQLiteDatabase db) {
        insertRecipeSeed(db, "Simple Pasta", "pasta,tomato sauce,cheese", "1. Boil pasta.\n2. Warm sauce.\n3. Combine and top with cheese.");
        insertRecipeSeed(db, "Scrambled Eggs", "egg,butter,salt", "1. Melt butter in pan.\n2. Whisk eggs with salt.\n3. Cook low and slow.");
        insertRecipeSeed(db, "Grilled Cheese", "bread,cheese,butter", "1. Butter bread slices.\n2. Add cheese inside.\n3. Grill until golden.");
        insertRecipeSeed(db, "Chicken Rice Bowl", "chicken,rice,soy sauce", "1. Cook rice.\n2. Pan-fry chicken.\n3. Mix with soy sauce.");
        insertRecipeSeed(db, "Oatmeal", "oats,milk,honey", "1. Heat oats with milk.\n2. Stir well.\n3. Top with honey.");
        insertRecipeSeed(db, "Omelette", "egg,cheese,bell pepper", "1. Whisk eggs.\n2. Pour into pan with peppers.\n3. Add cheese and fold.");
        insertRecipeSeed(db, "Pancakes", "flour,milk,egg,butter", "1. Whisk ingredients into batter.\n2. Pour onto griddle.\n3. Flip when bubbly.");
        insertRecipeSeed(db, "Garlic Bread", "bread,garlic,butter", "1. Mix garlic and butter.\n2. Spread on bread.\n3. Bake until crisp.");
        insertRecipeSeed(db, "Tomato Soup", "tomato,butter,milk", "1. Puree cooked tomatoes.\n2. Simmer with milk and butter.");
        insertRecipeSeed(db, "Rice Pudding", "rice,milk,sugar", "1. Cook rice in milk.\n2. Stir in sugar until creamy.");
        insertRecipeSeed(db, "Fried Rice", "rice,egg,soy sauce", "1. Stir fry cold cooked rice.\n2. Push aside, scramble egg.\n3. Mix with soy sauce.");
        insertRecipeSeed(db, "Banana Smoothie", "banana,milk,honey", "1. Place all items in blender.\n2. Blend until smooth.");
        insertRecipeSeed(db, "French Toast", "bread,egg,milk,cinnamon", "1. Whisk egg, milk, cinnamon.\n2. Dip bread.\n3. Fry in butter.");
        insertRecipeSeed(db, "Mashed Potatoes", "potato,butter,milk", "1. Boil potatoes.\n2. Mash with warm milk and butter.");
        insertRecipeSeed(db, "BLT Sandwich", "bread,bacon,lettuce,tomato", "1. Fry bacon.\n2. Layer bacon, lettuce, and tomato on bread.");
    }

    private void insertRecipeSeed(SQLiteDatabase db, String name, String ingredients, String steps) {
        ContentValues cv = new ContentValues();
        cv.put(COL_RECIPE_NAME, name);
        cv.put(COL_RECIPE_INGREDIENTS, ingredients);
        cv.put(COL_RECIPE_STEPS, steps);
        db.insert(TABLE_RECIPES, null, cv);
    }

    /*--------------------------------------
    CRUD OPERATIONS FOR PANTRY
    ---------------------------------------*/
    public boolean addPantryItem(String name, double quantity, String unit, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PANTRY_NAME, name.trim().toLowerCase());
        cv.put(COL_PANTRY_QTY, quantity);
        cv.put(COL_PANTRY_UNIT, unit);
        cv.put(COL_PANTRY_EXPIRY, expiry);
        return db.insert(TABLE_PANTRY, null, cv) != -1;
    }

    public Cursor getAllPantryItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PANTRY, null);
    }

    public boolean updatePantryItem(int id, String name, double quantity, String unit, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PANTRY_NAME, name.trim().toLowerCase());
        cv.put(COL_PANTRY_QTY, quantity);
        cv.put(COL_PANTRY_UNIT, unit);
        cv.put(COL_PANTRY_EXPIRY, expiry);
        return db.update(TABLE_PANTRY, cv, COL_PANTRY_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PANTRY, COL_PANTRY_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public Cursor getAllRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RECIPES, null);
    }
}