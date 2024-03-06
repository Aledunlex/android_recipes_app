package com.example.foodapp.data.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A class that represents a list of meal descriptions as retrieved in TheMealDB API. It will
 * likely contain a single {@link MealDescription}.
 */
public class MealDescriptionsList {

    // A list of meal descriptions
    @SerializedName("meals")
    @Expose
    private List<MealDescription> meals = null;

    /**
     * Gets the list of meal descriptions.
     *
     * @return the list of meal descriptions.
     */
    public List<MealDescription> getMealDescription() {
        return meals;
    }

    /**
     * Sets the list of meal descriptions.
     *
     * @param meals the list of meal descriptions to set.
     */
    public void setMeals(List<MealDescription> meals) {
        this.meals = meals;
    }
}