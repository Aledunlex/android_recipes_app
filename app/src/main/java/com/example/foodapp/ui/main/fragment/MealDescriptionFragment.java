package com.example.foodapp.ui.main.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;
import com.example.foodapp.data.api.APIClient;
import com.example.foodapp.data.api.APIInterface;
import com.example.foodapp.data.api.model.Meal;
import com.example.foodapp.data.api.model.MealDescription;
import com.example.foodapp.data.api.model.MealDescriptionsList;
import com.example.foodapp.data.api.model.MealsList;
import com.example.foodapp.data.dao.entity.MealEntity;
import com.example.foodapp.data.dao.repository.FavoritesRepository;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Displays the detailed information about a meal.
 * The user can also click a button to add (or remove) the corresponding meal
 * to (or from) its local favorites.
 */
public class MealDescriptionFragment extends Fragment {

    private Fragment context;
    private View view;
    private Button heartIcon;
    private String mealId;
    private APIInterface apiService;
    private FavoritesRepository favoritesRepository;
    private MealEntity mealEntity;
    private String currentCategoryId;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be
     *                  attached to. The fragment should not add the view itself, but this can
     *                  be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        context = this;

        favoritesRepository = FavoritesRepository.getInstance(requireActivity().getApplication());

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.item_description_layout, container, false);

        heartIcon = view.findViewById(R.id.favorite_heart);

        // Retrieving the title of the fragment
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Obtain the meal id from the arguments passed to this fragment
        if (getArguments() != null) {
            mealId = getArguments().getString(getString(R.string.clicked_meal_id));
            currentCategoryId = getArguments().getString(getString(R.string.clicked_category_id));
        }

        // Create the API service
        apiService = APIClient.getClient().create(APIInterface.class);

        // Create the observable to fetch the meal description from the API
        Observable<MealDescriptionsList> mealDescriptionsListObservable = apiService.getMealDescriptionFromId(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);

        // Display the meal description
        handleMealDescription(mealId, mealDescriptionsListObservable);

        return view;
    }

    /**
     * Handles the meal description data from the API and updates the layout with the retrieved data.
     * Also sets up the onClickListener for the bookmark (favorite) icon for this meal.
     *
     * @param meal_id the id of the meal to be displayed
     * @param mealDescriptionsListObservable the observable object to fetch the meal
     *                                       description data from the API
     */
    private void handleMealDescription(
            String meal_id, Observable<MealDescriptionsList> mealDescriptionsListObservable) {

        Disposable disposableObserver = mealDescriptionsListObservable
                .subscribeWith(new DisposableObserver<MealDescriptionsList>() {
            @Override
            public void onNext(@NonNull MealDescriptionsList mealDescriptionsList) {
                Log.d("MealDescriptionFrag", "Meal id " + meal_id + ":\n");
                MealDescription mealDescription = mealDescriptionsList.getMealDescription().get(0);

                // Set category meal description
                if(currentCategoryId == null) currentCategoryId = mealDescription.getStrCategory();
                logMeals(mealDescriptionsList);

                updateLayoutWithData(mealDescription);

                favoritesRepository.observeMealEntity(context, mealEntities -> {
                    initMealEntityFromApi(meal_id, currentCategoryId);
                    updateFavoriteIcon(mealDescription);
                });

                // Toggle bookmark (favorite) for this meal
                heartIcon.setOnClickListener(v -> {
                    if (!isFavoriteMeal(mealDescription))
                        insertMealEntity();
                    else
                        deleteMealEntity();
                });
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("MealDescriptionAct", e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("MealDescriptionAct", "complet");
            }
        });
    }

    /**
     * Deletes the meal entity from the favorites database
     */
    private void deleteMealEntity() {
        favoritesRepository.delete(mealEntity);
        heartIcon.setBackgroundResource(R.drawable.heart_icon_selector);
        Toast.makeText(getActivity(), String.format(
                "Removed %s from favorites.", mealEntity.getStrMeal()
        ), Toast.LENGTH_SHORT).show();
    }

    /**
     * Inserts the meal entity into the favorites database
     */
    private void insertMealEntity() {
        favoritesRepository.insert(mealEntity);
        heartIcon.setBackgroundResource(R.drawable.heart_icon);
        Toast.makeText(getActivity(), String.format(
                "Added %s to favorites!", mealEntity.getStrMeal()
        ), Toast.LENGTH_SHORT).show();
    }

    /**
     * Updates the favorite icon for the meal depending on whether it is bookmarked or not
     *
     * @param mealDescription the meal description for the current meal
     */
    private void updateFavoriteIcon(MealDescription mealDescription) {
        if (!isFavoriteMeal(mealDescription)) {
            heartIcon.setBackgroundResource(R.drawable.heart_icon_selector);
        }
        else {
            heartIcon.setBackgroundResource(R.drawable.heart_icon);
        }
    }

    /**
     * Updates the layout with data from a MealDescription object.
     * This includes displaying the meal's title on the action bar,
     * its thumbnail image, ingredients and quantities, and description.
     *
     * @param mealDescription the MealDescription object containing data for the layout
     */
    private void updateLayoutWithData(MealDescription mealDescription) {
        // Display meal title on the action bar
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar())
                .setTitle(mealDescription.getStrMeal());

        // Display meal thumbnail image
        updateMealImage(mealDescription);

        // Display meal ingredients and quantities
        TextView ingredients = view.findViewById(R.id.meal_ingredients);
        ingredients.setText(
                getString(R.string.meal_ingredients_first_line_decorator,
                        formatIngredientsAndQuantities(mealDescription))
                        .replace("\n", "\n- ")
        );

        // Display meal description
        TextView description = view.findViewById(R.id.meal_description);
        description.setText(mealDescription.getStrInstructions());
    }

    /**
     * Updates the meal's thumbnail image using Glide.
     * Use of Glide to display an image as provided in "TD 5. Appels réseaux"
     *
     * @param mealDescription the MealDescription object containing the meal thumbnail image
     */
    private void updateMealImage(MealDescription mealDescription) {
        ImageView image = view.findViewById(R.id.imageView);
        Glide.with(image.getContext())
                .load(mealDescription.getStrMealThumb())
                .into(image);
    }

    /**
     * Formats the ingredients and quantities of the meal in a human-readable string.
     *
     * @param mealDescription the MealDescription object containing the ingredients and quantities data
     * @return a formatted string of the ingredients and quantities
     */
    private String formatIngredientsAndQuantities(MealDescription mealDescription) {
        Map<String, String> mapIngredientsAndQuantities = mealDescription.getMapIngredientsAndQuantities();
        StringBuilder sb = new StringBuilder();
        int size = mapIngredientsAndQuantities.size();
        int i = 0;

        for (Map.Entry<String, String> entry : mapIngredientsAndQuantities.entrySet()) {
            sb.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue());
            if (++i < size) { sb.append("\n"); }    // No newline for the last item
        }

        return sb.toString();
    }

    /**
     * Logs the names of the meals in a MealDescriptionsList object.
     *
     * @param meals the MealDescriptionsList object containing a list of MealDescription objects
     */
    private void logMeals(MealDescriptionsList meals) {
        String mealsString = Optional.ofNullable(meals.getMealDescription())
                .map(mealCategories -> mealCategories.stream()
                        .map(MealDescription::getStrMeal)
                        .collect(Collectors.joining("\n")))
                .orElse("");
        Log.d("MealDescriptionAct", "\n" + mealsString);
    }

    /**
     * Checks in the local database to check if the meal currently displayed on the page
     * is bookmarked
     * @param meal the MealDescription displayed on this page
     * @return true if the meal on this page is in local favorites, false otherwise
     */
    private boolean isFavoriteMeal(MealDescription meal) {
        return favoritesRepository.isFavoriteMeal(meal.getIdMeal());
    }

    /**
     * Initializes a MealEntity object with data from the API.
     * The MealEntity object is created using the specified meal ID and category ID.
     *
     * @param mealId the ID of the meal to be initialized
     * @param categoryId the ID of the category the meal belongs to
     */
    private void initMealEntityFromApi(String mealId, String categoryId) {
        // Create the observable to fetch the meal description from the API
        Observable<MealsList> mealsListObservable = apiService.getMealsFromCategory(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);

        Disposable result = mealsListObservable.subscribeWith(new DisposableObserver<MealsList>() {
            @Override
            public void onNext(@NonNull MealsList mealsList) {
                for (Meal meal : mealsList.getMeals()) {
                    if (meal.getIdMeal().equals(mealId)) {
                        mealEntity = new MealEntity(mealId, meal.getStrMeal(),
                                meal.getStrMealThumb(), categoryId);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("MealFromCatFrag", e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("MealFromCatFrag", "complet");
            }
        });

    }
}

