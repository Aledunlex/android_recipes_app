package com.example.foodapp.data.api.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class representing a category of meals as retrieved in TheMealDB API.
 */
public class Category {

    // The ID of the category.
    @SerializedName("idCategory")
    @Expose
    private String idCategory;

    // The name of the category.
    @SerializedName("strCategory")
    @Expose
    private String strCategory;

    // The URL of an image representing the category.
    @SerializedName("strCategoryThumb")
    @Expose
    private String strCategoryThumb;

    // A description of the category.
    @SerializedName("strCategoryDescription")
    @Expose
    private String strCategoryDescription;

    /**
     * Returns the ID of the category.
     *
     * @return the ID of the category.
     */
    public String getIdCategory() {
        return idCategory;
    }

    /**
     * Sets the ID of the category.
     *
     * @param idCategory the ID of the category.
     */
    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    /**
     * Returns the name of the category.
     *
     * @return the name of the category.
     */
    public String getStrCategory() {
        return strCategory;
    }

    /**
     * Sets the category of the meal.
     *
     * @param strCategory the category of the meal.
     */
    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    /**
     * Returns the thumbnail image of the meal's category.
     *
     * @return the thumbnail image of the meal's category.
     */
    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    /**
     * Sets the thumbnail image of the meal's category.
     *
     * @param strCategoryThumb the thumbnail image of the meal's category.
     */
    public void setStrCategoryThumb(String strCategoryThumb) {
        this.strCategoryThumb = strCategoryThumb;
    }

    /**
     * Returns the description of the meal's category.
     *
     * @return the description of the meal's category.
     */
    public String getStrCategoryDescription() {
        return strCategoryDescription;
    }

    /**
     * Sets the description of the meal's category.
     *
     * @param strCategoryDescription the description of the meal's category.
     */
    public void setStrCategoryDescription(String strCategoryDescription) {
        this.strCategoryDescription = strCategoryDescription;
    }

    @NonNull
    @Override
    public String toString() {
        return "MealCategory{" +
                "idCategory='" + idCategory + '\'' +
                ", strCategory='" + strCategory + '\'' +
                ", strCategoryThumb='" + strCategoryThumb + '\'' +
                ", strCategoryDescription='" + strCategoryDescription + '\'' +
                '}';
    }
}