package com.codecool.ehotel.model;

import java.util.PriorityQueue;
import java.util.Optional;

public record Buffet(PriorityQueue<Meal> meals) {
    public void addManyMeals(PriorityQueue<Meal> meals) {
        this.meals.addAll(meals);
    }

    public void removeManyMeal(PriorityQueue<Meal> meals) {
        this.meals.removeAll(meals);
    }
    public void serveMeal(Meal meal){
        meals.remove(meal);
    }
    public Optional<Meal> getFreshestMeal(MealType mealType){
        return meals.stream().filter(meal -> meal.mealType().equals(mealType))
                 .sorted((o1, o2) -> o1.ageCycle() - o2.ageCycle())
                 .findFirst();
    }

}
