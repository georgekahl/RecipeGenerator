package pantry.service;

import pantry.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class PantryService{
    private List<Ingredient> ingredients;

    public PantryService(){
        ingredients = new ArrayList<>();
    }
    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }

    public void removeIngredient(String name){
        for (Ingredient ingredient : ingredients){
            if (ingredient.getName().equalsIgnoreCase(name)){
                ingredients.remove(ingredient);
                return;
            }
        }
    }

    public void updateQuantity(String name, double quantity){
        for (Ingredient ingredient : ingredients){
            if (ingredient.getName().equalsIgnoreCase(name)){
                ingredient.setQuantity(quantity);
                return;
            }
        }
    }

    public Ingredient findIngredient(String name){
        for (Ingredient ingredient : ingredients){
            if(ingredient.getName().equalsIgnoreCase(name)){
                return ingredient;
            }
        }
        return null;
    }

    public void displayPantry(){
        for (Ingredient ingredient: ingredients){
            System.out.println(ingredient.getName() + " - " + ingredient.getQuantity() + " - " + ingredient.getUnit());
        }
    }

    public List<Ingredient> getIngredients(){
        return ingredients;
    }
}