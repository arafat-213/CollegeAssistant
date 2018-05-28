package com.wordpress.yourblogger.collegeassistant_v10;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextToSpeech textToSpeech;
    EditText queryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //String msg = getIntent().getStringExtra("USERNAME");
        queryInput = findViewById(R.id.queryInput);
        //Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
        dbHelper = new DBHelper(this);
    }

    public void sendQuery(View view) {
        String userQuery = queryInput.getText().toString();
        String response = dbHelper.askQuestion(userQuery);
        textToSpeech.speak(response, TextToSpeech.QUEUE_FLUSH, null);
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }


    public void logOut(View view) {
        clearPreferences();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    public void clearPreferences() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", "empty");
        editor.putString("password", "empty");
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to go back?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearPreferences();
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
        //super.onBackPressed();
    }
}