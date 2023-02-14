package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public interface BuffetService {

     Map<Meal, Integer> createBach(MealType type, int portion,  int timeStamp);

     void refill(Map<Meal, Integer> batch, Buffet buffet);

     boolean consumeFreshest(MealType freshMeal);


}
