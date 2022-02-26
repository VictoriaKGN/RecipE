package com.comp3350.recip_e.UI;

import androidx.appcompat.app.AppCompatActivity;
import com.comp3350.recip_e.R;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_view);
    }

    // changes the view to the right side
    public void changeToRightView_click(View view)
    {
        Intent rIntent = new Intent(LeftViewActivity.this, MainActivity.class);
        LeftViewActivity.this.startActivity(rIntent);
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
        ImageView pic = findViewById(R.id.picture);
        pic.setBackground(picture);
    }

    // set the ingredients of the recipe
    public void setIngredients(String ings)
    {
        TextView text = findViewById(R.id.ingredients);
        text.setText(ings);
    }

    public void addRecipe_click(View view)
    {
        Intent rIntent = new Intent(LeftViewActivity.this, LeftEditActivity.class);
        LeftViewActivity.this.startActivity(rIntent);
    }
}