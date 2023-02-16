package com.codecool.ehotel.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record GuestsAtDay(LocalDate date, List<List<Guest>> guestsAtDay) {
}
