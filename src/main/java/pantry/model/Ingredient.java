package pantry.model;

import java.time.LocalDate;

public class Ingredient{
    private String name;
    private double quantity;
    private String unit;
    private LocalDate expiryDate;

    public Ingredient(String name, double quantity, String unit, LocalDate expiryDate){
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
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

    public LocalDate getExpiryDate(){
        return expiryDate;
    }

    public void setQuantity(double quantity) {
    this.quantity = quantity;
}

    public void setExpiryDate(LocalDate expiryDate){
        this.expiryDate = expiryDate;
    }
}