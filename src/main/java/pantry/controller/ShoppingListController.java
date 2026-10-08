package pantry.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pantry.database.IngredientDao;
import pantry.model.Recipe;
import pantry.model.ShoppingItem;
import pantry.model.ShoppingList;
import pantry.service.PantryService;
import pantry.service.RecipeService;
import pantry.service.ShoppingListService;

public class ShoppingListController{
    @FXML
    private ComboBox<String> recipeComboBox;

    @FXML
    private ListView<String> shoppingListView;

    private ObservableList<String> shoppingItems = FXCollections.observableArrayList();
    private ShoppingListService shoppingListService;
    private RecipeService recipeService;

    @FXML
    private void initialize(){
        PantryService pantryService = new PantryService(new IngredientDao());
        recipeService = new RecipeService(pantryService);
        shoppingListService = new ShoppingListService(pantryService);
        shoppingListView.setItems(shoppingItems);

        for(Recipe recipe : recipeService.getRecipes()){
            recipeComboBox.getItems().add(recipe.getName());
        }
    }

    @FXML
    private void generateShoppingList(){
        String selectedRecipeName = recipeComboBox.getValue();

        if(selectedRecipeName == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Recipe Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a recipe.");
            alert.showAndWait();
            return;
        }

        Recipe selectedRecipe = null;

        for(Recipe recipe : recipeService.getRecipes()){
            if(recipe.getName().equals(selectedRecipeName)){
                selectedRecipe = recipe;
                break;
            }
        }
        if(selectedRecipe == null){
            return;
        }

        ShoppingList shoppingList = shoppingListService.generateShoppingList(selectedRecipe);
        shoppingItems.clear();
        if(shoppingList.getItems().isEmpty()){
            shoppingItems.add("You have everything needed!");
        }else{
            for (ShoppingItem item: shoppingList.getItems()){
                shoppingItems.add(item.getName() + " - " + item.getQuantity() + " " + item.getUnit());
            }
        }
    }

    @FXML
    private void goHome(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
            Parent homePage = loader.load();
            Scene scene = new Scene(homePage, 1100, 700);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = (Stage) shoppingListView.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Recipe Generator");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}