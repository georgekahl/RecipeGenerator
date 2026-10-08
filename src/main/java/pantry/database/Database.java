package pantry.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:sqlite:pantry.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTables() {

        // Ingredients taboe
        String ingredientsSql = """
                CREATE TABLE IF NOT EXISTS ingredients (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE,
                    quantity REAL NOT NULL,
                    unit TEXT NOT NULL,
                    expiry_date TEXT
                )
                """;

        // Recipes table
        String recipesSql = """
                CREATE TABLE IF NOT EXISTS recipes(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                instructions TEXT
                )
                """;

        // Recipe ingredients table
        String recipeIngredientSql = """
                CREATE TABLE IF NOT EXISTS recipe_ingredients(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                recipe_id INTEGER NOT NULL,
                ingredient_name TEXT NOT NULL,
                quantity REAL NOT NULL,
                unit TEXT NOT NULL,
                FOREIGN KEY (recipe_id) REFERENCES recipes(id)
                )
                """;



        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.execute(ingredientsSql);
            statement.execute(recipesSql);
            statement.execute(recipeIngredientSql);

            System.out.println("Ingredients table created!");

        } catch (SQLException e) {
            System.out.println("Failed to create ingredients table.");
            e.printStackTrace();
        }
    }
}