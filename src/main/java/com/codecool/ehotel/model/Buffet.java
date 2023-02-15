package com.codecool.ehotel.model;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public record Buffet(List<FoodItem> foodItems) {
    public void addMany(List<FoodItem> foodItems) {
        this.foodItems.addAll(foodItems);
    }

    public void removeMany(List<FoodItem> foodItems) {
        this.foodItems.removeAll(foodItems);
    }
    public void removeOne(FoodItem meal){
        foodItems.remove(meal);
    }
    public Optional<FoodItem> getFreshestMeal(FoodType foodType){
        return foodItems.stream().filter(item -> item.getType().equals(foodType))
                 .sorted((o1, o2) -> o1.getAgeCycle() - o2.getAgeCycle())
                 .findFirst();
    }
    public void increaseAgePairItem() {
        foodItems.forEach(FoodItem::increaseAge);
    }
    public List<FoodItem> expiredMeals(){
        return foodItems.stream().filter(FoodItem::isItemExpired).toList();
    }



}
