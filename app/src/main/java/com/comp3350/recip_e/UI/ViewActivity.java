package com.comp3350.recip_e.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.comp3350.recip_e.R;

public class ViewActivity extends AppCompatActivity {

    // TODO: recipe manager global variable, current viewing recipe id
    // private RecipeManager recipeManager;
    // private int recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // recipeManager = new RecipeManager();
        // recipeID = recipeManager.getFirst().getID();

        // if (recipeID ==
    }

    // ********************************** set methods ***************************************

    // set all fields
    public void setAllFields()
    {
        /*
        Recipe currRecipe = recipeManager.getRecipe(recipeID);

        setRecipeName(currRecipe.getName());
        setServings(currRecipe.getServings());
        setPrepTime(currRecipe.getPrep());
        setCookingTime(currRecipe.getCooking());
        setIngredients(currRecipe.getIngredients());
        setInstructions(currRecipe.getInstructions());

        // TODO: set the picture if there is one

         */
    }

    // set the # of serves
    public void setServings(String newServes)
    {
        TextView text = findViewById(R.id.num_serves);
        text.setText(newServes);
    }

    // set the prep time
    public void setPrepTime(String newTime)
    {
        TextView text = findViewById(R.id.num_prep);
        text.setText(newTime);
    }

    // set the cooking time
    public void setCookingTime(String toChange)
    {
        TextView text = findViewById(R.id.num_cook);
        text.setText(toChange);
    }

    // set the recipe name
    public void setRecipeName(String newName)
    {
        TextView text = findViewById(R.id.recipe_name);
        text.setText(newName);
    }

    // set the picture of the recipe if there is one
    public void setPicture(Drawable picture)
    {
        ImageView pic = findViewById(R.id.recipe_pic);
        pic.setBackground(picture);
    }

    // set the ingredients of the recipe
    public void setIngredients(String ings)
    {
        TextView text = findViewById(R.id.ingredients);
        text.setText(ings);
    }

    // set the instructions of the recipe
    public void setInstructions(String ins)
    {
        TextView text = findViewById(R.id.instructions);
        text.setText(ins);
    }

    // ********************************** button clicks ***************************************

    // change the view to the new recipe layout and pass in the recipe manager
    public void addRecipe_click(View view)
    {
        Intent intent = new Intent(ViewActivity.this, LeftEditActivity.class);
        /* Bundle b = new Bundle();

        b.putString("recipeManager", recipeManager);
        intent.putExtras(b);*/
        ViewActivity.this.startActivity(intent);
    }

    // change flipper view
    public void changeToRightView_click(View view)
    {
        ViewFlipper flipper = findViewById(R.id.view_flipper);
        flipper.showNext();
    }

    // changes the view to the left side
    public void changeToLeftView_click(View view)
    {
        ViewFlipper flipper = findViewById(R.id.view_flipper);
        flipper.showPrevious();
    }

    // double check with the user if they want to proceed with the deletion, and if so, send a signal to logic layer
    public void deleteRecipe_click(View view)
    {
        TextView text = findViewById(R.id.recipe_name);

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivity.this);
        builder.setTitle("Discard Recipe?");
        builder.setMessage("Are you sure you want to discard " + text.getText() + "? The recipe will be lost. ");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // user likes to proceed with the deletion
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(ViewActivity.this, "Recipe deleted...", Toast.LENGTH_SHORT).show();
                // TODO: send the confirmation to logic layer, view the previous recipe
                // recipeManager.deleteRecipe(recipeID);
                // recipeID =
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() { // user does not want to proceed with the deletion
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ViewActivity.this, "Recipe was restored...", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
}