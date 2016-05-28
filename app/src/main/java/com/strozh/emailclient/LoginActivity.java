package com.strozh.emailclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.woods.emailclient.R;

public class LoginActivity extends AppCompatActivity {
    EditText editTextLogin;
    EditText editTextPassword;

    final String APP_PREFERENCES = "setting";
    final String LOGIN = "login";
    final String PASSWORD = "password";

    String USER = null;
    String PASS = null;

    Button buttonLogin;
    Button buttonLogout;

    SharedPreferences appSharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appSharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        editor = appSharedPreferences.edit();

        editTextLogin = (EditText) findViewById(R.id.edit_text_log_in);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);

        buttonLogin = (Button) findViewById(R.id.button_log_in);
        buttonLogout = (Button) findViewById(R.id.button_log_out);

        if (appSharedPreferences.contains(LOGIN) && appSharedPreferences.contains(PASSWORD)){
            editTextLogin.setText(appSharedPreferences.getString(LOGIN, ""));
            editTextPassword.setText(appSharedPreferences.getString(PASSWORD, ""));
        } else Toast.makeText(LoginActivity.this, "Enter login and password", Toast.LENGTH_SHORT).show();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString(LOGIN, editTextLogin.getText().toString().trim());
                editor.putString(PASSWORD, editTextPassword.getText().toString().trim());
                editor.apply();

                Toast.makeText(LoginActivity.this, "Save login...", Toast.LENGTH_SHORT).show();
                LoginActivity.this.finish();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();

                editTextLogin.setText("");
                editTextPassword.setText("");

                Toast.makeText(LoginActivity.this, "Delete login...", Toast.LENGTH_SHORT).show();
            }

        });


    }
}
