package com.example.foodapp.data.dao.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.foodapp.data.dao.FavoriteDatabase;
import com.example.foodapp.data.dao.MealDao;
import com.example.foodapp.data.dao.entity.MealEntity;

import java.util.List;
import java.util.Objects;

/**
 * A repository for accessing favorite meals in the database.
 * Provides methods for inserting, deleting, and retrieving meals.
 */
public class FavoritesRepository {

    // A Data Access Object for the MealEntity class.
    private final MealDao mealDao;

    // A LiveData list of all favorite meals in the database.
    private final LiveData<List<MealEntity>> allMeals;

    // A Data Access Object for the MealEntity class.
    private static FavoritesRepository instance;

    /**
     * Returns the singleton instance of the repository.
     * Creates the instance if it does not exist.
     *
     * @param application the application in which the repository will be used
     * @return the singleton instance of the repository
     */
    public static FavoritesRepository getInstance(Application application) {
        if (instance == null) {
            Log.d("Repo", "Initializing repository");
            instance = new FavoritesRepository(application);
        }
        else {
            Log.d("Repo", "Retrieving initialized repository");
        }
        return instance;
    }

    /**
     * Creates a new repository instance.
     *
     * @param application the application in which the repository will be used
     */
    private FavoritesRepository(Application application) {
        FavoriteDatabase mdb = FavoriteDatabase.getDatabase(application);
        mealDao = mdb.mealDao();
        allMeals = mealDao.getAllMeals();
    }

    /**
     * Currently only required to update whether a meal is bookmarked or not
     * when it's being added (on a meal description view)
     * @param owner the Activity or Fragment in which the meals are being displayed
     * @param observer the observer that will perform an activity based on the meals
     */
    public void observeMealEntity(LifecycleOwner owner, Observer<List<MealEntity>> observer) {
        allMeals.observe(owner, observer);
    }

    /**
     * Inserts a meal into the database in a background thread.
     *
     * @param meal the MealEntity object to insert
     */
    public void insert(MealEntity meal) {
        FavoriteDatabase.databaseWriteExecutor.execute(() -> mealDao.insert(meal));
        Log.d("Repo", "Inserting " + meal.getStrMeal());
    }

    /**
     * Deletes a meal from the database in a background thread.
     *
     * @param meal the MealEntity object to delete
     */
    public void delete(MealEntity meal) {
        FavoriteDatabase.databaseWriteExecutor.execute(() -> mealDao.delete(meal.getIdMeal()));
        Log.d("Repo", "Deleting " + meal.getStrMeal());
    }

    /**
     * Returns a LiveData list of all meals in the database.
     *
     * @return a LiveData list of MealEntity objects
     */
    public LiveData<List<MealEntity>> getAllMeals() {
        return allMeals;
    }

    /**
     * Returns in a LiveData the MealEntity of corresponding mealId value
     * @param mealId of a Meal or MealDescription to be used to retrieve the MealEntity in the DB
     * @return the MealEntity in the DB corresponding to a Meal or MealDescription
     */
    public LiveData<MealEntity> getMeal(String mealId) {
        return mealDao.getMealById(mealId); }

    /**
     * Checks in the local database if the meal ID passed as parameter
     * corresponds to a meal that is in the MealDao
     * @param mealId the id of a meal displayed on some page
     * @return true if the meal is in local favorites>, false otherwise
     */
    public boolean isFavoriteMeal(String mealId) {
        boolean result = false;
        Log.d("Repo",String.valueOf(getAllMeals().getValue()));
        for (MealEntity m : getAllMeals().getValue()) {
            if (Objects.equals(m.getIdMeal(), mealId)) {
                result = true;
                break;
            }
        }
        Log.d("Repo", String.format("Meal id %s is %sin the repo", mealId, result ? "" : "NOT "));
        return result;
    }

}

