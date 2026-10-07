package model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList{
    private List<ShoppingItem> items;

    public List<ShoppingItem> getItems(){
        return items;
    }

    public void addItem(Shopping item){
        items.add(item);
    }

    public void removeItem(Shopping item){
        items.remove(item);
    }
}