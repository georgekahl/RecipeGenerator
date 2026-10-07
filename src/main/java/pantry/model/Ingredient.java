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
}