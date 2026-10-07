package service;

import model.Ingredient;
import model.Recipe;
import model.RecipeIngredient;
import model.ShoppingItem;
import model.ShoppingList;

public class ShoppingListService{
    private PantryService pantryService;

    public ShoppingListService(PantryService pantryService){
        this.pantryService = pantryService;
    }
    public ShoppingList generateShoppingList(Recipe recipe){
        ShoppingList shoppingList = new ShoppingList();
        for(RecipeIngredient recipeIngredient : recipe.getIngredients()){
            Ingredient pantryIngredient = pantryService.findIngredient(recipeIngredient.getName());

            if (pantryIngredient == null){
                ShoppingItem item = new ShoppingItem(recipeIngredient.getName(), recipeIngredient.getQuantity(), recipeIngredient.getUnit());
                shoppingList.addItem(item);
            }
            else if (pantryIngredient.getQuantity() < recipeIngredient.getQuantity()){
                double missingQuantity = recipeIngredient.getQuantity() - pantryIngredient.getQuantity();
                ShoppingItem item = new ShoppingItem(recipeIngredient.getName(), missingQuantity, recipeIngredient.getUnit());
                shoppingList.addItem(item);
            }
        }        
    return shoppingList;
    }
}