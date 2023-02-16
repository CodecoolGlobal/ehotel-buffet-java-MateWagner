package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.FoodItem;
import com.codecool.ehotel.model.MealType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class BreakfastManagerTest {
    List<FoodItem> foodItems = new ArrayList<>();
    Buffet buffet = new Buffet(foodItems);

    BreakfastManager manager = new BreakfastManager(buffet);

    @Test
    public void testGetFreshMealReturnsEmptyWhenPreferenceIsEmpty() {
        foodItems.clear();
        foodItems.add(new FoodItem(MealType.CROISSANT));
        List<MealType> preference = new ArrayList<>();
        Optional<FoodItem> result = manager.getFreshMeal(preference);
        assertFalse(result.isPresent());
    }
    @Test
    public void testGetFreshMealReturnsFreshMeal() {
        MealType meal1 = MealType.SCRAMBLED_EGGS;
        MealType meal2 = MealType.CEREAL;
        FoodItem freshItem1 = new FoodItem(1, meal1);
        FoodItem freshItem2 = new FoodItem(0, meal2);
        foodItems.clear();
        foodItems.add(freshItem1);
        foodItems.add(freshItem2);
        foodItems.add(new FoodItem(1, meal1));
        foodItems.add(new FoodItem(2, meal2));
        foodItems.add(new FoodItem(3, meal2));

        List<MealType> preferences = new ArrayList<>();
        preferences.add(meal1);
        preferences.add(meal2);

        Optional<FoodItem> result = manager.getFreshMeal(preferences);
        assertTrue(result.isPresent());
        assertEquals(freshItem2, result.get());
    }
}
