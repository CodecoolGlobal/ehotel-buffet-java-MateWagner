package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.FoodItem;
import com.codecool.ehotel.model.FoodType;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public interface BuffetService {

     void createBach(MealType mealType, int portion, int timeStamp);

     List<FoodItem> refill(Map<FoodItem, Integer> batch, Buffet buffet);

     boolean consumeFreshest(List<MealType> preference);

     int collectWaste(List<FoodItem> meals, int cycleNumber);
}
