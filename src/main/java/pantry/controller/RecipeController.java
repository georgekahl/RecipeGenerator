package pantry.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pantry.model.Recipe;
import pantry.model.RecipeIngredient;
import pantry.database.RecipeDao;
import pantry.service.PantryService;
import pantry.service.RecipeService;

import java.util.ArrayList;
import java.util.List;

public class RecipeController {
    @FXML
    private ListView<String> recipeList;

    private ObservableList<String> recipes = FXCollections.observableArrayList();
    private RecipeService recipeService;

    @FXML
    private void initialize(){
        PantryService pantryService = new PantryService(new pantry.database.IngredientDao());
        recipeService = new RecipeService(pantryService);
        recipeList.setItems(recipes);

        for(Recipe recipe : recipeService.getRecipes()){
            recipes.add(recipe.getName());
        }
    }

    @FXML
    private void addRecipe(){

        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("Add Recipe");
        dialog.setHeaderText("Create a new recipe");

        ButtonType saveButtonType = new ButtonType("Save Recipe", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField recipeNameField = new TextField();
        recipeNameField.setPromptText("Recipe name");

        ListView<String> ingredientList = new ListView<>();
        Button addIngredientButton = new Button("Add Ingredient");
        Button removeIngredientButton = new Button("Remove Ingredient");

        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Recipe Name:"), 0, 0);
        grid.add(recipeNameField, 1, 0);
        grid.add(new Label("Ingredients:"), 0, 1);
        grid.add(ingredientList, 1, 1);
        grid.add(addIngredientButton, 1, 2);
        grid.add(removeIngredientButton, 1, 3);

        dialog.getDialogPane().setContent(grid);

        List<RecipeIngredient> ingredients = new ArrayList<>();
        addIngredientButton.setOnAction(event -> {
            Dialog<ButtonType> ingredientDialog = new Dialog<>();
            ingredientDialog.setTitle("Add Ingredient");
            ingredientDialog.setHeaderText("Add an ingredient to the recipe");
            ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            ingredientDialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
            TextField nameField = new TextField();
            nameField.setPromptText("Ingredient name");
            TextField quantityField = new TextField();
            quantityField.setPromptText("Quantity");
            TextField unitField = new TextField();
            unitField.setPromptText("Unit");
            GridPane ingredientGrid = new GridPane();
            ingredientGrid.setHgap(10);
            ingredientGrid.setVgap(10);
            ingredientGrid.add( new Label("Name:"), 0, 0);    
            ingredientGrid.add(nameField, 1, 0);
            ingredientGrid.add(new Label("Quantity"), 0, 1);
            ingredientGrid.add(quantityField, 1, 1);
            ingredientGrid.add(new Label("Unit:"), 0, 2);
            ingredientGrid.add(unitField, 1, 2);
            ingredientDialog.getDialogPane().setContent(ingredientGrid);
            ingredientDialog.showAndWait().ifPresent(result -> {
                if(result == addButton){
                    try{
                        String name = nameField.getText().trim();
                        double quantity = Double.parseDouble(quantityField.getText().trim());
                        String unit = unitField.getText().trim();
                        if(name.isEmpty() || unit.isEmpty() || quantity <= 0){
                            throw new Exception("Invalid ingredient");
                        }
                        RecipeIngredient ingredient = new RecipeIngredient(name, quantity, unit);
                        ingredients.add(ingredient);
                        ingredientList.getItems().add(name + " - " + quantity + " " + unit);
                    }catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Ingredient");
                        alert.setContentText("Please enter a valid name, quantity and unit.");
                        alert.showAndWait();
                    }
                }
            });
        
        });
        removeIngredientButton.setOnAction(event -> {
            int selectedIndex = ingredientList.getSelectionModel().getSelectedIndex();
            if(selectedIndex >= 0){
                ingredients.remove(selectedIndex);
                ingredientList.getItems().remove(selectedIndex);

            }
        });
        dialog.showAndWait().ifPresent(result -> {
            if (result == saveButtonType){
                String recipeName = recipeNameField.getText().trim();
                if(recipeName.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Recipe");
                    alert.setContentText("Please enter a recipe name.");
                    alert.showAndWait();
                    return;
                }
                Recipe recipe = new Recipe(recipeName);
                for (RecipeIngredient ingredient : ingredients){
                    recipe.addIngredient(ingredient);
                }
                recipeService.addRecipe(recipe);
                recipes.add(recipe.getName());
            }
        });
    }

    @FXML
    private void deleteRecipe(){
        int selectedIndex = recipeList.getSelectionModel().getSelectedIndex();
        if(selectedIndex < 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Recipe Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a recipe to delete.");
            alert.showAndWait();
            return;
        }

        String recipeName = recipes.get(selectedIndex);
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Recipe");
        confirmation.setHeaderText("Delete Recipe");
        confirmation.setContentText("Are you sure you want to delete " + recipeName + "?");

        confirmation.showAndWait().ifPresent(result ->{
            if(result == ButtonType.OK){
                recipeService.removeRecipe(recipeName);
                recipes.remove(selectedIndex);
            }
        });
    }

    @FXML
    private void goHome(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
            Parent homePage = loader.load();
            Scene scene = new Scene(homePage, 1100, 700);
            Stage stage = (Stage) recipeList.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Recipe Generator");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
