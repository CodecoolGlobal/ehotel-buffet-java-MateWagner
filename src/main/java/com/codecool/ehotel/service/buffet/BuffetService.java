package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.FoodItem;
import com.codecool.ehotel.model.MealType;

import java.util.List;
import java.util.Map;

public interface BuffetService {

     void createBatch(MealType mealType, int portion);

     void refill();

     boolean consumeFreshest(List<MealType> preference);

     int collectWaste(List<FoodItem> meals);
}
