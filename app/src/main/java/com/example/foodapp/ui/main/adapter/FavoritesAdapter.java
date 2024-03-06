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
import com.example.foodapp.data.dao.entity.MealEntity;
import com.example.foodapp.data.dao.repository.FavoritesRepository;
import com.example.foodapp.ui.main.viewholder.FavoritesViewHolder;

import java.util.List;

/**
 * The FavoritesAdapter class is responsible for providing views that represent the data
 * in the favoritesList to the RecyclerView. It creates view holders and binds them to their data.
 */
public class FavoritesAdapter extends Adapter<FavoritesViewHolder> {

    private List<MealEntity> favoritesList;
    private final Context context;

    /**
     * Constructor for the FavoritesAdapter.
     *
     * @param favoritesList the list of favorite meals to be displayed
     * @param context the context in which the adapter is being used
     */
    public FavoritesAdapter(List<MealEntity> favoritesList, Context context) {
        this.favoritesList = favoritesList;
        this.context = context;
    }

    /**
     * Sets the list of favorite meals to be displayed in the RecyclerView.
     *
     * @param favoritesList the new list of favorite meals
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setMealList(List<MealEntity> favoritesList) {
        this.favoritesList = favoritesList;
        notifyDataSetChanged();
    }

    /**
     * Creates a new ViewHolder to display a favorite meal.
     *
     * @param parent the parent ViewGroup for the new View
     * @param viewType the type of View to create
     * @return a new FavoritesViewHolder to display the favorite meal
     */
    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_layout, parent, false);
        return new FavoritesViewHolder(view, this.context);
    }

    /**
     * Binds a {@link MealEntity} to a {@link FavoritesViewHolder}. This method is called by the RecyclerView
     * when it needs to display a new item.
     *
     * @param holder The view holder that should be updated with the meal information.
     * @param position The position of the meal in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        // Get the current Meal object
        MealEntity currentMeal = this.getFavoritesList().get(position);
        // Set the text and image for the current item
        holder.getStrMealView()
                .setText(currentMeal.getStrMeal());
        updateMealImage(holder, currentMeal);
        holder.setMealId(currentMeal.getIdMeal());

        FavoritesRepository favoritesRepository = FavoritesRepository.getInstance(null);
        // Creates an observer for each ViewHolder (each Meal), to verify if a Meal's mealId is
        // in the FavoriteDB or not, and allow adding/deleting it from it through a button
        favoritesRepository.getMeal(currentMeal.getIdMeal()).observe((LifecycleOwner) context, mealEntity -> {
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
                }
                // No 'else': if the meal gets deleted, it will be removed from the list
            });
        });
    }

    /**
     * Updates the image for a given favorite meal.
     * Use of Glide to display an image as provided in "TD 5. Appels réseaux"
     *
     * @param holder The view holder that should be updated with the meal image.
     * @param meal The meal for which the image should be updated.
     */
    private void updateMealImage(@NonNull FavoritesViewHolder holder, MealEntity meal) {
        Glide.with(this.getContext())
                .load(meal.getStrMealThumb())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.getStrMealThumbView());
    }

    /**
     * Gets the number of items in the adapter.
     *
     * @return The number of items in the adapter.
     */
    @Override
    public int getItemCount() {
        if (getFavoritesList() != null) {
            return getFavoritesList().size();
        }
        return 0;
    }

    /**
     * Gets the list of meals entities being displayed by the adapter.
     *
     * @return The list of meals entities being displayed by the adapter.
     */
    public List<MealEntity> getFavoritesList() {
        return favoritesList;
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
