package com.example.foodapp.ui.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.foodapp.R;
import com.example.foodapp.data.api.model.Category;
import com.example.foodapp.ui.main.viewholder.CategoryViewHolder;

import java.util.List;

/**
 * CategoryAdapter is an Adapter class that is responsible for displaying a list of meal
 * categories in a RecyclerView. It displays each category's name and thumbnail image.
 * The list of meal categories is provided to the CategoryAdapter through the constructor
 * and can be updated using the setMealCategoryList method.
 * The CategoryAdapter also requires a Context object which it uses to inflate the layout
 * for each item in the list.
 */
public class CategoryAdapter extends Adapter<CategoryViewHolder> {

    private List<Category> categories_list;
    private final Context context;

    /**
     * Constructs a new CategoryAdapter with the given list of categories and context.
     *
     * @param categories_list list of categories to display in the RecyclerView
     * @param context the context in which the adapter is used
     */
    public CategoryAdapter(List<Category> categories_list, Context context) {
        this.categories_list = categories_list;
        this.context = context;
    }

    /**
     * Sets the list of categories to display in the RecyclerView and updates the view to reflect
     * the changes.
     *
     * @param categories_list list of categories to display in the RecyclerView
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setMealCategoryList(List<Category> categories_list) {
        this.categories_list = categories_list;
        notifyDataSetChanged();
    }

    /**
     * Creates a new CategoryViewHolder and sets its layout.
     *
     * @param parent the parent view group for the CategoryViewHolder
     * @param viewType the view type for the CategoryViewHolder
     * @return a new CategoryViewHolder with its layout set
     */
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_layout, parent, false);
        return new CategoryViewHolder(view, this.context);
    }

    /**
     * Called by the RecyclerView to display the data at the specified position.
     * This method should update the contents of the itemView to reflect the item at the given
     * position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     * at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = this.getMealCategoriesList().get(position);
        holder.getStrCategoryView()
                .setText(category.getStrCategory());
        updateMealCategoryImage(holder, category);
    }

    /**
     * Updates the image of a given Category.
     * Use of Glide to display an image as provided in "TD 5. Appels réseaux"
     *
     * @param holder The CategoryViewHolder that displays the image.
     * @param category The Category object whose image should be displayed.
     */
    private void updateMealCategoryImage(@NonNull CategoryViewHolder holder, Category category) {
        Glide.with(this.getContext())
                .load(category.getStrCategoryThumb())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.getStrThumbnailCategoryView());
    }

    /**
     * Gets the number of items in the adapter.
     *
     * @return The number of items in the adapter.
     */
    @Override
    public int getItemCount() {
        if (getMealCategoriesList() != null) {
            return getMealCategoriesList().size();
        }
        return 0;
    }

    /**
     * Gets the list of categories being displayed by the adapter.
     *
     * @return The list of categories being displayed by the adapter.
     */
    public List<Category> getMealCategoriesList() {
        return categories_list;
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