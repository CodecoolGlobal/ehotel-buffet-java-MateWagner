package com.codecool.ehotel.service;

import com.codecool.ehotel.model.Guest;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class Statistic {
    private int dailyWasteCost;
    private int dailyUnhappyGuestAmount;
    private int dailyGuestAmount;
    private int seasonalGuestAmount;
    private int seasonallyWasteCost;
    private int seasonallyUnhappyGuestAmount;

 /*   public void collectUnHappyGuestAmount(List<Guest> guests){
        setDailyUnhappyGuestAmount(guests.stream().filter(guest -> !guest.isHappy()).toList().size());
    }*/

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

    public int getSeasonallyWasteCost() {
        return seasonallyWasteCost;
    }

    public int getSeasonallyUnhappyGuestAmount() {
        return seasonallyUnhappyGuestAmount;
    }

    public int getDailyGuestAmount() {
        return dailyGuestAmount;
    }

    public int getSeasonalGuestAmount() {
        return seasonalGuestAmount;
    }

    public void setDailyWasteCost(int dailyWasteCost) {
        this.dailyWasteCost = dailyWasteCost;
    }

    public void setDailyUnhappyGuestAmount(int dailyUnhappyGuestAmount) {
        this.dailyUnhappyGuestAmount = dailyUnhappyGuestAmount;
    }

    public void collectUnHappyGuestAmount(List<List<Guest>> allGuestsPerDay) {
        int dailyUnhappyGuests = 0;
        for (List<Guest> guestList : allGuestsPerDay){
            dailyUnhappyGuests += guestList.stream()
                    .filter(guest -> !guest.isHappy())
                    .toList()
                    .size();
        }
       setDailyUnhappyGuestAmount(dailyUnhappyGuests);
    }

    public void setSeasonallyWasteCost(int seasonallyWasteCost) {
        this.seasonallyWasteCost = seasonallyWasteCost;
    }



    public void setDailyGuestAmount(int dailyGuestAmount) {
        this.dailyGuestAmount = dailyGuestAmount;
    }

    private void setSeasonalGuestAmount(int seasonalGuestAmount) {
        this.seasonalGuestAmount = seasonalGuestAmount;
    }

    public void setSeasonallyUnhappyGuestAmount(int seasonallyUnhappyGuestAmount) {
        this.seasonallyUnhappyGuestAmount = seasonallyUnhappyGuestAmount;
    }

    private void increaseSeasonalGuestAmount(){
        setSeasonalGuestAmount(seasonalGuestAmount + dailyGuestAmount);
    }
    private void increaseSeasonalWasteCost(){
        setSeasonallyWasteCost(seasonallyWasteCost + dailyWasteCost);
    }
    private void increaseSeasonalUnhappyGuests(){
        setSeasonallyUnhappyGuestAmount(seasonallyUnhappyGuestAmount + dailyUnhappyGuestAmount);
    }

    public void gatherStatistics(int dailyGuests){
        setDailyGuestAmount(dailyGuests);
        increaseSeasonalUnhappyGuests();
        increaseSeasonalWasteCost();
        increaseSeasonalGuestAmount();
    }
public void displayStatistics(LocalDate date, String period, String CURRENCY, int unhappyGuestAmount, int totalGuestAmount, int wasteCost){
    DecimalFormat df = new DecimalFormat("#.##");
    double rate = ((double) unhappyGuestAmount /(double) totalGuestAmount) * 100;

    System.out.println("------------------------------------------------");
    System.out.println("Date : " + date);
    System.out.printf("%-30s : %-6s%-1s", period + " unhappy guest rate",df.format(rate) , " %");
    System.out.printf("\n%-30s : %-6d %-6s\n", period + " waste cost", wasteCost, CURRENCY);
}
// SET DAILY GUEST AMOUNT
}
