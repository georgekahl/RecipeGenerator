package model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList{
    private List<ShoppingItem> items;

    public ShoppingList(){
        items = new ArrayList<>();
    }

    public List<ShoppingItem> getItems(){
        return items;
    }

    public void addItem(ShoppingItem item){
        items.add(item);
    }

    public void removeItem(ShoppingItem item){
        items.remove(item);
    }
}