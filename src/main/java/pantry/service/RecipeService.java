public class RecipeService{
    private PantryService PantryService;
    private List<Recipe> recipes;
    public RecipeService(PantryService pantryService){
        this.pantryService = pantryService;
        this.recipes = new ArrayList<>();
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
    }
}
