package model;

public class RecipeIngredient{
    private String ingredientName;
    private double quantity;
    private String unit;

    public RecipeIngredient(String ingredientName, double quantity, String unit){

    this.ingredientName = ingredientName;
    this.quantity = quantity;
    this.unit = unit;
    }

    public String getName(){
        return name;
    }

    public double getQuantity(){
        return quantity;
    }

    public String getUnit(){
        return unit;
    }

    public void setQuanitty(double quantity){
        this.quantity = quantity;
    }
}