package com.example.foodapp.ui.main.fragment;

import static com.example.foodapp.ui.main.MainActivity.ITEM_PER_ROW;
import static com.example.foodapp.ui.main.MainActivity.displayMode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.data.dao.entity.MealEntity;
import com.example.foodapp.data.dao.repository.FavoritesRepository;
import com.example.foodapp.ui.main.adapter.FavoritesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * FavoritesFragment is a Fragment subclass that displays a list of the user's favorite meals.
 * It is initialized with a list of MealEntity objects, which are passed from the parent Activity.
 * The Fragment displays the name and image of each meal in a RecyclerView.
 */
public class FavoritesFragment extends Fragment {

    private FavoritesAdapter recyclerAdapter;
    private FavoritesRepository favoritesRepository;
    private String title;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    /**
     * This method is called when the Fragment's view is created. It inflates the layout for the
     * fragment, and sets up the display of meals from favorites. The favorites are retrieved
     * from the repository, and the display is set up using a RecyclerView. The layout manager for
     * the RecyclerView is either a GridLayoutManager or a LinearLayoutManager, depending on the
     * value of the "gridDisplay" variable.
     * The ActionBar's title is also set to the title of the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in
     *                 the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be
     *                  attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     * @return The inflated view for the fragment.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recycler_layout, container, false);

        // Calling the repository
        favoritesRepository = FavoritesRepository.getInstance(requireActivity().getApplication());

        // Retrieving the title of the fragment
        if(getArguments() != null) {
            title = getArguments().getString("title");
        }
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(title);
        }

        // Setting up the display of meals from favorites
        List<MealEntity> list_favorites = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        updateFragmentDisplay();
        recyclerAdapter = new FavoritesAdapter(list_favorites, getContext());
        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    /**
     * Called when the fragment is visible and in the foreground.
     * This method is responsible for updating the data displayed in the fragment by getting the
     * list of favorites from the repository and displaying it in the RecyclerView.
     */
    @Override
    public void onResume() {
        super.onResume();

        // Getting the list of favorites
        displayFavorites(favoritesRepository);
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
     * Retrieves the list of favorite meals from the repository
     * and updates the RecyclerView with the new data.
     *
     * @param favoritesRepository the repository from which the list of favorite meals is retrieved
     */
    public void displayFavorites(FavoritesRepository favoritesRepository) {
        // Get the LiveData object from the repository
        LiveData<List<MealEntity>> mealsListLiveData = favoritesRepository.getAllMeals();

        mealsListLiveData.observe(this, mealsList -> {
            // Update the RecyclerView with the new data
            recyclerAdapter.setMealList(mealsList);
        });
    }

}