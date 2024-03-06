package com.example.foodapp.ui.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.foodapp.R;
import com.example.foodapp.data.api.model.Meal;
import com.example.foodapp.data.dao.entity.MealEntity;
import com.example.foodapp.data.dao.repository.FavoritesRepository;
import com.example.foodapp.ui.main.viewholder.MealViewHolder;

import java.util.List;

/**
 * This class is the RecyclerView Adapter for the meals list displayed in the HomeFragment
 * and MealFromCategoryFragment. It uses the ViewHolder design pattern to optimize the performance
 * of the RecyclerView by only inflating and binding views when necessary.
 */
public class MealAdapter extends Adapter<MealViewHolder> {

    private List<Meal> mealsList;
    private final Context context;
    private final String categoryId;

    /**
     * Constructor for the MealAdapter class.
     *
     * @param mealsList list of meals to be displayed in the RecyclerView
     * @param categoryId ID of the category to which the meals belong
     * @param context context of the application
     */
    public MealAdapter(List<Meal> mealsList, String categoryId, Context context) {
        this.mealsList = mealsList;
        this.context = context;
        this.categoryId = categoryId;
    }

    /**
     * Method to update the list of meals displayed in the RecyclerView.
     *
     * @param mealsList new list of meals to be displayed
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setMealList(List<Meal> mealsList) {
        this.mealsList = mealsList;
        notifyDataSetChanged();
    }

    /**
     * Method to create a new ViewHolder and inflate the item_card_layout layout file.
     *
     * @param parent parent view group
     * @param viewType type of view
     * @return a new MealViewHolder instance
     */
    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_layout, parent, false);
        return new MealViewHolder(view, this.context);
    }

    /**
     * Called by the RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link MealViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = this.getMealsList().get(position);

        holder.getStrMealView()
                .setText(meal.getStrMeal());
        updateMealImage(holder, meal);
        holder.setMealId(meal.getIdMeal());
        holder.setCategoryId(this.categoryId);

        FavoritesRepository favoritesRepository = FavoritesRepository.getInstance(null);
        // Creates an observer for each ViewHolder (each Meal), to verify if a Meal's mealId is
        // in the FavoriteDB or not, and allow adding/deleting it from it through a button
        favoritesRepository.getMeal(meal.getIdMeal()).observe((LifecycleOwner) context, mealEntity -> {
            // Initializes the color of the fav icon for that meal
            if (mealEntity != null) {  // Which means if the meal's id is present in the MealDao
                holder.getHeartIcon().setBackgroundResource(R.drawable.heart_icon);
            } else {
                holder.getHeartIcon().setBackgroundResource(R.drawable.heart_icon_selector);
            }

            // Initializes the fav button for that meal to toggle adding/deleting it of the DB
            holder.getHeartIcon().setOnClickListener(v -> {
                if (mealEntity != null) {
                    // MealEntity is in the database, so delete it
                    favoritesRepository.delete(mealEntity);
                    holder.getHeartIcon().setBackgroundResource(R.drawable.heart_icon_selector);
                    Toast.makeText(context, String.format(
                            "Removed %s from favorites.", mealEntity.getStrMeal()
                    ), Toast.LENGTH_SHORT).show();
                } else {
                    // MealEntity is not in the database, so insert it
                    favoritesRepository.insert(new MealEntity(
                            meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb(), categoryId));
                    holder.getHeartIcon().setBackgroundResource(R.drawable.heart_icon);
                    Toast.makeText(context, String.format(
                            "Added %s to favorites!", meal.getStrMeal()
                    ), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    /**
     * Updates the image displayed in the view holder to be the image of the given meal.
     * Use of Glide to display an image as provided in "TD 5. Appels réseaux"
     *
     * @param holder The view holder to update the image of.
     * @param meal The meal whose image to display.
     */
    private void updateMealImage(@NonNull MealViewHolder holder, Meal meal) {
        Glide.with(this.getContext())
                .load(meal.getStrMealThumb())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.getStrThumbnailMealView());
    }

    /**
     * Gets the number of items in the adapter.
     *
     * @return The number of items in the adapter.
     */
    @Override
    public int getItemCount() {
        if (getMealsList() != null) {
            return getMealsList().size();
        }
        return 0;
    }

    /**
     * Gets the list of meals being displayed by the adapter.
     *
     * @return The list of meals being displayed by the adapter.
     */
    public List<Meal> getMealsList() {
        return mealsList;
    }

    /**
     * Gets the context in which the adapter is being used.
     *
     * @return The context in which the adapter is being used.
     */
    public Context getContext() {
        return context;
    }
}