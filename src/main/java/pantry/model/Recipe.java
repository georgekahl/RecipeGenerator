package pantry.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe{
    private String name;
    private List<RecipeIngredient> ingredients;
    private String instructions;

    public Recipe(String name){

        this.name = name;
        this.ingredients = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public List<RecipeIngredient> getIngredients(){
        return ingredients;
    }

    public void addIngredient(RecipeIngredient ingredient){
        ingredients.add(ingredient);
    }
    
    public void removeIngredient(RecipeIngredient ingredient){
        ingredients.remove(ingredient);
    }
}