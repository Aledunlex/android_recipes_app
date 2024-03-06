package com.example.foodapp.ui.main.viewholder;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.foodapp.R;
import com.example.foodapp.data.api.model.Category;
import com.example.foodapp.data.api.model.Meal;
import com.example.foodapp.ui.main.fragment.HomeFragment;
import com.example.foodapp.ui.main.fragment.MealFromCategoryFragment;

/**
 * ViewHolder for {@link Category} objects. Display each category's name and image.
 * Also hides the favorite_heart image, visible by default in the layout used to display
 * categories and meals. When a Category's card is clicked, its id and name are transmitted to
 * a new Fragment which will display all the {@link Meal} in that Category.
 */
public class CategoryViewHolder extends ViewHolder {

    private TextView strCategoryView;
    private ImageView strThumbnailCategoryView;

    /**
     * Creates a ViewHolder for a single Category
     * @param itemView the View of a Category to be displayed
     * @param context in which the ViewHolder is created, the {@link HomeFragment}
     */
    public CategoryViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        setStrCategoryView((TextView) itemView.findViewById(R.id.strItem));
        setStrThumbnailCategoryView((ImageView) itemView.findViewById(R.id.imageView));

        // Removing the favorite icon from the view of categories
        itemView.findViewById(R.id.favorite_heart).setVisibility(View.INVISIBLE);

        itemView.setOnClickListener(view -> {
            Log.d(this.getClass().getName(), "clicked on category id " + strCategoryView.getText());

            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MealFromCategoryFragment mealFromCategoryFragment = new MealFromCategoryFragment();

            // Adds a bundle to the fragment with the selected Category
            Bundle bundle = new Bundle();
            bundle.putString(context.getString(R.string.clicked_category_id), (String) strCategoryView.getText());
            bundle.putString("title", (String) strCategoryView.getText());
            mealFromCategoryFragment.setArguments(bundle);

            // Replaces current fragment by a new one
            fragmentTransaction.replace(R.id.fragment_container, mealFromCategoryFragment);
            fragmentTransaction.addToBackStack("LIST");
            fragmentTransaction.commit();
        });
    }

    /**
     * Gets the Category's name View to update it
     * @return the category's title view
     */
    public TextView getStrCategoryView() {
        return strCategoryView;
    }

    /**
     * Sets the Category's name View to a given TextView
     * @param strCategoryView the given TextView
     */
    public void setStrCategoryView(TextView strCategoryView) {
        this.strCategoryView = strCategoryView;
    }

    /**
     * Gets the Category's image View to update it
     * @return the category's thumbnail image view
     */
    public ImageView getStrThumbnailCategoryView() {
        return strThumbnailCategoryView;
    }

    /**
     * Sets the Category's image view to a given ImageView
     * @param strThumbnailCategoryView the given ImageView
     */
    public void setStrThumbnailCategoryView(ImageView strThumbnailCategoryView) {
        this.strThumbnailCategoryView = strThumbnailCategoryView;
    }

}
