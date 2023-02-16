package com.codecool.ehotel.model;

public class FoodItem {
    private int ageCycle;
    private  final FoodType type;

    public FoodItem(int ageCycle, FoodType type) {
        this.ageCycle = ageCycle;
        this.type = type;
    }
    public FoodItem(FoodType type) {
        this.ageCycle = 0;
        this.type = type;
    }

    public FoodType getType() {
        return type;
    }

    public int getAgeCycle() {
        return ageCycle;
    }

    public void increaseAge(){
        this.ageCycle++;
    }

    public boolean isItemExpired(){
        return  this.ageCycle >= this.type.getDurability().getAgeCycleLimit() && this.ageCycle != 0;
    }
    public boolean isDalyWaste(){
        return  this.type.getDurability() == MealDurability.SHORT || this.type.getDurability() == MealDurability.MEDIUM;
    }
    @Override
    public String toString(){

        return "age: " + ageCycle + " " + this.type.toString();
    }
}
