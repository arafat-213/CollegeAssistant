package com.wordpress.yourblogger.collegeassistant_v10;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ChatQuery chatQuery;
    EditText questionInput, answerInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        dbHelper = new DBHelper(this);
        questionInput = findViewById(R.id.questionInput);
        answerInput = findViewById(R.id.answerInput);
    }

    public void addQuery(View view) {
        chatQuery = new ChatQuery();
        String question = questionInput.getText().toString().trim();
        String answer = answerInput.getText().toString().trim();
        chatQuery.setmQuestion(question);
        chatQuery.setmAnswer(answer);
        dbHelper.addChatQuery(chatQuery);
        Toast.makeText(getApplicationContext(), "1 query added", Toast.LENGTH_SHORT).show();
    }

    public void logOut(View view) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", "empty");
        editor.putString("password", "empty");
        editor.commit();
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(intent);
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
