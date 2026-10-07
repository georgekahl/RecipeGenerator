public class Recipe{
    private String name;
    private List<RecipeIngredient> ingredients;
    private String instructions;

    public Recipe(String name, List<RecipeIngredient> ingredients, String instructions){

        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
}