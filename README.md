# SmartPantryManager
This is An offline-first Android application built in Java using Android Studio and SQLite persistence. The application tracks household grocery inventory in real time and uses a strict boolean ingredient-matching engine to suggest recipes that can be cooked immediately using only available pantry stock.

#Overview
App Title: Smart Pantry Manager
Target OS: Android (API Level 24+ / Android 7.0+)
Language & Architecture: Java, Native Android SDK, Model-View-Adapter (MVA)
Storage Engine: SQLite (SQLiteOpenHelper)
Core Value Proposition: Eliminates household food waste and mealtime uncertainty by cross-referencing user pantry stock with a pre-seeded recipe database to surface actionable meals.

#Key Features
Pantry Inventory Management (CRUD):
Add & Edit Items: Record ingredient names, floating-point quantities, measurement units, and ISO expiration dates (YYYY-MM-DD).
Input Validation: Enforces non-empty string checks and numeric quantity validation prior to SQLite transaction execution.
Delete & List View: Remove items individually; custom RecyclerView adapters update the UI dynamically on lifecycle events (onResume).

Pre-Seeded Recipe CollectionSeeded Database: 
Pre-loads 15 distinct recipes upon initial app setup into SQLite.
Structured Entities: Each recipe entry includes a primary key ID, title, comma-separated required ingredients list, and numbered step-by-step cooking directions.

Strict Ingredient-Matching Engine (RecipeMatcher)Deterministic Matching:
Evaluates active pantry inventory against the recipe repository using a strict boolean gatekeeper ($R \subseteq P$).
Zero-Tolerance Rule: A recipe is displayed only if 100% of its required ingredients exist in the user's current pantry. Missing even a single required ingredient excludes the recipe from suggestions.

#Environment Setup & Installation Instructions
Open Project:
Launch Android Studio.
Select Open and select the root directory of the cloned project.

Build & Sync:
Allow Gradle to download dependencies and sync automatically.

Run Configuration:
Select an emulator profile (e.g., Small Phone AVD running API 30 with 1024 MB RAM) or attach a physical Android device with USB Debugging enabled.
Press Shift + F10 or click the green Run button.
