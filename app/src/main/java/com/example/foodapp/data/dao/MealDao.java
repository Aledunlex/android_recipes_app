package com.example.foodapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodapp.data.dao.entity.MealEntity;

import java.util.List;

/**
 * Data Access Object for the MealEntity class, providing database access methods.
 */
@Dao
public interface MealDao {

    /**
     * Inserts a meal into the database.
     * If the meal already exists, the insertion is ignored.
     *
     * @param meal the MealEntity object to insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MealEntity meal);

    /**
     * Deletes all meals from the database.
     */
    @Query("DELETE FROM MealEntity")
    void deleteAll();

    /**
     * Retrieves all meals from the database as a LiveData list.
     *
     * @return a LiveData list of MealEntity objects
     */
    @Query("SELECT * FROM MealEntity")
    LiveData<List<MealEntity>> getAllMeals();

    /**
     * Retrieves a meal from the database by its ID.
     *
     * @param mealId the ID of the meal to retrieve
     * @return the MealEntity object with the specified ID, or null if not found
     */
    @Query("SELECT * FROM MealEntity WHERE idMeal = :mealId")
    LiveData<MealEntity> getMealById(String mealId);

    /**
     * Deletes a meal from the database by its ID.
     *
     * @param mealId the ID of the meal to delete
     */
    @Query("DELETE FROM MealEntity WHERE idMeal = :mealId")
    void delete(String mealId);
}