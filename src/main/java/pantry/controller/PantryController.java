package pantry.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import pantry.database.IngredientDao;
import pantry.model.Ingredient;
import pantry.service.PantryService;

import java.time.LocalDate;

public class PantryController{
    @FXML
    private TableView<Ingredient> pantryTable;

    @FXML
    private TableColumn<Ingredient, String> nameColumn;

    @FXML
    private TableColumn<Ingredient, Double> quantityColumn;

    @FXML
    private TableColumn<Ingredient, String> unitColumn;

    @FXML
    private TableColumn<Ingredient, LocalDate> expiryColumn;

    private PantryService pantryService;

    @FXML
    private void initialize(){
        pantryService = new PantryService(new IngredientDao());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        expiryColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        refreshTable();
    }

    private void refreshTable(){
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(pantryService.getIngredients());
        pantryTable.setItems(ingredients);
    }

    @FXML
    private void addIngredient(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Ingredient");
        dialog.setHeaderText("Add a new ingredient to your pantry");

        ButtonType addButtonType = new ButtonType("Add Ingredient", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        TextField unitField = new TextField();
        unitField.setPromptText("Unit");

        DatePicker expiryPicker = new DatePicker();
        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(quantityField, 1, 1);

        grid.add(new Label("Unit:"), 0, 2);
        grid.add(unitField, 1, 2);

        grid.add(new Label("Expiry Date:"), 0, 3);
        grid.add(expiryPicker, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait().ifPresent(result -> {
            if (result == addButtonType){
                try{
                    String name = nameField.getText().trim();
                    double quantity = Double.parseDouble(quantityField.getText());
                    String unit = unitField.getText().trim();
                    LocalDate expiryDate = expiryPicker.getValue();

                    Ingredient ingredient = new Ingredient(name , quantity, unit, expiryDate);
                    pantryService.addIngredient(ingredient);
                    refreshTable();

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void goHome(){
        try{
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/home.fxml"));
            javafx.scene.Parent homePage = loader.load();

            javafx.scene.Scene scene = new javafx.scene.Scene(homePage, 1100, 700);
            javafx.stage.Stage stage = (javafx.stage.Stage) pantryTable.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Recipe Generator");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void removeIngredient(){
        Ingredient selectedIngredient = pantryTable.getSelectionModel().getSelectedItem();
        if(selectedIngredient == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Ingredient Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an ingredient to remove");
            alert.showAndWait();

            return;
        }
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Remove Ingredient");
        confirmation.setHeaderText("Remove Ingredient");
        confirmation.setContentText("Are you sure you want to remove " + selectedIngredient.getName() + "?");
        confirmation.showAndWait().ifPresent(result->{
            if (result == ButtonType.OK){
                pantryService.removeIngredient(selectedIngredient.getName());
                refreshTable();
            }
        });
    }
}
