package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Meal;
import com.codecool.ehotel.model.MealType;

import java.util.Map;
import java.util.Optional;

public class BreakfastManager implements BuffetService {

    Buffet buffet;
    Map<Meal, Integer> batch;

    public BreakfastManager(Buffet buffet) {
        this.buffet = buffet;
    }

    @Override
    public void createBach(MealType mealType, int portion, int timeStamp) {

        batch.put(new Meal(0, mealType), portion);
    }

    public void refill(Map<Meal, Integer> batch, Buffet buffet) {

        for (Map.Entry<Meal, Integer> entry : batch.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++)
                buffet.meals().offer(entry.getKey());
        }
        batch.clear();
    }

    @Override
    public boolean consumeFreshest(MealType mealType) {

        Optional<Meal> freshMeal = buffet.getFreshestMeal(mealType);
        freshMeal.ifPresent(meal -> buffet.serveMeal(meal));

        return freshMeal.isPresent();
    }
}


