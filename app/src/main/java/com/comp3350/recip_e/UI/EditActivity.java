//Edit Activity Java Class

package com.comp3350.recip_e.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import com.comp3350.recip_e.R;
import com.comp3350.recip_e.logic.InvalidRecipeException;
import com.comp3350.recip_e.objects.Recipe;
import com.comp3350.recip_e.databinding.ActivityEditBinding;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EditActivity extends DrawerBaseActivity {

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
    private ActivityEditBinding activityEditBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditBinding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(activityEditBinding.getRoot());

        selectedIngredientIndex = -1;
        selectedInstructionIndex = -1;

        ingredientArrayList = new ArrayList<String>();
        instructionArrayList = new ArrayList<String>();
        ingredientArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, ingredientArrayList);
        instructionArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, instructionArrayList);

        ingredientListView = findViewById(R.id.ingredients_container);
        instructionListView = findViewById(R.id.instructions_container);

        ingredientListView.setAdapter(ingredientArrayAdapter);
        instructionListView.setAdapter(instructionArrayAdapter);
        setBtnsEnabled(false, true);
        setBtnsEnabled(false, false);

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




    private boolean isValidInput(String input, String message)
    {
        boolean retVal = true;

        if(input.isEmpty() || input.length() == 0 || input.equals("") || input == null)
        {
            Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
            retVal = false;
        }

        return retVal;
    }

    private boolean isValidList(ArrayList<String> list, String message)
    {
        boolean retVal = true;

        if (list.isEmpty())
        {
            Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
            retVal = false;
        }

        return retVal;
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
    public void setIngredients(ArrayList<String> ingredientList)
    {
        ArrayAdapter<String> ingredientAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, ingredientList);

        ingredientListView.setAdapter(ingredientAdapter);
        ingredientAdapter.notifyDataSetChanged();
    }

    // set the instructions of the recipe
    public void setInstructions(ArrayList<String> instructionList)
    {
        ArrayAdapter<String> instructionAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, instructionList);

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
    public String saveImage(Uri uri)
    {
        String retVal = null;

        if (uri != null) {
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
            retVal = path.toString();
        }

        return retVal;
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
            if (doesIngredientExist(ingredientString, -1))
            {
                Toast.makeText(this, "The inputted ingredient already exists... \nPlease edit the existing one to update...", Toast.LENGTH_LONG).show();
            }
            else
            {
                String input = amountString + " " + unitString + " " + ingredientString;

                ingredientArrayList.add(input);
                ingredientArrayAdapter.notifyDataSetChanged();

                clearIngredients();
                selectedIngredientIndex = -1;
                setSelector(false, true);
                setBtnsEnabled(false, true);
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
            selectedIngredientIndex = -1;
            setSelector(false, false);
            setBtnsEnabled(false, false);
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
            if (!doesIngredientExist(ingredientString, selectedIngredientIndex) )
            {
                String input = amountString + " " + unitString + " " + ingredientString;

                ingredientArrayList.set(selectedIngredientIndex, input);
                ingredientArrayAdapter.notifyDataSetChanged();

                clearIngredients();
                selectedIngredientIndex = -1;
                setSelector(false, true);
                setBtnsEnabled(false, true);
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
            setBtnsEnabled(false, false);
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

    // input validation when the user tries to save a new recipe
    public void saveInput_click(View view)
    {
        EditText recName = (EditText) findViewById(R.id.recipe_name);
        String rec_text = recName.getText().toString().trim();

        EditText serveNum = (EditText) findViewById(R.id.num_serves);
        String serveNum_text = serveNum.getText().toString().trim();

        EditText timePrep = (EditText) findViewById(R.id.num_prep);
        String timePrep_text = timePrep.getText().toString().trim();

        EditText timeCook = (EditText) findViewById(R.id.num_cook);
        String timeCook_text = timeCook.getText().toString().trim();

        if (isValidInput(rec_text, "Please enter a recipe name") && isValidInput(serveNum_text, "Please enter the # of people it serves")
                && isValidInput(timePrep_text, "Please enter the prepping time") && isValidInput(timeCook_text, "Please enter the cooking time")
                && isValidList(ingredientArrayList, "Please enter ingredients") && isValidList(instructionArrayList, "Please enter instructions"))
        {
            String path = saveImage(pictureUri);

            try
            {
                Recipe newRecipe = new Recipe(rec_text, ingredientArrayList, instructionArrayList, Integer.parseInt(serveNum_text), Integer.parseInt(timePrep_text), Integer.parseInt(timeCook_text), path, "user@email.com");
                // TODO: change the user to ((App)getApplication()).getCurrentUser().getEmail()

                Recipe recipe = recipeManager.addRecipe(newRecipe);

                Intent intent = new Intent();

                Bundle extras = new Bundle();
                extras.putInt("NEW_RECIPE_ID", recipe.getID());

                intent.putExtras(extras);
                setResult(7, intent);
                finish();
            }
            catch (InvalidRecipeException exc)
            {
                Toast.makeText(EditActivity.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean doesIngredientExist(String ingredient, int index)
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