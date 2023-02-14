package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
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
    public void createBach(MealType type, int portion, int timeStamp) {
        batch.put(Meal, portion);
    }

    @Override
    public void refill(Map<Meal, Integer> batch, Buffet buffet) {

           /*  for (Map<Meal, Integer> entry : batch){
            for (int i = 0; i < entry.value(); i++)
                 Buffet.content.offer(entry.getKey());
         }
    */
        batch.clear();
    }

    @Override
    public boolean consumeFreshest(MealType mealType) {
       Optional<Meal> freshMeal = buffet.getFreshestMeal(mealType).is;
       if(freshMeal.isPresent())
           buffet.serveMeal(freshMeal.get());

        return freshMeal.ifPresent(buffet.content);

    }
}


