package com.comp3350.recip_e.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.comp3350.recip_e.R;
import com.comp3350.recip_e.databinding.ActivityViewBinding;
import com.comp3350.recip_e.logic.InvalidRecipeException;
import com.comp3350.recip_e.objects.Recipe;

import java.io.File;

public class ViewActivity extends DrawerBaseActivity {

    private Recipe currRecipe;
    private ActivityResultLauncher<Intent> activityLauncher;
    private ActivityViewBinding activityViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewBinding = ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(activityViewBinding.getRoot());

        activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == 7)
                    {
                        Intent intent = result.getData();

                        if (intent != null)
                        {
                            // extract all the data
                            Bundle bundle = intent.getExtras();
                            Recipe newRecipe = null;

                            if(bundle.getString("RECIPE_PICTURE") != null) {
                                try {
                                    //newRecipe = new Recipe(bundle.getString("RECIPE_NAME"), bundle.getString("INGREDIENTS"), bundle.getString("INSTRUCTIONS"),
                                            //bundle.getInt("NUM_SERVES"), bundle.getInt("PREP_TIME"), bundle.getInt("COOK_TIME"), bundle.getString("RECIPE_PICTURE"));
                                } catch (InvalidRecipeException exc)
                                {
                                    Toast.makeText(ViewActivity.this, "The recipe was not valid. Progress is lost ...", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                try
                                {
                                    //newRecipe = new Recipe (bundle.getString("RECIPE_NAME"), bundle.getString("INGREDIENTS"), bundle.getString("INSTRUCTIONS"),
                                            //bundle.getInt("NUM_SERVES"), bundle.getInt("PREP_TIME"), bundle.getInt("COOK_TIME"));
                                } catch (InvalidRecipeException exc)
                                {
                                    Toast.makeText(ViewActivity.this, "The recipe was not valid. Progress is lost ...", Toast.LENGTH_SHORT).show();
                                }

                            }

                            if (newRecipe != null)
                            {
                                recipeManager.addRecipe(newRecipe);
                                currRecipe = newRecipe;
                                setAllFields();
                            }
                        }
                    }
                }
            }
        );

        currRecipe = recipeManager.getFirstRecipe();
        setAllFields();
    }

    // ********************************** set methods ***************************************
    // set all fields
    public void setAllFields()
    {
        if (currRecipe != null)
        {
            setRecipeName(currRecipe.getName());
            setServings(String.valueOf(currRecipe.getServings()));
            setPrepTime(String.valueOf(currRecipe.getPrepTime()));
            setCookingTime(String.valueOf(currRecipe.getCookTime()));
            //setIngredients(currRecipe.getIngredients());
            //setInstructions(currRecipe.getInstructions());

            if (currRecipe.hasPicture())
            {
                setPicture(currRecipe.getPicture());
            }
        }
        else
        {
            setRecipeName("");
            setServings("");
            setPrepTime("");
            setCookingTime("");
            setIngredients("");
            setInstructions("");
            setPicture("");
        }
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
    public void setPicture(String picturePath)
    {
        File newFile = new File(picturePath);
        ImageView pic = findViewById(R.id.recipe_pic);

        if (newFile.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(newFile.getAbsolutePath());
            pic.setImageBitmap(myBitmap);
        }
        else
        {
            pic.setImageResource(android.R.color.transparent);
        }
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
        Intent intent = new Intent(ViewActivity.this, EditActivity.class);
        activityLauncher.launch(intent);
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
                recipeManager.deleteRecipe(currRecipe.getID());
                currRecipe = null;
                setAllFields();
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