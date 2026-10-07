import java.util.ArrayList;
import java.util.List;
public class Ingredient{
    private String name;
    private double quantity;
    private String unit;
    private LocalDate expiryDate;

    public Ingredient(String name, double quantity, String unit, LocalDate expiryDate){
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expirdyDate;
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

    public void setQuanity(double quantity){
        this.quanity = quantity;
    }

    public void setExpiryDate(LocalDate expiryDate){
        this.expiryDate = expiryDate;
    }
}