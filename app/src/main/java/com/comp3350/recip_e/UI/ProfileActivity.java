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
    private AlertDialog dialog;
    private UserManager userManagerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = null;
        profileMode = true;
        changePasswordMode = false;
        changeUsernameMode = false;
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
            header.setText(getResources().getString(R.string.changePassword));
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
        }
        else if (changeUsernameMode)
        {

            header.setText(getResources().getString(R.string.changeUsername));
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

        User loggedUser = ((App)getApplication()).getCurrentUser();

        if (changePasswordMode)
        {
            if(!isValid(oldPasswordText, "Please fill the old password field...") && !isValid(newPasswordText, "Please fill the new password field...") && !isValid(confirmPasswordText, "Please fill the confirm new password field...") )
            {
                if (oldPasswordText.equals(loggedUser.getPassword())) // first need to check that the old password is indeed the correct password
                {
                    if (newPasswordText.equals(confirmPasswordText)) // then check that new password matches the confirm one
                    {
                        loggedUser.setPassword(newPasswordText);
                        userManagerProfile.updateUser(loggedUser);
                    }
                    else
                    {
                        Toast.makeText(ProfileActivity.this, "Please make sure your passwords match...", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ProfileActivity.this, "Please make sure your old password is the one on record...", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (changeUsernameMode)
        {
            if(!isValid(oldUsernameText, "Please fill the old username field...") && !isValid(newUsernameText, "Please fill the new username field...") && !isValid(confirmPasswordText, "Please fill the confirm new username field...") )
            {
                if (oldUsernameText.equals(loggedUser.getUsername())) // first need to check that the old username is indeed the correct password
                {
                    if (newUsernameText.equals(confirmNewUsernameText)) // then check that new username matches the confirm one
                    {
                        loggedUser.setUsername(newUsernameText);
                        userManagerProfile.updateUser(loggedUser);
                    }
                    else
                    {
                        Toast.makeText(ProfileActivity.this, "Please make sure your usernames match...", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ProfileActivity.this, "Please make sure your old username is the one on record...", Toast.LENGTH_SHORT).show();
                }
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
                profileMode = false;
                changePasswordMode = true;
                changeMode_click();
            }
        });

        Button donePassBtn = dialog.findViewById(R.id.donePassword);
        donePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                submit_click();
            }
        });

        Button passBackBtn = dialog.findViewById(R.id.goBackPassword);
        passBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                changePasswordMode = false;
                profileMode = true;
                changeMode_click();
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
            }
        });

        Button doneNameBtn = dialog.findViewById(R.id.doneUsername);
        doneNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                submit_click();
            }
        });

        Button nameBack = dialog.findViewById(R.id.goBackUsername);
        nameBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                changeUsernameMode = false;
                profileMode = true;
                changeMode_click();
            }
        });

        Button logoutBtn = dialog.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ((App)getApplication()).removeCurrentUser();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button backBtn = dialog.findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
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
