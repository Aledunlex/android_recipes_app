package com.example.foodapp.data.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class representing a meal as retrieved in TheMealDB API.
 */
public class Meal {

    // The name of the meal.
    @SerializedName("strMeal")
    @Expose
    private String strMeal;

    // The URL of the thumbnail image for the meal.
    @SerializedName("strMealThumb")
    @Expose
    private String strMealThumb;

    // The ID of the meal.
    @SerializedName("idMeal")
    @Expose
    private String idMeal;

    /**
     * Gets the name of the meal.
     *
     * @return the name of the meal.
     */
    public String getStrMeal() {
        return strMeal;
    }

    /**
     * Sets the name of the meal.
     *
     * @param strMeal the name of the meal.
     */
    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    /**
     * Gets the URL of the thumbnail image for the meal.
     *
     * @return the URL of the thumbnail image for the meal.
     */
    public String getStrMealThumb() {
        return strMealThumb;
    }

    /**
     * Sets the thumbnail image URL.
     *
     * @param strMealThumb the thumbnail image URL to set
     */
    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    /**
     * Returns the category id.
     *
     * @return the category id
     */
    public String getIdMeal() {
        return idMeal;
    }

    /**
     * Sets the category id.
     *
     * @param idMeal the category id to set
     */
    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "strMeal='" + strMeal + '\'' +
                ", strMealThumb='" + strMealThumb + '\'' +
                ", idMeal='" + idMeal + '\'' +
                '}';
    }
}