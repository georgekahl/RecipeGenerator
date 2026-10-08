package pantry.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import pantry.database.IngredientDao;
import pantry.database.RecipeDao;
import pantry.model.Ingredient;
import pantry.model.Recipe;
import pantry.service.PantryService;
import pantry.service.RecipeService;

import java.time.LocalDate;


public class HomeController{
    @FXML
    private Label pantryCount;

    @FXML
    private Label recipeCount;

    @FXML
    private Label expiringCount;

    private PantryService pantryService;
    private RecipeService recipeService;

    @FXML
    private void initialize(){
        pantryService = new PantryService(new IngredientDao());
        recipeService = new RecipeService(pantryService);

        updateDashboard();
    }

    private void updateDashboard(){
        int pantrySize = pantryService.getIngredients().size();
        pantryCount.setText(String.valueOf(pantrySize));

        int recipeSize = recipeService.getRecipes().size();
        recipeCount.setText(String.valueOf(recipeSize));

        int expiring = 0;

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysFromNow = today.plusDays(7);

        for (Ingredient ingredient : pantryService.getIngredients()){
            LocalDate expiryDate = ingredient.getExpiryDate();

            if(expiryDate != null && !expiryDate.isBefore(today) && !expiryDate.isAfter(sevenDaysFromNow)){
                expiring++;
            }
        }
        expiringCount.setText(String.valueOf(expiring));
    }

    @FXML
    private void openPantry(ActionEvent event){
        try {
            Parent pantryPage = FXMLLoader.load(getClass().getResource( "/fxml/pantry.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(pantryPage, 1100, 700));
            stage.setTitle("Recipe Generator - Pantry");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openRecipes(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/recipes.fxml"));
            Parent recipesPage = loader.load();
            Stage stage = (Stage) pantryCount.getScene().getWindow();
            stage.setScene(new Scene(recipesPage, 1100, 700));
            stage.setTitle("Recipe Generator - Recipes");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void openShoppingList(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/shopping-list.fxml"));
            Parent shoppingListPage = loader.load();
            Stage stage = (Stage) pantryCount.getScene().getWindow();
            stage.setScene(new Scene(shoppingListPage, 1100, 700));
            stage.setTitle("Recipe Generator - ShoppingList");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}