package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

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
    public boolean consumeFreshest(List<MealType> preference) {
        Optional<Meal> freshMeal = null;
        int i = 0;
        while(i < preference.size() && !freshMeal.isPresent()){
            freshMeal = buffet.getFreshestMeal(preference.get(i));
            freshMeal.ifPresent(meal -> buffet.serveMeal(meal));
            i++;
        }
        return freshMeal.isPresent();
    }

    @Override
    public int collectWaste(PriorityQueue<Meal> meals, int cycleNumber) {
        int costOfWastedMeals = 0;
        for (Meal meal : meals) {
            if (cycleNumber >= 8) {
                if (meal.mealType().getDurability().equals(MealDurability.SHORT) || meal.mealType().getDurability().equals(MealDurability.MEDIUM)) {
                    meals.remove(meal);
                    costOfWastedMeals += meal.mealType().getCost();
                }
            }
            if (meal.ageCycle() > meal.mealType().getDurability().getAgeCycleLimit())
                meals.remove(meal);
            costOfWastedMeals += meal.mealType().getCost();

        }
        return costOfWastedMeals;
    }

    public void serve(List<Guest> guests) {

        refill(batch, buffet);
        for (Guest guest : guests) {

            consumeFreshest(guest.guestType().getMealPreferences());
        }
    }
}


