public class ExipiryService{
    public List<Ingredient> getExpiringSoon(List<Ingredient> ingredients, int days){
        List<Ingredient> expiringSoon = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate expiryLimit = today.plusDays(days);

        for(ingredient ingredient : ingredients){
            LocalDate expiryDate = ingredients.getExpiryDate();

            if(!expiryDate.isBefore(today) && !expiryDate.isAfter(expiryLimit)){
                expiringSoon.add(ingredient);
            }
        }

        //find ingredients expiring soon
        return expiringSoon;
    }
}