package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.*;

import java.util.*;

public class BreakfastManager implements BuffetService {

    Buffet buffet;
    Map<FoodItem, Integer> batch;

    public BreakfastManager(Buffet buffet) {
        this.buffet = buffet;
        this.batch = new HashMap<>();
    }

    public void refill(Map<FoodItem, Integer> batch, Buffet buffet) {
        List<FoodItem> newFoods = new ArrayList<>();
        for (Map.Entry<FoodItem, Integer> entry : batch.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++)
                newFoods.add(entry.getKey());
        }
        buffet.addMany(newFoods);
        batch.clear();
    }

    @Override
    public void createBatch(MealType mealType, int portion, int timeStamp) {
        batch.put(new FoodItem(0, mealType), portion);
    }

    @Override
    public boolean consumeFreshest(List<MealType> preference) {
        Optional<FoodItem> freshMeal = getFreshMeal(preference);
        freshMeal.ifPresent(buffet::removeOne);
        return freshMeal.isPresent();
    }

    @Override
    public int collectWaste(List<FoodItem> wastedMeals, int cycleNumber) {
        return wastedMeals.stream()
                .mapToInt(meal -> meal.getType().getCost())
                .sum();
    }

    public void serve(List<Guest> guests) {
        refill(batch, buffet);
        for (Guest guest : guests)
            consumeFreshest(guest.guestType().getMealPreferences());
    }

    private Optional<FoodItem> getFreshMeal(List<MealType> preference) {
        Optional<FoodItem> freshMeal = Optional.empty();
        int i = 0;
        while (i < preference.size() && freshMeal.isEmpty()) {
            freshMeal = buffet.getFreshestMeal(preference.get(i));
            i++;
        }
        return freshMeal;
    }
}


