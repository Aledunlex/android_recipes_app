package com.example.foodapp.data.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * A Room database entity class representing a favorite meal.
 * Contains fields for the meal ID, name, thumbnail image URL, and meal category ID.
 */
@Entity
public class MealEntity {

    // The primary key field, a unique identifier for the meal.
    @PrimaryKey
    @NonNull
    private String idMeal;

    // The name of the meal.
    @NonNull
    private String strMeal;

    // The URL of the thumbnail image of the meal.
    @NonNull
    private String strMealThumb;

    // The ID of the meal's category.
    private String idMealCategory;

    /**
     * Constructs a MealEntity object with the given field values.
     *
     * @param idMeal the meal ID
     * @param strMeal the meal name
     * @param strMealThumb the URL of the thumbnail image
     * @param idMealCategory the meal category ID
     */
    public MealEntity(@NonNull String idMeal, @NonNull String strMeal, @NonNull String strMealThumb, String idMealCategory) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMealCategory = idMealCategory;
    }

    /**
     * Returns the meal name.
     *
     * @return the meal name
     */
    @NonNull
    public String getStrMeal() {
        return strMeal;
    }

    /**
     * Sets the meal name.
     *
     * @param strMeal the meal name to set
     */
    public void setStrMeal(@NonNull String strMeal) {
        this.strMeal = strMeal;
    }

    /**
     * Returns the meal id.
     *
     * @return the meal id
     */
    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    /**
     * Sets the meal id.
     *
     * @param idMeal the meal id to set
     */
    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    /**
     * Returns the thumbnail image URL.
     *
     * @return the thumbnail image URL
     */
    @NonNull
    public String getStrMealThumb() {
        return strMealThumb;
    }

    /**
     * Sets the thumbnail image URL.
     *
     * @param strMealThumb the thumbnail image URL to set
     */
    public void setStrMealThumb(@NonNull String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    /**
     * Returns the category id.
     *
     * @return the category id
     */
    public String getIdMealCategory() {
        return idMealCategory;
    }

    /**
     * Sets the category id.
     *
     * @param idMealCategory the category id to set
     */
    public void setIdMealCategory(String idMealCategory) {
        this.idMealCategory = idMealCategory;
    }

    /**
     * Returns a string representation of this MealEntity object.
     *
     * @return a string in the form "Meal{strMeal='meal name'}"
     */
    @NonNull
    @Override
    public String toString() {
        return "Meal{" +
                "strMeal='" + strMeal + '\'' +
                '}';
    }
}