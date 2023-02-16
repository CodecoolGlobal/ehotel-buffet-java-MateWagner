package com.codecool.ehotel.data;

import java.util.Random;

public class Names {
    private String[] givenNames = new String[] {
            "Jacob", "Emma", "Michael", "Olivia", "Ethan", "Sophia", "William", "Isabella", "Alexander", "Charlotte",
            "Daniel", "Amelia", "Matthew", "Mia", "Elijah", "Harper", "Joshua", "Evelyn", "James", "Abigail"
    };
    private String[] lastNames = new String[] {
            "Smith", "Johnson", "Williams", "Brown", "Garcia", "Martinez", "Davis", "Rodriguez", "Miller", "Martin",
            "Anderson", "Taylor", "Thomas", "Jackson", "White", "Harris", "Martin", "Hall", "Adams", "King"
    };

    public String getRandomName() {
        Random random = new Random();
        return givenNames[random.nextInt(givenNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
    }


}
