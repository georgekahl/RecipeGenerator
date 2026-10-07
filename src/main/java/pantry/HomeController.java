package pantry;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController{
    @FXML
    private Label pantryCount;

    @FXML
    private Label recipeCount;

    @FXML
    private Label expiringCount;

    @FXML
    private void openPantry(){
        System.out.println("Pantry button clicked");
    }

    @FXML
    private void openRecipes(){
        System.out.println("Recipes button clicked");
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