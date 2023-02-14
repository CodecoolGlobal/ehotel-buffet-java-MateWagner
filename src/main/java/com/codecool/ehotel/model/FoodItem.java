package com.codecool.ehotel.model;

public class FoodItem {
    private int ageCycle;
    private  final FoodType type;

    public FoodItem(int ageCycle, FoodType type) {
        this.ageCycle = ageCycle;
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
}
