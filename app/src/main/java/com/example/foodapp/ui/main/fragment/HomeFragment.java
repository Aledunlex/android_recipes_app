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
import com.example.foodapp.data.api.model.CategoriesList;
import com.example.foodapp.data.api.model.Category;
import com.example.foodapp.ui.main.adapter.CategoryAdapter;

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
 * Displays the main screen of the application.
 * It displays a list of meal categories that the user can browse through.
 * When the user selects a category, a new fragment is opened that shows a list of meals within
 * that category.
 */
public class HomeFragment extends Fragment {

    private CategoryAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private String title;

    /**
     * This method is responsible for creating and returning the view hierarchy associated with
     * the fragment.
     * It inflates the layout for this fragment and sets up the title of the fragment in the
     * action bar.
     * It also sets up the RecyclerView for displaying a list of categories.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views
     *                 in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI
     *                  should be attached to. The fragment should not add the view itself,
     *                  but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here.
     * @return The view for the fragment's UI, or null
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recycler_layout, container, false);

        // Retrieving the title of the fragment
        if(getArguments() != null) {
            title = getArguments().getString("title");
        }
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(title);
        }

        List<Category> list_categories = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        updateFragmentDisplay();
        recyclerAdapter = new CategoryAdapter(list_categories, getContext());
        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    /**
     * Called when the fragment is resumed.
     * It calls the displayCategories method to retrieve and display the list of categories.
     */
    @Override
    public void onResume() {
        super.onResume();

        APIInterface apiService = APIClient.getClient().create(APIInterface.class);

        displayCategories(apiService);
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
     * Retrieves the list of categories from the API and updates the RecyclerView
     * with the new data.
     *
     * @param apiService The API interface used to make the API call.
     */
    private void displayCategories(APIInterface apiService) {
        Observable<CategoriesList> categoriesListObservable = apiService.getCategoriesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);

        Disposable result = categoriesListObservable.subscribeWith(new DisposableObserver<CategoriesList>() {
            @Override
            public void onNext(@NonNull CategoriesList categories) {
                logCategories(categories);
                recyclerAdapter.setMealCategoryList(categories.getCategories());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("HomeFrag", e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("HomeFrag", "complet");
            }
        });
    }

    /**
     * Logs the categories in the CategoriesList object.
     * If the categories object is not null, it maps each Category object in the list to
     * its strCategory value, joining them together with a "; " separator. If the categories
     * object is null, it returns an empty string.
     *
     * @param categories The CategoriesList object to be logged
     */
    private void logCategories(CategoriesList categories){
        String mealsString = Optional.ofNullable(categories.getCategories())
                .map(mealCategories -> mealCategories.stream()
                        .map(Category::getStrCategory)
                        .collect(Collectors.joining("; ")))
                .orElse("");
        Log.d("HomeFrag", "\n" + mealsString);
    }
}