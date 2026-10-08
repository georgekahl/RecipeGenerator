package pantry.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class HomeController{
    @FXML
    private Label pantryCount;

    @FXML
    private Label recipeCount;

    @FXML
    private Label expiringCount;

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
        System.out.println("Shopping List button clicked");
    }

    @FXML
    private void openHome(){
        System.out.println("Home button clicked");
    }



}