package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.*;

import java.util.*;

public class BreakfastManager implements BuffetService {
    Buffet buffet;
    Map<FoodType, Integer> batch;

    public void serve(List<List<Guest>> guestsCycles) {
        int maximumGuestNumber = guestsCycles.stream().mapToInt(List::size).sum();
        int minAvailablePotion = (int) (maximumGuestNumber * 0.5);

        // serving over the 8 cycle
        for (List<Guest> guests : guestsCycles) {
            //Refill the buffet
            //createDummyBatches(minAvailablePotion);
            createOptimalBatches(guestsCycles);
            refill();

            //Try to feed the guests
            for (Guest guest : guests) {
                if (!consumeFreshest(guest.getGuestType().getMealPreferences()))
                    // TODO: Set guest happiness to unhappy
                    System.out.println("Guest is unhappy!");  // <- This is just a placeholder!!!
            }

            //Returns the cost of all wasted meals
            //TODO: add the cost to the statistics
            collectWaste(buffet.expiredMeals());

            buffet.increaseAgePairItem();
        }
            collectWaste(buffet.dalyCleanUp());
    }

    public BreakfastManager(Buffet buffet) {
        this.buffet = buffet;
        this.batch = new HashMap<>();
    }

    public void refill() {
        List<FoodItem> newFoods = new ArrayList<>();
        for (Map.Entry<FoodType, Integer> entry : batch.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++)
                newFoods.add(new FoodItem(entry.getKey()));
        }
        buffet.addMany(newFoods);
        batch.clear();
    }

    @Override
    public void createBatch(MealType mealType, int portion) {
        batch.put(mealType, portion);
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

    private void createDummyBatches(int minAvailablePotion) {
        for (MealType mealType : MealType.values()) {
            createBatch(mealType, minAvailablePotion - buffet.foodItemCount(mealType));
        }
    }

    private void createOptimalBatches(List<List<Guest>> guestsAtDay) {
        Random random = new Random();
        for (List<Guest> guestsAtCycle : guestsAtDay) {
            for (Guest guest : guestsAtCycle) {
                List<MealType> mealPreferences = guest.getGuestType().getMealPreferences();
                createBatch(mealPreferences.get(random.nextInt(mealPreferences.size())), 1);
            }
        }
    }
}
