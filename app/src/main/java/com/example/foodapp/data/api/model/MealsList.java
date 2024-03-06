package com.example.foodapp.data.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This class represents a list of meals as retrieved in TheMealDB API.
 * It contains a list of Meal objects.
 */
public class MealsList {

    // The list of meals.
    @SerializedName("meals")
    @Expose
    private List<Meal> meals;

    /**
     * Returns the list of meals.
     *
     * @return the list of meals.
     */
    public List<Meal> getMeals() {
        return meals;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "MealsList{" +
                "meals=" + meals +
                '}';
    }
}