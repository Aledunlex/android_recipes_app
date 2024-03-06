package com.example.foodapp.data.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This class represents a list of categories of meals as retrieved in TheMealDB API.
 * It includes a list of {@link Category} objects.
 */
public class CategoriesList {

    // The list of categories of meals.
    @SerializedName("categories")
    @Expose
    private List<Category> categories;

    /**
     * Returns the list of categories of meals.
     *
     * @return the list of categories of meals.
     */
    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "CategoriesList{" +
                "categories=" + categories +
                '}';
    }
}