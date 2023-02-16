package com.codecool.ehotel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BuffetTest {
    List<FoodItem> items;
    Buffet buffet = new Buffet(new ArrayList<>());

    @BeforeEach
    void initList() {
        items = new ArrayList<>();
        items.add(new FoodItem(MealType.SCRAMBLED_EGGS));
        items.add(new FoodItem(1, MealType.SCRAMBLED_EGGS));
        items.add(new FoodItem(2, MealType.SCRAMBLED_EGGS));
        items.add(new FoodItem(3, MealType.SCRAMBLED_EGGS));
        items.add(new FoodItem(7, MealType.BUN));
    }

    @Test
    void addMany() {
        buffet.addMany(items);
        assertEquals(5, buffet.foodItems().size());
    }

    @Test
    void removeMany(){
        buffet = new Buffet(items);
        List<FoodItem> things = List.of(items.get(0), items.get(1));
        buffet.removeMany(things);
        assertEquals(3, buffet.foodItems().size());
    }

    @Test
    void removeOne() {
        buffet = new Buffet(items);
        buffet.removeOne(items.get(1));
        assertEquals(4, buffet.foodItems().size());
    }

    @Test
    void getFreshestMeal() {
        buffet = new Buffet(items);
        Optional<FoodItem> item = buffet.getFreshestMeal(MealType.SCRAMBLED_EGGS);
        Optional<FoodItem> item2 = buffet.getFreshestMeal(MealType.BUN);
        assertEquals(0, item.get().getAgeCycle());
        assertEquals(7, item2.get().getAgeCycle());
    }

    @Test
    void collectWaste() {
        buffet = new Buffet(items);
        List<FoodItem> waste = buffet.expiredMeals();
        assertEquals(1, waste.size());
    }

    @Test
    void increaseAgePairItem() {
        buffet = new Buffet(items);
        buffet.increaseAgePairItem();
        List<FoodItem> waste = buffet.expiredMeals();
        assertEquals(3, waste.size());
    }
    @Test
    void dalyCleanUp() {
        buffet = new Buffet(items);
        List<FoodItem> waste = buffet.dalyCleanUp();
        assertEquals(5, waste.size());
    }
}