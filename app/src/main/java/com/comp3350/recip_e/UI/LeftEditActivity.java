package com.comp3350.recip_e.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.comp3350.recip_e.R;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class LeftEditActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1001;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Uri pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_edit);

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

    // changes the view to the right side
    public void changeToRightView_click(View view)
    {
        Intent rIntent = new Intent(LeftEditActivity.this, MainActivity.class);
        LeftEditActivity.this.startActivity(rIntent);
    }

    // sends info to logic layer when user tries to save the input
    public void saveInput_click(View view)
    {

    }

    public void discardChanges_click(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LeftEditActivity.this);
        builder.setTitle("Discard Changes?");
        builder.setMessage("Are you sure you want to discard this new recipe? Your progress will be lost.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // user likes to proceed with the deletion
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(LeftEditActivity.this, "Changes discarded...", Toast.LENGTH_SHORT).show();
                // TODO: send the confirmation to logic layer
                finish();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() { // user does not want to proceed with the deletion
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(LeftEditActivity.this, "Progress was restored...", Toast.LENGTH_SHORT).show();
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
        //startActivityForResult(intent, IMAGE_PICK_CODE);
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

    // set the picture of the recipe if there is one
    public void setPicture(Drawable picture)
    {
        ImageView pic = findViewById(R.id.picture);
        pic.setBackground(picture);
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

}