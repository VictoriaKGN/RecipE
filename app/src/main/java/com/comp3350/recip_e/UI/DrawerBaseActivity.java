package com.comp3350.recip_e.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.comp3350.recip_e.R;
import com.comp3350.recip_e.logic.RecipeManager;
import com.comp3350.recip_e.objects.Recipe;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected RecipeManager recipeManager;
    private boolean isNameSearch;

    @Override
    public void setContentView(View view)
    {
        recipeManager = new RecipeManager();
        isNameSearch = true;

        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activity_container);
        container.addView(view);

        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setRecipeMenu();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ImageButton profileBtn = toolbar.findViewById(R.id.profile_button);
        profileBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // start profile activity

            }
        });


        SearchView searchBar = navigationView.getHeaderView(0).findViewById(R.id.search_bar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                Menu menu = navigationView.getMenu();
                menu.clear();
                ArrayList<Recipe> recipe_list = null;

                if (isNameSearch = true)        // search by name
                    recipe_list = recipeManager.searchRecipeByName("user@email.com", s);
                else if (isNameSearch != true)  // search by ingredient
                    recipe_list = recipeManager.searchRecipeByIngredient("user@email.com", s);
                //TODO (((App)getApplication()).getCurrentUser().getEmail());

                for (Recipe rec : recipe_list) {
                    menu.add(0, rec.getID(), 0, rec.getName());
                }
                return false;
            }
        });

        RadioGroup searchMode = navigationView.getHeaderView(0).findViewById(R.id.search_modes);
        searchMode.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.name_mode)
                {
                    isNameSearch = true;
                }
                else
                {
                    isNameSearch = false;
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        drawerLayout.closeDrawer(GravityCompat.START);

        Intent intent = new Intent(DrawerBaseActivity.this, ViewActivity.class);
        intent.putExtra("RecipeID", item.getItemId());

        startActivity(intent);
        overridePendingTransition(0, 0);

        return true;
    }

    // set drawer menu items
    private void setRecipeMenu()
    {
        Menu menu = navigationView.getMenu();
        ArrayList<Recipe> userRecs = recipeManager.getUserRecipes("user@email.com"); //TODO (((App)getApplication()).getCurrentUser().getEmail());

        for (Recipe rec : userRecs)
        {
            menu.add(0, rec.getID(), 0, rec.getName());
        }
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    public void changeMode_click()
    {
        isNameSearch = !isNameSearch;

        RadioButton name = navigationView.getHeaderView(0).findViewById(R.id.name_mode);

        RadioButton ingredient = navigationView.getHeaderView(0).findViewById(R.id.ingredient_mode);
        ingredient.toggle();


    }
}