package com.comp3350.recip_e.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.comp3350.recip_e.R;
import com.comp3350.recip_e.databinding.ActivityViewBinding;
import com.comp3350.recip_e.objects.Recipe;
import com.comp3350.recip_e.application.App;

import java.io.File;
import java.util.ArrayList;

public class ViewActivity extends DrawerBaseActivity {

    private Recipe currRecipe;
    private ActivityResultLauncher<Intent> activityLauncher;
    private ActivityViewBinding activityViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewBinding = ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(activityViewBinding.getRoot());

        ImageView recipePic = findViewById(R.id.recipe_pic);
        recipePic.setClickable(true);
        recipePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePopup();
            }
        });

        currRecipe = null;

        activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == 7)
                    {
                        Bundle extras = result.getData().getExtras();

                        if (extras != null)
                        {
                            // extract the new recipe
                            int newRecipeID = extras.getInt("NEW_RECIPE_ID");
                            Recipe newRecipe = recipeManager.getRecipe(newRecipeID);

                            currRecipe = newRecipe;
                            setAllFields();

                        }
                    }
                }
            }
        );

        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            int recipeID = bundle.getInt("RecipeID");
            currRecipe = recipeManager.getRecipe(recipeID);
            setAllFields();
        }
        else
        {
            currRecipe = recipeManager.getUserRecipes(((App)getApplication()).getCurrentUser().getEmail()).get(0);
            setAllFields();
        }
    }

    // ********************************** set methods ***************************************
    // set all fields
    public void setAllFields()
    {
        setRecipeMenu();
        if (currRecipe != null)
        {
            setRecipeName(currRecipe.getName());
            setServings(String.valueOf(currRecipe.getServings()));
            setPrepTime(String.valueOf(currRecipe.getPrepTime()));
            setCookingTime(String.valueOf(currRecipe.getCookTime()));
            setIngredients(currRecipe.getIngredients());
            setInstructions(currRecipe.getInstructions());

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
            setIngredients(new ArrayList<String>());
            setInstructions(new ArrayList<String>());
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
            pic.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        else
        {
            pic.setBackgroundResource(R.drawable.default_picture_icon);
        }
    }

    // set the ingredients of the recipe
    public void setIngredients(ArrayList<String> ingredientList)
    {
        ListView list = findViewById(R.id.ingredients);
        ArrayAdapter<String> ingredientAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, ingredientList);

        list.setAdapter(ingredientAdapter);
        ingredientAdapter.notifyDataSetChanged();
    }

    // set the instructions of the recipe
    public void setInstructions(ArrayList<String> instructionList)
    {
        ListView list = findViewById(R.id.instructions);
        ArrayAdapter<String> instructionAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, instructionList);

        list.setAdapter(instructionAdapter);
        instructionAdapter.notifyDataSetChanged();
    }

    // ********************************** button clicks ***************************************

    // change the view to the new recipe layout and pass in the recipe manager
    public void addRecipe_click(View view)
    {
        Intent intent = new Intent(ViewActivity.this, EditActivity.class);
        Bundle newBundle = new Bundle();
        newBundle.putBoolean("isEdit", false);
        intent.putExtras(newBundle);
        activityLauncher.launch(intent);
    }

    // change flipper view
    public void changeToRightView_click(View view)
    {
        ViewFlipper flipper = findViewById(R.id.view_flipper);
        flipper.showNext();
    }

    public void editRecipe_click(View view)
    {
        Intent intent = new Intent(ViewActivity.this, EditActivity.class);
        Bundle newBundle = new Bundle();
        newBundle.putBoolean("isEdit", true);
        newBundle.putInt("RecipeId", currRecipe.getID());
        intent.putExtras(newBundle);
        activityLauncher.launch(intent);
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
                recipeManager.deleteRecipe(currRecipe.getID(), ((App)getApplication()).getCurrentUser().getEmail());
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

    private void showImagePopup()
    {
        if (currRecipe.getPicture() != null)
        {
            Dialog builder = new Dialog(this);
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //nothing;
                }
            });

            ImageView imageView = new ImageView(this);
            imageView.setImageURI(Uri.fromFile(new File(currRecipe.getPicture())));
            builder.addContentView(imageView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            builder.show();
        }
    }
}