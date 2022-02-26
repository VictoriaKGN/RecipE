package com.comp3350.recip_e.UI;

import androidx.appcompat.app.AppCompatActivity;
import com.comp3350.recip_e.R;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_edit);
    }

    // changes the view to the right side
    public void changeToRightView_click(View view)
    {
        Intent rIntent = new Intent(LeftEditActivity.this, MainActivity.class);
        LeftEditActivity.this.startActivity(rIntent);
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
    public String getServings(String newServes)
    {
        EditText text = findViewById(R.id.num_serves);
        return text.getText().toString();
    }

    // get the prep time from input
    public String getPrepTime(String newTime)
    {
        EditText text = findViewById(R.id.num_prep);
        return text.getText().toString();
    }

    // get the cooking time from input
    public String getCookingTime(String toChange)
    {
        EditText text = findViewById(R.id.num_cook);
        return text.getText().toString();
    }

    // get the recipe name from input
    public String getRecipeName(String newName)
    {
        EditText text = findViewById(R.id.recipe_name);
        return text.getText().toString();
    }

    /*// get the picture of the recipe if there is one
    public void getPicture(Drawable picture)
    {
        ImageView pic = findViewById(R.id.picture);
        pic.setBackground(picture);
    }*/

    // get the ingredients of the recipe from input
    public String getIngredients(String ings)
    {
        EditText text = findViewById(R.id.ingredients);
        return text.getText().toString();
    }

}