import java.util.ArrayList;
import java.util.List;
public class ShoppingListService{
    public ShoppingList generateShoppingList(List<Recipe> recipes){
        ShoppingList shoppingList = new ShoppingList();
        for(Recipe recipe : recipes){
            for(RecipeIngredient recipeIngredient : recipe.getIngredients()){
                ingredient pantryIngredient = pantryService.findIngredient(recipeIngredient.getName());

                if (pantryIngredient == null){
                    ShoppingItem = new ShoppingItem(recipeIngredient.getName(), recipeIngredient.getQuantity(), recipeIngredient.getUnit());
                    shoppingList.addItem(item);
                }
                else if (pantryIngredient.getQuantity() < recipeIngredient.getQuantity()){
                    double missingQuantity = recipeIngredient.getQuantity() - pantryIngredient.getQuantity();
                    ShoppingItem = new ShoppingItem(recipeIngredient.getName(), missingQuantity, recipeIngredient.getUnit());
                }
                shoppingList.addItem(item);
            }
        }
        // Compare recipe ingredients with whats in the pantry and create shopping list
        
        return shoppingList;
    }
}