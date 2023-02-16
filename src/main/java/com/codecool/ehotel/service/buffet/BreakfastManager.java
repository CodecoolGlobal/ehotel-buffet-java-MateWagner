package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.Statistic;

import java.util.*;
import java.util.stream.IntStream;

public class BreakfastManager implements BuffetService {
    Buffet buffet;
    Statistic statistic;
    Map<FoodItem, Integer> batch;

    public BreakfastManager(Buffet buffet, Statistic statistic) {
        this.buffet = buffet;
        this.statistic = statistic;
        this.batch = new HashMap<>();
    }

    public void serve(List<List<Guest>> guests) {
        int maximumGuestNumber = guests.stream().mapToInt(ArrayList::size).sum();
        int minimumBatchSizes = (int) (maximumGuestNumber * 0.5);
        //Refill the buffet
        refill();
        //Try to feed the guests
        for (Guest guest : guests)
            if (!consumeFreshest(guest.getGuestType().getMealPreferences()))
                // TODO: Set guest happiness to unhappy
                System.out.println("Guest is unhappy!");  // <- This is just a placeholder!!!        //Check the guests list's guest happiness and collect the amount of unhappy ones
        statistic.collectUnHappyGuestAmount(guests);
        //Store in the statistic the cost of all wasted meals
        statistic.collectCostOfWastedFoodPerCycle(collectWaste(buffet.expiredMeals()));

        buffet.increaseAgePairItem();
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


    public Optional<FoodItem> getFreshMeal(List<MealType> preference) {
        return preference.stream()
                .map(buffet::getFreshestMeal)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparingInt(FoodItem::getAgeCycle));
    }
    private void createDummyBatches(){
        int minAvailablePotion = 10; //todo Need to move upper
        for (MealType mealType : MealType.values()) {
           createBatch(mealType, minAvailablePotion - buffet.foodItemCount(mealType));
        }
    }
}


