package com.example.foodapp.data.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodapp.data.dao.entity.MealEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A Room database for storing favorite meals.
 * This class is vastly reusing code as provided in "TD 6. Room"
 */
@Database(entities = {MealEntity.class}, version = 5, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {

    public abstract MealDao mealDao();

    // database name and version go here
    public static final String DB_NAME = "favorites_db";
    // A singleton instance of the database.
    private static volatile FavoriteDatabase INSTANCE;
    // The number of threads used for database write operations.
    private static final int NUMBER_OF_THREADS = 4;
    // An ExecutorService for writing to the database in a background thread.
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Returns the singleton instance of the database.
     * Creates the instance if it does not exist.
     *
     * @param context the context in which the database will be used
     * @return the singleton instance of the database
     */
    public static FavoriteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FavoriteDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}