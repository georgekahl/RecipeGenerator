package pantry.database;

import pantry.model.Ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao{
    public void saveIngredient(Ingredient ingredient){

        String sql = """
                INSERT INTO ingredients (name, quantity, unit, expiry_date) VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = Database.connect(); PreparedStatement statement = connection.prepareStatement(sql)){
            
            statement.setString(1, ingredient.getName());
            statement.setDouble(2, ingredient.getQuantity());
            statement.setString(3, ingredient.getUnit());
            statement.setString(4, ingredient.getExpiryDate().toString());

            statement.executeUpdate();

            System.out.println("Ingredient saved");

        } catch (SQLException e){
            System.out.println("Failed to save ingredient");
            e.printStackTrace();
        }
    }

    public List<Ingredient> findAll(){
        List<Ingredient> ingredients = new ArrayList<>();

        String sql = "SELECT * FROM ingredients";

        try (Connection connection = Database.connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){

                String name = resultSet.getString("name");
                double quantity = resultSet.getDouble("quantity");
                String unit = resultSet.getString("unit");
                String expiryDate = resultSet.getString("expiry_date");

                Ingredient ingredient = new Ingredient(name, quantity, unit, java.time.LocalDate.parse(expiryDate));
                ingredients.add(ingredient);
            }
        } catch (SQLException e){
            System.out.println("Failed to retrieve ingredients");
            e.printStackTrace();
        }
        return ingredients;
    }
}