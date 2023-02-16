package com.codecool.ehotel.model;

public enum MealDurability {
    SHORT(3),
    MEDIUM(8),
    LONG(0);
    private final int ageCycleLimit;
    MealDurability(int ageCycleLimit){
        this.ageCycleLimit = ageCycleLimit;
    };
    public int getAgeCycleLimit(){return ageCycleLimit;};
}
