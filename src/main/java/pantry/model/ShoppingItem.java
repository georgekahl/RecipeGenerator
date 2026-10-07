package pantry.model;

public class ShoppingItem{
    private String ingredientName;
    private double quantity;
    private String unit;
    private boolean purchased;

    public ShoppingItem(String ingredientName, double quantity, String unit) {

    this.ingredientName = ingredientName;
    this.quantity = quantity;
    this.unit = unit;
    this.purchased = false;
    }

    public String getName(){
        return ingredientName;
    }
    
    public double getQuantity(){
        return quantity;
    }

    public String getUnit(){
        return unit;
    }

    public boolean isPurchased(){
        return purchased;
    }

    public void setQuantity(double quantity){
        this.quantity = quantity;
    }

    public void setPurchase(boolean purchased){
        this.purchased = purchased;
    }


}