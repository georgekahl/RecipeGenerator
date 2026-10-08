package pantry.database;

import pantry.model.Recipe;
import pantry.model.RecipeIngredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {
    public void saveRecipe(Recipe recipe){
        String recipeSql = """
                INSERT INTO recipes (name, instructions)
                VALUES (?, ?)
                """;

        String ingredientSql = """
                INSERT INTO recipe_ingredients
                (recipe_id, ingredient_name, quantity, unit)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = Database.connect()){
            connection.setAutoCommit(false);
            try(PreparedStatement recipeStatement = connection.prepareStatement(recipeSql, Statement.RETURN_GENERATED_KEYS)){
                recipeStatement.setString(1, recipe.getName());
                recipeStatement.setString(2, null);

                recipeStatement.executeUpdate();

                ResultSet keys = recipeStatement.getGeneratedKeys();
                if (keys.next()){
                    int recipeId = keys.getInt(1);
                    try(PreparedStatement ingredientStatement = connection.prepareStatement(ingredientSql)){
                        for (RecipeIngredient ingredient: recipe.getIngredients()){
                            ingredientStatement.setInt(1, recipeId);
                            ingredientStatement.setString(2, ingredient.getName());
                            ingredientStatement.setDouble(3, ingredient.getQuantity());
                            ingredientStatement.setString(4, ingredient.getUnit());
                            ingredientStatement.addBatch();
                        }
                        ingredientStatement.executeBatch();
                    }
                }
                connection.commit();
                System.out.println("Recipe saved!");

            }catch (SQLException e){
                connection.rollback();
                throw e;
            }
        }catch (SQLException e){
            System.out.println("Failed to save recipe.");
            e.printStackTrace();
        }
    }

    public List<Recipe> findAll(){
        List<Recipe> recipes = new ArrayList<>();

        String recipeSql = """
                SELECT id, name, instructions
                FROM recipes
                """;
        String ingredientSql = """
                SELECT ingredient_name, quantity, unit
                FROM recipe_ingredients
                WHERE recipe_id = ?
                """;
        try (Connection connection = Database.connect(); 
        PreparedStatement recipeStatement = connection.prepareStatement(recipeSql);
        ResultSet recipeResults = recipeStatement.executeQuery()){
            while(recipeResults.next()){
                int recipeId = recipeResults.getInt("id");
                String name = recipeResults.getString("name");

                Recipe recipe = new Recipe(name);

                try(PreparedStatement ingredientStatement = connection.prepareStatement(ingredientSql)){
                    ingredientStatement.setInt(1, recipeId);
                    try(ResultSet ingredientResults = ingredientStatement.executeQuery()){
                        while(ingredientResults.next()){
                            String ingredientName = ingredientResults.getString("ingredient_name");
                            double quantity = ingredientResults.getDouble("quantity");
                            String unit = ingredientResults.getString("unit");
                            RecipeIngredient ingredient = new RecipeIngredient(ingredientName, quantity, unit);
                            recipe.addIngredient(ingredient);
                        }
                    }

                }

                recipes.add(recipe);

            }
        }catch (SQLException e){
            System.out.println("Failed to retrieve recipes.");
            e.printStackTrace();
        }
        return recipes;
    }

    public void deleteRecipe(String recipeName){
        String findRecipeSql = """
                SELECT id
                FROM recipes
                WHERE name = ?
                """;

        String deleteIngredientSql = """
                DELETE FROM recipe_ingredients
                WHERE recipe_id = ?
                """;

        String deleteRecipeSql = """
                DELETE FROM recipes
                WHERE id = ?
                """;

        try (Connection connection = Database.connect()){
            connection.setAutoCommit(false);
            try{
                int recipeId = -1;

                try(PreparedStatement statement = connection.prepareStatement(findRecipeSql)){
                    statement.setString(1, recipeName);

                    try(ResultSet resultSet = statement.executeQuery()){
                        if(resultSet.next()){
                            recipeId = resultSet.getInt("id");
                        }
                    }
                }
                if(recipeId != -1){
                    try(PreparedStatement statement = connection.prepareStatement(deleteIngredientSql)){
                        statement.setInt(1, recipeId);
                        statement.executeUpdate();
                    }
                    try(PreparedStatement statement = connection.prepareStatement(deleteRecipeSql)){
                        statement.setInt(1, recipeId);
                        statement.executeUpdate();
                    }
                }
                connection.commit();
                System.out.println("Recipe deleted!");
            }catch (SQLException e){
                connection.rollback();
                throw e;
            }
        }catch (SQLException e){
            System.out.println("Failed to delete recipe.");
            e.printStackTrace();
        }

    }
}
    

