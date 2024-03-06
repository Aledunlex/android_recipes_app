package com.example.foodapp.data.api;

import com.example.foodapp.data.api.model.CategoriesList;
import com.example.foodapp.data.api.model.MealDescriptionsList;
import com.example.foodapp.data.api.model.MealsList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    /**
     * This method retrieves a list of categories of meals from the API.
     *
     * @return an Observable of the list of categories.
     */
    @GET("categories.php")
    Observable<CategoriesList> getCategoriesList();

    /**
     * This method retrieves a list of meals from a specified category from the API.
     *
     * @param category the category of meals to retrieve.
     * @return an Observable of the list of meals.
     */
    @GET("filter.php?")
    Observable<MealsList> getMealsFromCategory(@Query("c") String category);

    /**
     * This method retrieves a description of a meal with a specified ID from the API.
     *
     * @param mealId the ID of the meal to retrieve.
     * @return an Observable of the meal description.
     */
    @GET("lookup.php?")
    Observable<MealDescriptionsList> getMealDescriptionFromId(@Query("i") String mealId);
}

