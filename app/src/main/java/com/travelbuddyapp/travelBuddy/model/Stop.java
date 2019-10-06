package com.travelbuddyapp.travelBuddy.model;

import androidx.room.Entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * eg a City or a national park
 * @author Marvin
 *
 */
@Entity
public class Stop {

    private String name;
    private String notes;
    private int days; //days of stay
    private int stars; //stars 1-5 for planning

    public Stop(String name) {
        this.name = name;
    }

    public String generateMessage(String[] salutations, String[] arrivals, String[] closings) {
        ArrayList<String[]> all = new ArrayList<>();
        all.add(salutations);
        all.add(arrivals);
        all.add(closings);

        String retStr = "";

        for (String[] array : all) {
            Random rand = new Random();
            int n = rand.nextInt(array.length - 1);
            retStr += array[n] + " ";
        }

        return retStr.replace("$stop",this.name);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the days of stay
     */
    public int getDays() {
        return days;
    }

    /**
     * @param days the days of stay to set
     */
    public void setDays(int days) {
        this.days = days;
    }

    /**
     * @return the rating stars (1-5) for planning
     */
    public int getStars() {
        return stars;
    }

    /**
     * @param stars the stars to set for planning (1-5)
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
