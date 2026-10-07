package model;

import java.util.ArrayList;
import java.util.List;

public class Recipe{
    private String name;
    private List<RecipeIngredient> ingredients;
    private String instructions;

    public Recipe(String name, List<RecipeIngredient> ingredients, String instructions){

        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getName(){
        return name;
    }

    public List<RecipeIngredient> getIngredients(){
        return ingredients;
    }
}