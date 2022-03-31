//Edit Activity Java Class

package com.comp3350.recip_e.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.comp3350.recip_e.R;
import com.comp3350.recip_e.databinding.ActivityEditBinding;
import com.comp3350.recip_e.objects.Recipe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditActivity extends DrawerBaseActivity {

    private static final int PERMISSION_CODE = 1001;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri pictureUri = null;
    private ActivityEditBinding activityEditBinding;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditBinding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(activityEditBinding.getRoot());
        isEdit = false;

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result)
            {
                if (result.getResultCode() == RESULT_OK) // set button to image
                {
                    ImageButton imageBtn = findViewById(R.id.recipe_pic);
                    pictureUri = result.getData().getData();
                    imageBtn.setImageURI(pictureUri);
                }
            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            isEdit = bundle.getBoolean("isEdit");

            if (isEdit)
            {
                setAllFields(recipeManager.getRecipe(bundle.getInt("RecipeId")));
            }

            setBtnsTint();
        }
    }

    // set all fields
    private void setAllFields(Recipe toSet)
    {
        setRecipeName(toSet.getName());
        setServings(String.valueOf(toSet.getServings()));
        setPrepTime(String.valueOf(toSet.getPrepTime()));
        setCookingTime(String.valueOf(toSet.getCookTime()));
        //setIngredients(currRecipe.getIngredients());
        //setInstructions(currRecipe.getInstructions());

        if (toSet.hasPicture())
        {
            ImageButton imageBtn = findViewById(R.id.recipe_pic);
            imageBtn.setImageURI(Uri.fromFile(new File(toSet.getPicture())));
        }
    }

    private void setBtnsTint()
    {
        ImageButton leftHighlight;
        ImageButton rightHightlight;
        ImageButton leftBtn;
        ImageButton rightBtn;

        if (isEdit)
        {
            leftHighlight = findViewById(R.id.edit_recipe_left_button);
            rightHightlight = findViewById(R.id.edit_recipe_right_button);
            leftBtn = findViewById(R.id.add_recipe_button);
            rightBtn = findViewById(R.id.add_recipe_right_button);
        }
        else
        {
            leftBtn = findViewById(R.id.edit_recipe_left_button);
            rightBtn = findViewById(R.id.edit_recipe_right_button);
            leftHighlight = findViewById(R.id.add_recipe_button);
            rightHightlight = findViewById(R.id.add_recipe_right_button);
        }

        leftHighlight.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.background, null)));
        rightHightlight.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.background, null)));
        leftBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.accent, null)));
        rightBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.accent, null)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                pickImage();
            }
            else
            {
                Toast.makeText(this, "Permission Denied...", Toast.LENGTH_LONG).show();
            }
        }
    }


    // input validation when the user tries to save a new recipe
    public void saveInput_click(View view)
    {
        int allValid = 0;

        EditText recName = (EditText) findViewById(R.id.recipe_name);
        String rec_text = recName.getText().toString().trim();

        if(rec_text.isEmpty() || rec_text.length() == 0 || rec_text.equals("") || rec_text == null)
        {
            //EditText is empty
            Toast.makeText(this, "Please enter a recipe name ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //EditText is not empty
            allValid++;
        }

        EditText serveNum = (EditText) findViewById(R.id.num_serves);
        String serveNum_text = serveNum.getText().toString().trim();
        int serves = 0;

        if(serveNum_text.isEmpty() || serveNum_text.length() == 0 || serveNum_text.equals("") || serveNum_text == null)
        {
            //EditText is empty
            Toast.makeText(this, "Please enter the # of people it serves ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //EditText is not empty
            serves = Integer.parseInt(serveNum_text);
            allValid++;
        }

        EditText timePrep = (EditText) findViewById(R.id.num_prep);
        String timePrep_text = timePrep.getText().toString().trim();
        int prepTime = 0;

        if(timePrep_text.isEmpty() || timePrep_text.length() == 0 || timePrep_text.equals("") || timePrep_text == null)
        {
            //EditText is empty
            Toast.makeText(this, "Please enter the prepping time ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //EditText is not empty
            prepTime = Integer.parseInt(timePrep_text);
            allValid++;
        }

        EditText timeCook = (EditText) findViewById(R.id.num_cook);
        String timeCook_text = timeCook.getText().toString().trim();
        int cookTime = 0;

        if(timeCook_text.isEmpty() || timeCook_text.length() == 0 || timeCook_text.equals("") || timeCook_text == null)
        {
            //EditText is empty
            Toast.makeText(this, "Please enter the cooking time ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //EditText is not empty
            cookTime = Integer.parseInt(timeCook_text);
            allValid++;
        }

        EditText ingred = (EditText) findViewById(R.id.ingredients);
        String ingred_text = ingred.getText().toString().trim();

        if(ingred_text.isEmpty() || ingred_text.length() == 0 || ingred_text.equals("") || ingred_text == null)
        {
            //EditText is empty
            Toast.makeText(this, "Please enter ingredients ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //EditText is not empty
            allValid++;
        }

        EditText inst = (EditText) findViewById(R.id.instructions);
        String inst_text = inst.getText().toString().trim();

        if(inst_text.isEmpty() || inst_text.length() == 0 || inst_text.equals("") || inst_text == null)
        {
            //EditText is empty
            Toast.makeText(this, "Please enter instructions ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //EditText is not empty
            allValid++;
        }

        // check if all fields are non-empty
        if (allValid == 6)
        {
            // pass back all the info collected
            Intent intent = new Intent();

            Bundle extras = new Bundle();
            extras.putString("RECIPE_NAME", rec_text);
            extras.putInt("NUM_SERVES", serves);
            extras.putInt("PREP_TIME", prepTime);
            extras.putInt("COOK_TIME", cookTime);
            extras.putString("INGREDIENTS", ingred_text);
            extras.putString("INSTRUCTIONS", inst_text);

            if (pictureUri != null)
            {
                String path = saveImage(pictureUri);
                extras.putString("RECIPE_PICTURE", path);
            }
            else
            {
                extras.putString("RECIPE_PICTURE", null);
            }

            intent.putExtras(extras);
            setResult(7, intent);
            finish();
        }
    }

    //The function below validates discarding changes.
    //Appropriate messages are thrwon to the user to confirm their actions.
    //Another message is thrown after the confirmed removal that lets the user know that their recipe changes were successfully discarded.
    public void discardChanges_click(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
        builder.setTitle("Discard Changes?");

        if (isEdit)
            builder.setMessage("Are you sure you want to discard the new changes? Your progress will be lost.");
        else
            builder.setMessage("Are you sure you want to discard this new recipe? Your progress will be lost.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // user likes to proceed with the deletion
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(EditActivity.this, "Changes discarded...", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        //Below is when a user does not want to delete the recipe.
        //If the user selects "No" their progress is restored and they return to the screen the way it was initially.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() { // user does not want to proceed with the deletion
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(EditActivity.this, "Progress was restored...", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    // check for permission and pick a picture
    public void choosePic_click(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) // check runtime permission
        {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) // request permission since its not granted
            {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_CODE); // show popup for runtime permission
            }
            else // permission already granted
            {
                pickImage();
            }
        }
        else // system OS is less than marshmallow
        {
            pickImage();
        }
    }

    private void pickImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }

    // set the # of serves
    public void setServings(String newServes)
    {
        EditText text = findViewById(R.id.num_serves);
        text.setText(newServes);
    }

    // set the prep time
    public void setPrepTime(String newTime)
    {
        EditText text = findViewById(R.id.num_prep);
        text.setText(newTime);
    }

    // set the cooking time
    public void setCookingTime(String toChange)
    {
        EditText text = findViewById(R.id.num_cook);
        text.setText(toChange);
    }

    // set the recipe name
    public void setRecipeName(String newName)
    {
        EditText text = findViewById(R.id.recipe_name);
        text.setText(newName);
    }

    // set the ingredients of the recipe
    public void setIngredients(String ings)
    {
        EditText text = findViewById(R.id.ingredients);
        text.setText(ings);
    }

    // get the # of serves from input
    public String getServings()
    {
        EditText text = findViewById(R.id.num_serves);
        return text.getText().toString();
    }

    // get the prep time from input
    public String getPrepTime()
    {
        EditText text = findViewById(R.id.num_prep);
        return text.getText().toString();
    }

    // get the cooking time from input
    public String getCookingTime()
    {
        EditText text = findViewById(R.id.num_cook);
        return text.getText().toString();
    }

    // get the recipe name from input
    public String getRecipeName()
    {
        EditText text = findViewById(R.id.recipe_name);
        return text.getText().toString();
    }

    // get the picture of the recipe if there is one
    public Uri getPicture()
    {
        return pictureUri;
    }

    // get the ingredients of the recipe from input
    public String getIngredients()
    {
        EditText text = findViewById(R.id.ingredients);
        return text.getText().toString();
    }

    /**
     * Save an image into internal storage
     *
     * @param uri Image to save
     * @return String representing the file path
     */
    public String saveImage(Uri uri) {
        Context context = this.getApplicationContext();
        Bitmap image = null;

        try {
            image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);   // Get the image from the gallery
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(Environment.DIRECTORY_PICTURES, Context.MODE_PRIVATE);   // Get the directory of internal storage
        File path = new File(directory, System.currentTimeMillis() + "_recipe.jpg");   // Create the file in internal storage
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(path);
            image.compress(Bitmap.CompressFormat.PNG, 100, out);    // Write the image data to the image file
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path.toString();
    }

// ********************************** button clicks ***************************************

    //The layout of the page is in such a way that the user can flip back and forth between two pages.
    //There are "<" and ">" buttons which denote next/right and previous/left page.

    // change the view to the new recipe layout and pass in the recipe manager

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

}