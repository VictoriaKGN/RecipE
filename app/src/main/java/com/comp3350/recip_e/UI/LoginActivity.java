package com.comp3350.recip_e.UI;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.comp3350.recip_e.R;
import com.comp3350.recip_e.logic.UserManager;
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

public class LoginActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> activityLauncher;
    private boolean signInMode;
    private AlertDialog dialog;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = null;
        signInMode = true;
        userManager = new UserManager();

        showDialog();
    }

    // ********************************** click methods ***************************************
    // when the user clicks on the change mode button (or, log in / or, sign up)
    public void changeMode_click()
    {
        // change all the fields
        TextView header = dialog.findViewById(R.id.header);
        Button submitBtn = dialog.findViewById(R.id.submit_btn);
        Button viewBtn = dialog.findViewById(R.id.changeMode_btn);
        EditText confPass = dialog.findViewById(R.id.confirm_password);
        EditText email = dialog.findViewById(R.id.email);

        if (signInMode)
        {
            header.setText(getResources().getString(R.string.signup));
            submitBtn.setText(getResources().getString(R.string.signup));
            viewBtn.setText(getResources().getString(R.string.or_login));
            confPass.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
        }
        else
        {
            header.setText(getResources().getString(R.string.login));
            submitBtn.setText(getResources().getString(R.string.login));
            viewBtn.setText(getResources().getString(R.string.or_signup));
            confPass.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
        }

        signInMode = !signInMode;
    }

    // when the user clicks on the submit button (log in / sign up)
    public void submit_click()
    {
        EditText username = dialog.findViewById(R.id.username);
        EditText password = dialog.findViewById(R.id.password);
        EditText confPass = dialog.findViewById(R.id.confirm_password);
        EditText email = dialog.findViewById(R.id.email);

        if (signInMode)
        {
            // TODO: confirm username exists and password matches
            // if username exists
            // get user using the username
            // check if passwords match
            // start an intent

        }
        else
        {
            // if the username and email dont already exist
            if (password.getText().toString().equals(confPass.getText().toString()))
            {
                User newUser = new User(email.getText().toString(), username.getText().toString(), password.getText().toString());
                userManager.addUser(newUser);

                dialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, ViewActivity.class);
                // TODO: pass in the user info
                startActivity(intent);
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Please make sure your passwords match...", Toast.LENGTH_SHORT).show();
            }
            //else if (just username already exists)
            Toast.makeText(LoginActivity.this, "Sorry, that username already exists...", Toast.LENGTH_SHORT).show();
            //else
            Toast.makeText(LoginActivity.this, "An account with given email already exists...", Toast.LENGTH_SHORT).show();
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
        View view = inflater.inflate(R.layout.activity_login, null);

        alert.setView(view);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Button viewBtn = dialog.findViewById(R.id.changeMode_btn);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                changeMode_click();
            }
        });

        Button submitBtn = dialog.findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                submit_click();
            }
        });
    }
}