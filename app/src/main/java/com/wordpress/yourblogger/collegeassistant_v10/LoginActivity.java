package com.wordpress.yourblogger.collegeassistant_v10;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextToSpeech t1;
    CheckBox keepMeCheckBox;
    private Button LoginButton;
    private EditText StudentIdInput, PasswordInput;
    private Intent i;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_login);
        LoginButton = findViewById(R.id.LogInButton);
        StudentIdInput = findViewById(R.id.StudentIdInput);
        PasswordInput = findViewById(R.id.PasswordInput);
        keepMeCheckBox = findViewById(R.id.keepMeCheckBox);
        dbHelper = new DBHelper(this);
        //  TextView displayLabel = findViewById(R.id.displayLabel);
        // displayLabel.setText(dbHelper.displayAll());
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });
        checkPreferences();
    }

    public void loginBtnClick(View view) {
        username = StudentIdInput.getText().toString().trim();
        password = PasswordInput.getText().toString().trim();
        checkAdmin();
        authenticate();
    }

    public void authenticate() {
        // Returns the password for the entered username from the table
        String correctPass = dbHelper.searchPass(username);

        if (correctPass.equals(password)) {
            i = new Intent(LoginActivity.this, ProfileActivity.class);
            //i.putExtra("USERNAME" ,username );
            startActivity(i);
            t1.speak("Welcome home " + username + ". I'm your college assistant. You can ask me anything you want and I will help you as much as I can. How can I help you?", TextToSpeech.QUEUE_FLUSH, null);
            //t1.speak("Welcome home "+username, TextToSpeech.QUEUE_FLUSH, )
            Toast.makeText(getApplicationContext(), "Hello " + username, Toast.LENGTH_LONG).show();
            if (keepMeCheckBox.isChecked()) {
                storePreferences();
            }
        } else {
            showInvalidDialog();
        }
    }

    public void checkAdmin() {
        if (username.equalsIgnoreCase("admin") && password.equals("admin")) {
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            return;
        }
    }

    public void checkPreferences() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String username = preferences.getString("username", "empty");
        if (!username.equals("empty")) {
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Welcome " + username, Toast.LENGTH_LONG).show();
        }
    }

    public void storePreferences() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    public void signUp(View view) {
        i = new Intent(LoginActivity.this, SignUpActivity.class);
        /*User user = new User();
        user.setUname("arfeee");
        user.setPass("arfeee");
        user.setSemester(6);
        user.setDepartment("Computer");
        dbHelper.addUser(user);*/
        startActivity(i);
    }

    public void showInvalidDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Invalid username/password");
        alertDialogBuilder.setTitle("Oops!!");
        alertDialogBuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StudentIdInput.setText("");
                PasswordInput.setText("");
                    /*Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);*/
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Toast.makeText(getApplicationContext(), "Invalid username/password", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
        //super.onBackPressed();
    }
}