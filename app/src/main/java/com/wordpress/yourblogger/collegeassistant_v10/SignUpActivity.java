package com.wordpress.yourblogger.collegeassistant_v10;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    DBHelper dbHelper;
    String[] semesters = {"1", "2", "3", "4", "5", "6", "7", "8"};
    String[] departments = {"Computer Engineering", "Civil Engineering", "Electrical Engineering"};
    private Spinner spinnerDepartment;
    private Spinner spinnerSemester;
    private String selectedDepartment;
    private String selectedSemester = "0";
    private EditText studentIdInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        studentIdInput = findViewById(R.id.StudentIdInput);
        passwordInput = findViewById(R.id.PasswordInput);
        dbHelper = new DBHelper(this);
        setupSpinners();
        //spinnerSemester.setOnItemClickListener();


        /**User user = new User();
         user.setUname("arfee");
         user.setPass("arfee");
         dbHelper = new DBHelper(this);
         dbHelper.addUser(user);
         Toast.makeText(this,user.getUname()+" added" , Toast.LENGTH_LONG).show();**/


    }


    public void signUp(View view) {
        User user = new User();
        String s_id = studentIdInput.getText().toString();
        String pass = passwordInput.getText().toString();
        boolean errorFree = checkErrors(s_id, pass);
        /*if(studentIdInput.getText().toString() == null){

            return;
        }
        if(passwordInput.getText().toString() == null){

            return;
        }*/
        if (errorFree) {
            user.setUname(s_id);
            user.setPass(pass);
            user.setDepartment(selectedDepartment);
            user.setSemester(selectedSemester);
            dbHelper.addUser(user);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Sign up complete");
            alertDialogBuilder.setTitle("Congratulations!");
            alertDialogBuilder.setPositiveButton("Login now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            Toast.makeText(getApplicationContext(), "Sign up failed", Toast.LENGTH_LONG).show();
        }

    }

    public boolean checkErrors(String s_id, String pass) {
        if (s_id.equals("")) {
            studentIdInput.setError("Student  ID can not be empty");
            return false;
        }
        if (pass.equals("")) {
            passwordInput.setError("Password can not be empty");
            return false;
        }
        return true;
    }

    public void setupSpinners() {
        ArrayAdapter departmentArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, departments);
        departmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spinnerDepartment.setAdapter(departmentArrayAdapter);
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDepartment = departments[i];
                //Toast.makeText(getApplicationContext(), selectedDepartment, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedDepartment = "No department selected";
            }
        });


        ArrayAdapter semesterArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, semesters);
        semesterArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSemester.setAdapter(semesterArrayAdapter);
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSemester = semesters[i];
                //Toast.makeText(getApplicationContext(),selectedSemester,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
