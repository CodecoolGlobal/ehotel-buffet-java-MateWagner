package com.codecool.ehotel.service;

import com.codecool.ehotel.model.Guest;

import java.util.List;

public class Statistic {
    private int dailyWasteCost;
    private int dailyUnhappyGuestAmount;

    public void collectUnHappyGuestAmount(List<Guest> guests){
        setDailyUnhappyGuestAmount(guests.stream().filter(guest -> !guest.isHappy()).toList().size());
    }

    public void collectCostOfWastedFoodPerCycle(int collectWaste){
         setDailyWasteCost(getDailyWasteCost() + collectWaste);
    }
    public void clearDailyStatistics(){
        setDailyWasteCost(0);
        setDailyUnhappyGuestAmount(0);
    }
    public int getDailyWasteCost() {
        return dailyWasteCost;
    }

    public int getDailyUnhappyGuestAmount() {
        return dailyUnhappyGuestAmount;
    }

    private void setDailyWasteCost(int dailyWasteCost) {
        this.dailyWasteCost = dailyWasteCost;
    }

    private void setDailyUnhappyGuestAmount(int dailyUnhappyGuestAmount) {
        this.dailyUnhappyGuestAmount = dailyUnhappyGuestAmount;
    }
    public void displayDailyStatistics(String CURRENCY){
        // TODO: replace unhappy guest amount with unhappy / all

        System.out.printf("%30s : %-3d%1s", "Unhappy guest rate: ", getDailyUnhappyGuestAmount(), "%");
        System.out.printf("%30s : %-20d%10s", "Daily waste cost: ", getDailyWasteCost(), CURRENCY);
    }
}
