package com.codecool.ehotel.model;

import java.time.LocalDate;
import java.util.ArrayList;

public record GuestsAtDay(LocalDate date, ArrayList<ArrayList<Guest>> guestsAtDay) {
}
