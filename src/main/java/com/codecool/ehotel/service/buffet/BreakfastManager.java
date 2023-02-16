package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.*;

import java.util.*;

public class BreakfastManager implements BuffetService {
    Buffet buffet;
    Map<FoodItem, Integer> batch;

   // int cycle = 0;
    public void serve(ArrayList<ArrayList<Guest>> guests) {
        int maximumGuestNumber = guests.stream().mapToInt(ArrayList::size).sum();
        int minimumBatchSizes = (int) (maximumGuestNumber * 0.5);
        //Refill the buffet
        refill();
        //Try to feed the guests
        for (Guest guest : guests)
            if (!consumeFreshest(guest.getGuestType().getMealPreferences()))
                // TODO: Set guest happiness to unhappy
                System.out.println("Guest is unhappy!");  // <- This is just a placeholder!!!
        //Returns the cost of all wasted meals
        //TODO: add the cost to the statistics
        collectWaste(buffet.expiredMeals());

        buffet.increaseAgePairItem();
    }

    public BreakfastManager(Buffet buffet) {
        this.buffet = buffet;
        this.batch = new HashMap<>();
    }

    public void refill() {
        createDummyBatches();
        List<FoodItem> newFoods = new ArrayList<>();
        for (Map.Entry<FoodItem, Integer> entry : batch.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++)
                newFoods.add(entry.getKey());
        }
        buffet.addMany(newFoods);
        batch.clear();
    }

    @Override
    public void createBatch(MealType mealType, int portion) {
        batch.put(new FoodItem(0, mealType), portion);
    }

    @Override
    public boolean consumeFreshest(List<MealType> preference) {
        Optional<FoodItem> freshMeal = getFreshMeal(preference);
        freshMeal.ifPresent(buffet::removeOne);
        return freshMeal.isPresent();
    }

    @Override
    public int collectWaste(List<FoodItem> expiredMeals) {
        int costOfWastedMeals = expiredMeals.stream()
                .mapToInt(meal -> meal.getType().getCost())
                .sum();
        buffet.removeMany(expiredMeals);
        return costOfWastedMeals;
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
    private void createDummyBatches(){
        int minAvailablePotion = 10; //todo Need to move upper
        for (MealType mealType : MealType.values()) {
           createBatch(mealType, minAvailablePotion - buffet.foodItemCount(mealType));
        }
    }
}


