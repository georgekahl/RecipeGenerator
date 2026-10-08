package pantry.service;

import pantry.database.RecipeDao;
import pantry.model.Ingredient;
import pantry.model.Recipe;
import pantry.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeService{
    private PantryService pantryService;
    private List<Recipe> recipes;

    private RecipeDao recipeDao;

    public RecipeService(PantryService pantryService){
        this.pantryService = pantryService;
        this.recipeDao = new RecipeDao();
        this.recipes = recipeDao.findAll();
    }
    public boolean canMakeRecipe(Recipe recipe){
        for (RecipeIngredient recipeIngredient : recipe.getIngredients()){
            Ingredient ingredient = pantryService.findIngredient(recipeIngredient.getName());
        
            if (ingredient == null){
                return false;
            }

            if (ingredient.getQuantity() < recipeIngredient.getQuantity()){
                return false;
            }
        }
        return true;
    }

    public List<Recipe> findRecipes(){
        List<Recipe> availableRecipes = new ArrayList<>();
        for (Recipe recipe: recipes){
            if (canMakeRecipe(recipe)){
                availableRecipes.add(recipe);
            }
        }
        return availableRecipes;
    }

    public void addRecipe(Recipe recipe){
        recipes.add(recipe);
        recipeDao.saveRecipe(recipe);
    }

    public void removeRecipe(String recipeName){
        for (Recipe recipe: recipes){
            if(recipe.getName().equalsIgnoreCase(recipeName)){
                recipes.remove(recipe);
                recipeDao.deleteRecipe(recipeName);
                return;
            }
        }
    }

    public List<Recipe> getRecipes(){
        return recipes;
    }
}
