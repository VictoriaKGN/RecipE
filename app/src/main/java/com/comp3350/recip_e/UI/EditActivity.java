//Edit Activity Java Class

package com.comp3350.recip_e.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.comp3350.recip_e.R;
import com.comp3350.recip_e.logic.InvalidRecipeException;
import com.comp3350.recip_e.logic.RecipeManager;
import com.comp3350.recip_e.objects.Ingredient;
import com.comp3350.recip_e.objects.Instruction;
import com.comp3350.recip_e.objects.Recipe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1001;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri pictureUri = null;

    private ArrayList<String> ingredientArrayList;
    private ArrayList<String> instructionArrayList;
    private ArrayAdapter<String> ingredientArrayAdapter;
    private ArrayAdapter<String> instructionArrayAdapter;
    private ListView ingredientListView;
    private ListView instructionListView;
    private int selectedIngredientIndex;
    private int selectedInstructionIndex;

    private RecipeManager recipeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        selectedIngredientIndex = -1;
        selectedInstructionIndex = -1;

        ingredientArrayList = new ArrayList<String>();
        instructionArrayList = new ArrayList<String>();
        ingredientArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, ingredientArrayList);
        instructionArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, instructionArrayList);

        ingredientListView = findViewById(R.id.ingredients_container);
        instructionListView = findViewById(R.id.instructions_container);

        ingredientListView.setAdapter(ingredientArrayAdapter);
        instructionListView.setAdapter(instructionArrayAdapter);
        setBtnsEnabled(false, true);
        setBtnsEnabled(false, false);

        recipeManager = new RecipeManager();

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

        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (i == selectedIngredientIndex)
                {
                    ingredientListView.setItemChecked(i, false);
                    selectedIngredientIndex = -1;
                    setSelector(false, true);
                    setBtnsEnabled(false, true);
                    clearIngredients();
                }
                else
                {
                    setSelector(true, true);
                    ingredientListView.setItemChecked(i, true);
                    setBtnsEnabled(true, true);
                    selectedIngredientIndex = i;
                    selectIngredientAtPosition(i);

                }
            }
        });

        instructionListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (i == selectedInstructionIndex)
                {
                    instructionListView.setItemChecked(i, false);
                    selectedInstructionIndex = -1;
                    setSelector(false, false);
                    setBtnsEnabled(false, false);
                    clearInstructions();
                }
                else
                {
                    setSelector(true, false);
                    instructionListView.setItemChecked(i, true);
                    setBtnsEnabled(true, false);
                    selectedInstructionIndex = i;
                    selectInstructionAtPosition(i);

                }
            }
        });
    }

    private void listViewItemClick()
    {

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
        Toast.makeText(EditActivity.this, "Im here", Toast.LENGTH_SHORT).show();

        EditText recName = (EditText) findViewById(R.id.recipe_name);
        String rec_text = recName.getText().toString().trim();

        if(isValidInput(rec_text))
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

        if(isValidInput(serveNum_text))
        {
            //EditText is empty
            Toast.makeText(this, "Please enter the # of people it serves", Toast.LENGTH_SHORT).show();
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

        if(isValidInput(timePrep_text))
        {
            //EditText is empty
            Toast.makeText(this, "Please enter the prepping time", Toast.LENGTH_SHORT).show();
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

        if(isValidInput(timeCook_text))
        {
            //EditText is empty
            Toast.makeText(this, "Please enter the cooking time", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //EditText is not empty
            cookTime = Integer.parseInt(timeCook_text);
            allValid++;
        }

        if(ingredientArrayList.isEmpty())
        {
            //is empty
            Toast.makeText(this, "Please enter ingredients", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //is not empty
            allValid++;
        }

        if(instructionArrayList.isEmpty())
        {
            //is empty
            Toast.makeText(this, "Please enter instructions ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //is not empty
            allValid++;
        }

        // check if all fields are non-empty
        if (allValid == 6)
        {
            /*Toast.makeText(EditActivity.this, "All valid", Toast.LENGTH_SHORT).show();
            // pass back all the info collected
            Intent intent = new Intent();

            Bundle extras = new Bundle();
            extras.putString("RECIPE_NAME", rec_text);
            extras.putInt("NUM_SERVES", serves);
            extras.putInt("PREP_TIME", prepTime);
            extras.putInt("COOK_TIME", cookTime);
            //extras.putStringArrayList("INGREDIENTS", ingredientList);
            //extras.putString("INSTRUCTIONS", inst_text);

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
            finish();*/
            // TODO: create Instructions
            ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

            // TODO create Ingredients
            ArrayList<Instruction> instructions = new ArrayList<Instruction>();

            Recipe newRecipe = null;

            if (pictureUri != null)
            {
                String path = saveImage(pictureUri);
                newRecipe = new Recipe(rec_text, ingredients, instructions, serves, prepTime, cookTime, path);
            }
            else
            {
                newRecipe = new Recipe(rec_text, ingredients, instructions, serves, prepTime, cookTime);
            }

            try
            {
                recipeManager.addRecipe(newRecipe);

                Intent intent = new Intent();

                Bundle extras = new Bundle();
                extras.putInt("NEW_RECIPE_ID", newRecipe.getID());

                intent.putExtras(extras);
                setResult(7, intent);
                finish();
            }
            catch (InvalidRecipeException exc)
            {
                Toast.makeText(EditActivity.this, "The recipe was not valid. Progress is lost ...", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private boolean isValidInput(String input)
    {
        return (input.isEmpty() || input.length() == 0 || input.equals("") || input == null);
    }

    //The function below validates discarding changes.
    //Appropriate messages are thrwon to the user to confirm their actions.
    //Another message is thrown after the confirmed removal that lets the user know that their recipe changes were successfully discarded.
    public void discardChanges_click(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
        builder.setTitle("Discard Changes?");
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

    // set the picture of the recipe if there is one
    public void setPicture(Drawable picture)
    {
        ImageView pic = findViewById(R.id.recipe_pic);
        pic.setBackground(picture);
    }

    // set the ingredients of the recipe
    public void setIngredients(ArrayList<Ingredient> ingredientList)
    {
        ArrayAdapter<Ingredient> ingredientAdapter = new ArrayAdapter<Ingredient>(getApplicationContext(), android.R.layout.simple_list_item_1, ingredientList);

        ingredientListView.setAdapter(ingredientAdapter);
        ingredientAdapter.notifyDataSetChanged();
    }

    // set the instructions of the recipe
    public void setInstructions(ArrayList<Instruction> instructionList)
    {
        ArrayAdapter<Instruction> instructionAdapter = new ArrayAdapter<Instruction>(getApplicationContext(), android.R.layout.simple_list_item_1, instructionList);

        instructionListView.setAdapter(instructionAdapter);
        instructionAdapter.notifyDataSetChanged();
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
    public ArrayList<String> getIngredients()
    {
        return ingredientArrayList;
    }

    // get the instructions
    public ArrayList<String> getInstructions()
    {
        return instructionArrayList;
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


    private void selectIngredientAtPosition(int pos)
    {
        String selected = ingredientArrayList.get(pos);
        String[] splitted = selected.split(" ");

        EditText amount = findViewById(R.id.amount_input);
        EditText unit = findViewById(R.id.unit_input);
        EditText ingredient = findViewById(R.id.ingredient_input);

        amount.setText(splitted[0]);
        unit.setText(splitted[1]);
        ingredient.setText(splitted[2]);
    }

    private void selectInstructionAtPosition(int pos)
    {
        String selected = instructionArrayList.get(pos);
        EditText instruction = findViewById(R.id.instruction_input);

        instruction.setText(selected);
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

    //
    public void addIngredient_click(View view)
    {
        EditText amount = findViewById(R.id.amount_input);
        String amountString = amount.getText().toString().trim();

        EditText unit = findViewById(R.id.unit_input);
        String unitString = unit.getText().toString().trim();

        EditText ingredient = findViewById(R.id.ingredient_input);
        String ingredientString = ingredient.getText().toString().trim();

        if (!amountString.isEmpty() && !amountString.equals("\\s+") && !unitString.isEmpty() && !unitString.equals("\\s+") && !ingredientString.isEmpty() && !ingredientString.equals("\\s+"))
        {
            if (isIngredientExist(ingredientString, -1))
            {
                Toast.makeText(this, "The inputted ingredient already exists... \nPlease edit the existing one to update...", Toast.LENGTH_LONG).show();
            }
            else
            {
                String input = amountString + " " + unitString + " " + ingredientString;

                ingredientArrayList.add(input);
                ingredientArrayAdapter.notifyDataSetChanged();

                clearIngredients();
                setSelector(false, true);
            }
        }
        else
        {
            Toast.makeText(this, "Please fill all ingredient fields before proceeding...", Toast.LENGTH_LONG).show();
        }

        //setSelector(false, true);
    }

    public void addInstruction_click(View view)
    {
        EditText instruction = findViewById(R.id.instruction_input);
        String string = instruction.getText().toString().trim();

        if (!string.isEmpty() && !string.equals("\\s+"))
        {
            instructionArrayList.add(string);
            instructionArrayAdapter.notifyDataSetChanged();

            clearInstructions();
            setSelector(false, false);
        }
        else
        {
            Toast.makeText(this, "Please fill the instruction field before proceeding...", Toast.LENGTH_LONG).show();
        }
    }

    //
    public void updateIngredient_click(View view)
    {
        EditText amount = findViewById(R.id.amount_input);
        String amountString = amount.getText().toString().trim();

        EditText unit = findViewById(R.id.unit_input);
        String unitString = unit.getText().toString().trim();

        EditText ingredient = findViewById(R.id.ingredient_input);
        String ingredientString = ingredient.getText().toString().trim();

        if (!amountString.isEmpty() && !amountString.equals("\\s+") && !unitString.isEmpty() && !unitString.equals("\\s+") && !ingredientString.isEmpty() && !ingredientString.equals("\\s+"))
        {
            if (!isIngredientExist(ingredientString, selectedIngredientIndex) )
            {
                String input = amountString + " " + unitString + " " + ingredientString;

                ingredientArrayList.set(selectedIngredientIndex, input);
                ingredientArrayAdapter.notifyDataSetChanged();

                clearIngredients();
                selectedIngredientIndex = -1;
                setSelector(false, true);
            }
            else
            {
                Toast.makeText(this, "The inputted ingredient already exists... \nPlease edit the existing one to update...", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Please fill all ingredient fields...", Toast.LENGTH_LONG).show();
        }


    }

    public void updateInstruction_click(View view)
    {
        EditText instruction = findViewById(R.id.instruction_input);
        String string = instruction.getText().toString().trim();

        if (!string.isEmpty() && !string.equals("\\s+"))
        {
            instructionArrayList.set(selectedInstructionIndex, string);
            instructionArrayAdapter.notifyDataSetChanged();

            clearInstructions();
            selectedInstructionIndex = -1;
            setSelector(false, false);
        }
        else
        {
            Toast.makeText(this, "Please fill the instruction field before proceeding...", Toast.LENGTH_LONG).show();
        }
    }

    //
    public void deleteIngredient_click(View view)
    {
        ingredientArrayList.remove(selectedIngredientIndex);
        ingredientArrayAdapter.notifyDataSetChanged();
        selectedIngredientIndex = -1;
        clearIngredients();
        setSelector(false, true);
        setBtnsEnabled(false, true);
    }

    public void deleteInstruction_click(View view)
    {
        instructionArrayList.remove(selectedInstructionIndex);
        instructionArrayAdapter.notifyDataSetChanged();

        selectedInstructionIndex = -1;
        clearInstructions();
        setSelector(false, false);
        setBtnsEnabled(false, false);
    }

    private boolean isIngredientExist(String ingredient, int index)
    {
        boolean retVal = false;
        String[] splitted = null;

        for (int i = 0; i < ingredientArrayList.size(); i++)
        {
            splitted = ingredientArrayList.get(i).split(" ");
            if (splitted[2].equals(ingredient) && i != index)
            {
                retVal = true;
            }
        }

        return retVal;
    }

    private void clearIngredients()
    {
        EditText amount = findViewById(R.id.amount_input);
        EditText unit = findViewById(R.id.unit_input);
        EditText ingredient = findViewById(R.id.ingredient_input);

        amount.setText("");
        unit.setText("");
        ingredient.setText("");
    }

    private void clearInstructions()
    {
        EditText instruction = findViewById(R.id.instruction_input);
        instruction.setText("");
    }

    private void setBtnsEnabled(boolean isEnabled, boolean isIngredient)
    {

        ImageButton updateBtn = null;
        ImageButton deleteBtn = null;

        if (isIngredient)
        {
            updateBtn = findViewById(R.id.update_ingredient_button);
            deleteBtn = findViewById(R.id.delete_ingredient_button);
        }
        else
        {
            updateBtn = findViewById(R.id.update_instruction_button);
            deleteBtn = findViewById(R.id.delete_instruction_button);
        }

        updateBtn.setEnabled(isEnabled);
        deleteBtn.setEnabled(isEnabled);
    }

    private void setSelector(boolean isSelected, boolean isIngredient)
    {
        ListView lv= null;

        if (isIngredient)
            lv = ingredientListView;
        else
            lv = instructionListView;

        if (isSelected)
            lv.setSelector(R.color.accent);
        else
        {
            lv.setSelector(R.color.background);
        }
    }
}