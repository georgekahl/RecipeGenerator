package pantry.service;

import pantry.model.Ingredient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpiryService{
    public List<Ingredient> getExpiringSoon(List<Ingredient> ingredients, int days){
        List<Ingredient> expiringSoon = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate expiryLimit = today.plusDays(days);

        for(Ingredient ingredient : ingredients){
            LocalDate expiryDate = ingredient.getExpiryDate();

            if(!expiryDate.isBefore(today) && !expiryDate.isAfter(expiryLimit)){
                expiringSoon.add(ingredient);
            }
        }
        return expiringSoon;
    }
}