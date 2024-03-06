package com.example.foodapp.ui.main.fragment;

import static com.example.foodapp.ui.main.MainActivity.ITEM_PER_ROW;
import static com.example.foodapp.ui.main.MainActivity.displayMode;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.data.api.APIClient;
import com.example.foodapp.data.api.APIInterface;
import com.example.foodapp.data.api.model.Meal;
import com.example.foodapp.data.api.model.MealsList;
import com.example.foodapp.ui.main.adapter.MealAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Displays a list of meals belonging to a specific category.
 * The user can select a meal to view its details.
 */
public class MealFromCategoryFragment extends Fragment {

    private MealAdapter mealAdapter;
    private APIInterface apiService;
    private String openedCategory;
    private String title;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This method sets up the display of meals belonging to a specific category,
     * as well as the action bar title and home button.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be
     *                  attached to. The fragment should not add the view itself, but this can be
     *                  used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from
     *                           a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recycler_layout, container, false);

        // Calling the API
        apiService = APIClient.getClient().create(APIInterface.class);

        // Retrieving the current category passed in previous view
        if(getArguments() != null) {
            openedCategory = getArguments().getString(getString(R.string.clicked_category_id));
            title = getArguments().getString("title");
        }

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Setting up the display of meals from current category
        List<Meal> list_meals = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        updateFragmentDisplay();
        mealAdapter = new MealAdapter(list_meals, openedCategory, getContext());
        recyclerView.setAdapter(mealAdapter);

        return view;
    }

    /**
     * Checks the value of MainActivity.displayMode and sets a new LayoutManage for this
     * Fragment depending on it.
     */
    public void updateFragmentDisplay() {
        if (displayMode.equals("GRID")) {
            layoutManager = new GridLayoutManager(getContext(), ITEM_PER_ROW);
        } else {
            layoutManager = new LinearLayoutManager(getContext());
        }
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * Displays a list of meals belonging to a specific category when the Fragment is resumed.
     */
    @Override
    public void onResume() {
        super.onResume();

        displayMealsFrom(apiService, openedCategory);
    }

    /**
     * Fetches a list of meals belonging to a specific category from an API service,
     * and displays them in a RecyclerView.
     *
     * @param apiService the API service used to fetch the data
     * @param some_category the category of meals to display
     */
    private void displayMealsFrom(APIInterface apiService, String some_category) {
        Observable<MealsList> mealsListObservable = apiService.getMealsFromCategory(some_category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);

        Disposable result = mealsListObservable.subscribeWith(new DisposableObserver<MealsList>() {
            @Override
            public void onNext(@NonNull MealsList mealsList) {
                logMeals(mealsList);
                mealAdapter.setMealList(mealsList.getMeals());
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

    /**
     * Logs the names of the meals in a MealsList object.
     *
     * @param meals the MealsList object containing a list of Meal objects
     */
    private void logMeals(MealsList meals) {
        String mealsString = Optional.ofNullable(meals.getMeals())
                .map(mealCategories -> mealCategories.stream()
                        .map(Meal::getStrMeal)
                        .collect(Collectors.joining("; ")))
                .orElse("");
        Log.d("MealFromCatFrag", mealsString);
    }
}
