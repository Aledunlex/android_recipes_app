package com.example.foodapp.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodapp.R;
import com.example.foodapp.data.dao.repository.FavoritesRepository;
import com.example.foodapp.databinding.ActivityMainBinding;
import com.example.foodapp.ui.main.fragment.FavoritesFragment;
import com.example.foodapp.ui.main.fragment.HomeFragment;
import com.example.foodapp.ui.main.fragment.MealFromCategoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import kotlin.NotImplementedError;

/**
 * Main entry point for the app. It handles navigation between fragments
 * using a bottom navigation bar, and manages the app's action bar. It also stores the
 * state of the display method (grid or line).
 */
public class MainActivity extends AppCompatActivity {

    public static final int ITEM_PER_ROW = 2;
    ActivityMainBinding binding;
    public static String displayMode;

    /**
     * Initializes the MainActivity view and sets up the BottomNavigationView listener.
     * When a menu item is selected, the appropriate Fragment is displayed in the fragment
     * container.
     * If the "display" menu item is selected, the current Fragment is updated to switch between
     * linear and grid layout.
     *
     * @param savedInstanceState saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize display mode
        displayMode = "LINE";

        // Initialize the FavoritesRepository
        FavoritesRepository.getInstance(getApplication());

        // Inflate the activity layout and set it as the content view
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the BottomNavigationView listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (itemId == R.id.display) {
                // If the "display" menu item is selected, update the current Fragment to switch
                // between linear and grid layout
                try {
                    updateFragmentDisplay(fragmentTransaction);
                } catch (NotImplementedError e) {
                    // If a Fragment does not have the ability to switch between linear and grid
                    // layout, it will not be restarted and keep its LayoutManager
                    Log.d("MainActivity", e.getMessage());
                }
            } else {
                // Create a new Fragment and display it in the fragment container
                Bundle args = new Bundle();
                if (itemId == R.id.home) {
                    startNewFragment(fragmentTransaction, args,
                            new HomeFragment(), "Categories", "HOME");
                } else if (itemId == R.id.fave) {
                    startNewFragment(fragmentTransaction, args,
                            new FavoritesFragment(), "Favorites", "FAVORITES");
                }
                fragmentTransaction.commit();
            }
            // Commit the FragmentTransaction
            return true;
        });
        // Set up the initial Fragment in the fragment container
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();

        // Set the title of the Fragment as an argument
        Bundle args = new Bundle();
        args.putString("title", "Categories");
        homeFragment.setArguments(args);

        // Replace the current Fragment with the new Fragment and add it to the back stack
        fragmentTransaction.replace(R.id.fragment_container, homeFragment);
        fragmentTransaction.addToBackStack("HOME");
        fragmentTransaction.commit();

    }

    /**
     * Updates the gridDisplay static value, and restarts the given Fragment with its own arguments
     * @param fragmentTransaction the fragmentTransaction opened when a menu item has been clicked
     *
     * @throws NotImplementedError if the currentFragment has not been implemented for a
     *          display switch
     */
    private void updateFragmentDisplay(FragmentTransaction fragmentTransaction)
            throws NotImplementedError
    {
        // updates the static value of displayMode, from LINE to GRID or vice-versa
        if (displayMode.equals("LINE"))
            displayMode = "GRID";
        else
            displayMode = "LINE";

        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        Fragment updatedFragment;
        if (currentFragment instanceof HomeFragment) {
            ((HomeFragment) currentFragment).updateFragmentDisplay();
        } else if (currentFragment instanceof FavoritesFragment) {
            ((FavoritesFragment) currentFragment).updateFragmentDisplay();
        } else if (currentFragment instanceof MealFromCategoryFragment) {
            ((MealFromCategoryFragment) currentFragment).updateFragmentDisplay();
        } else {
            String errorMessage = String.format(
                    "Switching display for %s is not currently supported",
                    currentFragment.getClass().getCanonicalName());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            throw new NotImplementedError(errorMessage);
        }
    }

    /**
     * Start a new fragment with the provided transaction, arguments, fragment, title,
     * and fragment name.
     *
     * @param fragmentTransaction The transaction to use for the fragment.
     * @param args The arguments to pass to the fragment.
     * @param fragment The fragment to start.
     * @param title The title to display for the fragment.
     * @param fragmentName The name of the fragment.
     */
    private void startNewFragment(FragmentTransaction fragmentTransaction, Bundle args,
                                  Fragment fragment, String title, String fragmentName)
    {
        args.putString("title", title);
        args.putString("display", displayMode);
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(fragmentName);
    }

    // Required to override default behaviour to pop current fragment when pressing back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Required to override default behaviour to disable android interface back arrow on Home
    // or on Favorites
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (!(currentFragment instanceof HomeFragment ||
                currentFragment instanceof FavoritesFragment)) {
            super.onBackPressed();
        }
    }
}
