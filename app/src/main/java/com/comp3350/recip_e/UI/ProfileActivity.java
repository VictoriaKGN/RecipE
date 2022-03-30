package com.comp3350.recip_e.UI;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.comp3350.recip_e.R;
import com.comp3350.recip_e.application.App;
import com.comp3350.recip_e.logic.UserManager;
import com.comp3350.recip_e.logic.exceptions.EmailDoesNotExistException;
import com.comp3350.recip_e.logic.exceptions.IncorrectPasswordException;
import com.comp3350.recip_e.objects.User;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> activityLauncher;
    private boolean profileMode;
    private boolean changeUsernameMode;
    private boolean changePasswordMode;
    private boolean logoutMode;
    private AlertDialog dialog;
    private UserManager userManagerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = null;
        profileMode = true;
        userManagerProfile = new UserManager();

        showDialog();
    }

    // ********************************** click methods ***************************************
    // when the user clicks on the change mode button (or, log in / or, sign up)
    public void changeMode_click()
    {
        // change all the fields
        TextView header = dialog.findViewById(R.id.header);
        Button changePassword = dialog.findViewById(R.id.changePassword);
        Button changeUsername = dialog.findViewById(R.id.changeUsername);
        Button logOut = dialog.findViewById(R.id.logout);
        Button doneUsername = dialog.findViewById(R.id.doneUsername);
        Button donePassword = dialog.findViewById(R.id.donePassword);
        Button goBackPassword = dialog.findViewById(R.id.goBackPassword);
        Button goBackUsername = dialog.findViewById(R.id.goBackUsername);


        EditText oldUsername = dialog.findViewById(R.id.oldUsername);
        EditText newUsername = dialog.findViewById(R.id.newUsername);
        EditText confirmNewUsername = dialog.findViewById(R.id.confirmNewUsername);
        EditText oldPassword = dialog.findViewById(R.id.oldPassword);
        EditText newPassword = dialog.findViewById(R.id.newPassword);
        EditText confirmNewPassword = dialog.findViewById(R.id.confirmNewPassword);

        if (profileMode)
        {
            header.setText(getResources().getString(R.string.profileHead));
            changeUsername.setText(getResources().getString(R.string.changeUsername));
            changePassword.setText(getResources().getString(R.string.changePassword));
            logOut.setText(getResources().getString(R.string.logout));
            oldPassword.setVisibility(View.GONE);
            newPassword.setVisibility(View.GONE);
            confirmNewPassword.setVisibility(View.GONE);
            oldUsername.setVisibility(View.GONE);
            newUsername.setVisibility(View.GONE);
            confirmNewUsername.setVisibility(View.GONE);
        }
        else if (changePasswordMode)
        {
            header.setText(getResources().getString(R.string.profileHead));
            oldPassword.setVisibility(View.VISIBLE);
            newPassword.setVisibility(View.VISIBLE);
            confirmNewPassword.setVisibility(View.VISIBLE);
            changePassword.setVisibility(View.GONE);
            changeUsername.setVisibility(View.GONE);
            logOut.setVisibility(View.GONE);
            doneUsername.setVisibility(View.GONE);
            donePassword.setVisibility(View.VISIBLE);
            goBackPassword.setVisibility(View.VISIBLE);
            goBackUsername.setVisibility(View.GONE);
//activity.finish ******
        }

        else if (changeUsernameMode)
        {

            header.setText(getResources().getString(R.string.profileHead));
            oldUsername.setVisibility(View.VISIBLE);
            newUsername.setVisibility(View.VISIBLE);
            confirmNewUsername.setVisibility(View.VISIBLE);
            changePassword.setVisibility(View.GONE);
            changeUsername.setVisibility(View.GONE);
            logOut.setVisibility(View.GONE);
            doneUsername.setVisibility(View.VISIBLE);
            donePassword.setVisibility(View.GONE);
            goBackPassword.setVisibility(View.GONE);
            goBackUsername.setVisibility(View.VISIBLE);


        }

        else if (logoutMode)
        {
            finish();

        }

    }

    // when the user clicks on the submit button (log in / sign up)
    public void submit_click()
    {
        EditText oldUsername = dialog.findViewById(R.id.oldUsername);
        String oldUsernameText = oldUsername.getText().toString();

        EditText newUsername = dialog.findViewById(R.id.newUsername);
        String newUsernameText = newUsername.getText().toString();

        EditText confirmNewUsername = dialog.findViewById(R.id.confirmNewUsername);
        String confirmNewUsernameText = confirmNewUsername.getText().toString();

        EditText oldPassword = dialog.findViewById(R.id.oldPassword);
        String oldPasswordText = oldPassword.getText().toString();

        EditText newPassword = dialog.findViewById(R.id.newPassword);
        String newPasswordText = newPassword.getText().toString();

        EditText confirmPassword = dialog.findViewById(R.id.confirmNewPassword);
        String confirmPasswordText = confirmPassword.getText().toString();

        /*if (profileMode)
        {

        }
        else
        {
            // if the username and email dont already exist
            if (oldPassword.getText().toString().equals(newPassword.getText().toString()))
            {
                Toast.makeText(ProfileActivity.this, "Please make sure your passwords match...", Toast.LENGTH_SHORT).show();
            }

        }*/

        if (changePasswordMode)
        {
            if(!isValid(oldPasswordText, "Please fill the old password field...") && !isValid(newPasswordText, "Please fill the new password field...") && !isValid(confirmPasswordText, "Please fill the confirm new password field...") )
            {
                User user = userManagerProfile.getUser(oldPasswordText);

                if (oldPasswordText.toString().equals(newPasswordText.toString()))
                {

                    ((App)this.getApplication()).getCurrentUser().setPassword(newPasswordText);
                     userManagerProfile.updateUser(((App)getApplication()).getCurrentUser());   //ERROR HERE
                }
                else {

                    Toast.makeText(ProfileActivity.this, "Please make sure your passwords match...", Toast.LENGTH_SHORT).show();

                }
                    /*try
                {
                    *//*userManagerProfile.validateUser(user);
                    userManagerProfile.addUser(user);
                    ((App)this.getApplication()).setCurrentUser(user);
                    Intent intent = new Intent(ProfileActivity.this, ViewActivity.class);
                    startActivity(intent);*//*
                }
                catch (IncorrectPasswordException e)
                {
                    Toast.makeText(ProfileActivity.this, "The given password is incorrect...", Toast.LENGTH_SHORT).show();
                }*/
            }
        }

        if (changeUsernameMode)
        {
            if(!isValid(oldUsernameText, "Please fill the old username field...") && !isValid(newUsernameText, "Please fill the new username field...") && !isValid(confirmNewUsernameText, "Please fill the confirm new username field...") )
            {
                User user = userManagerProfile.getUser(oldPasswordText);

                if (oldUsernameText.toString().equals(newUsernameText.toString()))
                {

                    ((App)this.getApplication()).getCurrentUser().setUsername(newUsernameText);
                    userManagerProfile.updateUser(((App)getApplication()).getCurrentUser());
                }
                else {

                    Toast.makeText(ProfileActivity.this, "Please make sure your passwords match...", Toast.LENGTH_SHORT).show();

                }

                /*try
                {
                    userManagerProfile.validateUser(user);
                    userManagerProfile.addUser(user);
                    ((App)this.getApplication()).setCurrentUser(user);
                    Intent intent = new Intent(ProfileActivity.this, ViewActivity.class);
                    startActivity(intent);
                }

                catch (IncorrectUsernameException e)
                {
                    Toast.makeText(ProfileActivity.this, "The given password is incorrect...", Toast.LENGTH_SHORT).show();
                }*/
            }
        }


    }

    // ********************************** dialog method ***************************************
    // shows the log in / sign up dialog (also sets on click listeners on the buttons)
    private void showDialog()
    {
        AlertDialog.Builder alert;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            alert = new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            alert = new AlertDialog.Builder(this);
        }

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_profile, null);

        alert.setView(view);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        Button changePassBtn = dialog.findViewById(R.id.changePassword);
        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               // changeMode_click();
                profileMode = false;
                changePasswordMode = true;
                changeMode_click();
                changePasswordMode = false;
                Button goBack = dialog.findViewById(R.id.goBackPassword);
                goBack.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Intent intentGobackPass = new Intent(ProfileActivity.this,
                                ProfileActivity.class);
                        startActivity(intentGobackPass);
                    }
                });


            }
        });

        Button changeUsrNameBtn = dialog.findViewById(R.id.changeUsername);
        changeUsrNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                profileMode = false;
                changeUsernameMode = true;
                changeMode_click();
                changeUsernameMode = false;
                Button goBack = dialog.findViewById(R.id.goBackUsername);
                goBack.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Intent intentGobackUser = new Intent(ProfileActivity.this,
                                ProfileActivity.class);
                        startActivity(intentGobackUser);
                    }
                });

               // submit_click();
            }
        });

        Button logoutBtn = dialog.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                profileMode = false;
                logoutMode = true;
                changeMode_click();
                logoutMode = false;
                Button goBack = dialog.findViewById(R.id.logout);
                goBack.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Intent intentLogout = new Intent(ProfileActivity.this,
                                LoginActivity.class);
                        startActivity(intentLogout);
                    }
                });

                // submit_click();
            }
        });



//function for username change, password change



    }


    private boolean isValid(String input, String message)
    {
        boolean retVal = false;

        if(input.isEmpty() || input.length() == 0 || input.equals("") || input == null)
        {
            Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
            retVal = true;
        }

        return retVal;
    }
    
}

// TODO:
//  1. Make sure all fields are populated in old,new and confirm pass&username----DONE
//  2. ENsure all inputs match username&pass old and new
//           oldUsername.equals( (App) getApplication().getCurrentUser()getUsername );
//              pass in a toast
//  3. next xhexk that the new and confirm new are both the same
//    IF CORRECT call Update from userManager
//       (App) getApplication().getCurrentUser()setUsername ;
//      userManagerProfileProfile.updateUser(getApplication().getCurrentUser;
//  4. Make sure the go back button in the change pass and change username activities
//          takes you back to the profile page ====Done
//  5. Make sure the logout page takes you back to the login page/startup of the app -----DONE
//  6. Set appropriate activity finishes in all the three modes= Profile, change username, change password ===DONE
