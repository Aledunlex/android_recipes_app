package com.example.foodapp.ui.main.viewholder;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.foodapp.R;
import com.example.foodapp.data.api.model.Meal;
import com.example.foodapp.data.api.model.MealDescription;
import com.example.foodapp.ui.main.fragment.FavoritesFragment;
import com.example.foodapp.ui.main.fragment.MealDescriptionFragment;
import com.example.foodapp.ui.main.fragment.MealFromCategoryFragment;

/**
 * ViewHolder for {@link Meal} objects in the FavoritesFragment. Display each meal's name and image.
 * Also has a clickable favorite_heart image, which when clicked, adds or delete the meal from
 * the FavoritesRepository. When a Meal's card is clicked, its id and name are transmitted to
 * a new Fragment which will display the {@link MealDescription} of that Meal.
 */
public class MealViewHolder extends ViewHolder {

    private TextView strMealView;
    private ImageView strThumbnailMealView;
    private String mealId;
    private String categoryId;
    private Button heartIcon;

    /**
     * Creates a ViewHolder for a single Meal
     * @param itemView the View of a Meal to be displayed
     * @param context in which the ViewHolder is created, the {@link MealFromCategoryFragment}
     */
    public MealViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        heartIcon = itemView.findViewById(R.id.favorite_heart);

        setStrMealView((TextView) itemView.findViewById(R.id.strItem));
        setStrThumbnailMealView((ImageView) itemView.findViewById(R.id.imageView));

        itemView.setOnClickListener(view -> {
            Log.d(this.getClass().getName(), "clicked on meal id " + mealId);

            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MealDescriptionFragment mealDescriptionFragment = new MealDescriptionFragment();

            // Adds a bundle to the fragment with the current category and the clicked meal
            Bundle bundle = new Bundle();
            bundle.putString(context.getString(R.string.clicked_meal_id), mealId);
            bundle.putString(context.getString(R.string.clicked_category_id), categoryId);
            mealDescriptionFragment.setArguments(bundle);

            // Replaces current fragment by a new one
            fragmentTransaction.replace(R.id.fragment_container, mealDescriptionFragment);
            fragmentTransaction.addToBackStack("MEAL");
            fragmentTransaction.commit();
        });

    }

    /**
     * Gets the meal's name View to update it
     * @return the meal's title view
     */
    public TextView getStrMealView() {
        return strMealView;
    }

    /**
     * Sets the meal's name View to a given TextView
     * @param strMealView the given TextView
     */
    public void setStrMealView(TextView strMealView) {
        this.strMealView = strMealView;
    }

    /**
     * Gets the meal's image View to update it
     * @return the category's thumbnail image view
     */
    public ImageView getStrThumbnailMealView() {
        return strThumbnailMealView;
    }

    /**
     * Sets the meals's name View to a given TextView
     * @param strThumbnailMealView the given TextView
     */
    public void setStrThumbnailMealView(ImageView strThumbnailMealView) {
        this.strThumbnailMealView = strThumbnailMealView;
    }

    /**
     * Sets the meal's id
     * @param mealId the id to be used
     */
    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    /**
     * Sets the meal's category's id
     * @param categoryId the id to be used
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Gets the meal's favorite button
     * @return the meal's favorite button
     */
    public Button getHeartIcon() {
        return this.heartIcon;
    }
}

