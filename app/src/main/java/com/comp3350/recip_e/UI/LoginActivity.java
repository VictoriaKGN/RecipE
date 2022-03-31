package com.comp3350.recip_e.UI;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.comp3350.recip_e.R;
import com.comp3350.recip_e.application.App;
import com.comp3350.recip_e.logic.UserManager;
import com.comp3350.recip_e.logic.exceptions.IncorrectPasswordException;
import com.comp3350.recip_e.logic.exceptions.EmailDoesNotExistException;
import com.comp3350.recip_e.objects.User;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private boolean signInMode;
    private AlertDialog dialog;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base);

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
        EditText username = dialog.findViewById(R.id.username);

        if (signInMode)
        {
            header.setText(getResources().getString(R.string.signup));
            submitBtn.setText(getResources().getString(R.string.signup));
            viewBtn.setText(getResources().getString(R.string.or_login));
            confPass.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
        }
        else
        {
            header.setText(getResources().getString(R.string.login));
            submitBtn.setText(getResources().getString(R.string.login));
            viewBtn.setText(getResources().getString(R.string.or_signup));
            confPass.setVisibility(View.GONE);
            username.setVisibility(View.GONE);
        }

        signInMode = !signInMode;
    }

    // when the user clicks on the submit button (log in / sign up)
    public void submit_click()
    {
        EditText username = dialog.findViewById(R.id.username);
        String usernameText = username.getText().toString();

        EditText password = dialog.findViewById(R.id.password);
        String passwordText = password.getText().toString();

        EditText confPass = dialog.findViewById(R.id.confirm_password);
        String confText = confPass.getText().toString();

        EditText email = dialog.findViewById(R.id.email);
        String emailText = email.getText().toString();

        if (signInMode)
        {
            if(isValid(emailText, "Please fill the email field...") && isValid(passwordText, "Please fill the password field..."))
            {
                User user = new User(emailText, usernameText, passwordText);

                try
                {
                    userManager.validateUser(user);
                    userManager.addUser(user);
                    ((App)this.getApplication()).setCurrentUser(user);
                    Intent intent = new Intent(LoginActivity.this, ViewActivity.class);
                    startActivity(intent);
                }
                catch (EmailDoesNotExistException e)
                {
                    Toast.makeText(LoginActivity.this, "The given email does not exist...", Toast.LENGTH_SHORT).show();
                }
                catch (IncorrectPasswordException e)
                {
                    Toast.makeText(LoginActivity.this, "The given password is incorrect...", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            if (isValid(emailText, "Please fill the email field...") && isValid(usernameText, "Please fill the username field")
                    && isValid(passwordText, "Please fill the password field") && isValid(confText, "Please fill the confirmation password field"))
            {
                if (!userManager.usernameExists(usernameText) && !userManager.emailExists(emailText))
                {
                    if (passwordText.equals(confText))
                    {
                        User newUser = new User(emailText, usernameText, passwordText);
                        userManager.addUser(newUser);

                        dialog.dismiss();
                        ((App) this.getApplication()).setCurrentUser(newUser);
                        Intent intent = new Intent(LoginActivity.this, ViewActivity.class);
                        startActivity(intent);
                    } else
                    {
                        Toast.makeText(LoginActivity.this, "Please make sure your passwords match...", Toast.LENGTH_SHORT).show();
                    }
                } else if (userManager.usernameExists(usernameText)) {
                    Toast.makeText(LoginActivity.this, "The given username already exists...", Toast.LENGTH_SHORT).show();
                } else // userManager.emailExists(emailText)
                {
                    Toast.makeText(LoginActivity.this, "An account with given email already exists...", Toast.LENGTH_SHORT).show();
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

    private boolean isValid(String input, String message)
    {
        boolean retVal = true;

        if(input.isEmpty() || input.length() == 0 || input.equals("") || input == null)
        {
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            retVal = false;
        }

        return retVal;
    }
}
