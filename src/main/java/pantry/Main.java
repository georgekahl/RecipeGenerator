import model.Ingredient;
import model.Recipe;
import model.RecipeIngredient;
import model.ShoppingItem;
import model.ShoppingList;

import service.ExpiryService;
import service.PantryService;
import service.RecipeService;
import service.ShoppingListService;

import java.time.LocalDate;
import java.util.List;

public class Main{
    public static void main(String[] args){
        PantryService pantryService = new PantryService();

        Ingredient chicken = new Ingredient("Chicken", 300, "g", LocalDate.now().plusDays(2));
        Ingredient rice = new Ingredient("Rice", 1000, "g", LocalDate.now().plusMonths(6));
        Ingredient onion = new Ingredient("Onion", 1, "item", LocalDate.now().plusDays(5));

        pantryService.addIngredient(chicken);
        pantryService.addIngredient(rice);
        pantryService.addIngredient(onion);

        System.out.println("Pantry");
        pantryService.displayPantry();

        Recipe chickenRice = new Recipe("Chicken and Rice");

        chickenRice.addIngredient(new RecipeIngredient("Chicken", 500, "g"));
        chickenRice.addIngredient(new RecipeIngredient("Rice", 300, "g"));
        chickenRice.addIngredient(new RecipeIngredient("Onion", 2, "item"));

        RecipeService recipeService = new RecipeService(pantryService);
        recipeService.addRecipe(chickenRice);

        System.out.println();
        System.out.println("Recipe check");

        if (recipeService.canMakeRecipe(chickenRice)){
            System.out.println("You can make " + chickenRice.getName());
        }else{
            System.out.println("You cannot make " + chickenRice.getName());
        }

        ShoppingListService shoppingListService = new ShoppingListService(pantryService);
        ShoppingList shoppingList = shoppingListService.generateShoppingList(chickenRice);

        System.out.println();
        System.out.println("Shopping list");

        for (ShoppingItem item : shoppingList.getItems()){
            System.out.println(item.getName() + " - " + item.getQuantity() + " - " + item.getUnit());
        }

        ExpiryService expiryService = new ExpiryService();
        List<Ingredient> expiringSoon = expiryService.getExpiringSoon(pantryService.getIngredients(),3);

        System.out.println();
        System.out.println("Expiring soon");

        for (Ingredient ingredient : expiringSoon){
            System.out.println(ingredient.getName() + " expries on " + ingredient.getExpiryDate());
        }

    }
}