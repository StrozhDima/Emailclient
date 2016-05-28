package com.strozh.emailclient;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.woods.emailclient.R;

public class LoginActivity extends AppCompatActivity {
    EditText editTextLogin;
    EditText editTextPassword;
    EditText editTextServer;
    EditText editTextPort;

    final String LOGIN = "login";
    final String PASSWORD = "password";
    final String SERVER = "server";
    final String PORT = "port";
    final String LOGIN_FLAG = "loginFlag";

    Button buttonLogin;
    Button buttonLogout;

    SharedPreferences appSharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = appSharedPreferences.edit();

        editTextLogin = (EditText) findViewById(R.id.edit_text_log_in);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);
        editTextServer = (EditText) findViewById(R.id.edit_text_server);
        editTextPort = (EditText) findViewById(R.id.edit_text_port);

        buttonLogin = (Button) findViewById(R.id.button_log_in);
        buttonLogout = (Button) findViewById(R.id.button_log_out);

        if (this.isUserLoggedIn()){
            editTextLogin.setText(appSharedPreferences.getString(LOGIN, ""));
            editTextPassword.setText(appSharedPreferences.getString(PASSWORD, ""));
            editTextServer.setText(appSharedPreferences.getString(SERVER, ""));
            editTextPort.setText(appSharedPreferences.getString(PORT, ""));
        } else Toast.makeText(LoginActivity.this, R.string.toast_pls_login, Toast.LENGTH_SHORT).show();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString(LOGIN, editTextLogin.getText().toString().trim());
                editor.putString(PASSWORD, editTextPassword.getText().toString().trim());
                editor.putString(SERVER, editTextServer.getText().toString().trim());
                editor.putString(PORT, editTextPort.getText().toString().trim());
                editor.putBoolean(LOGIN_FLAG, true);
                editor.apply();

                Toast.makeText(LoginActivity.this, R.string.toast_save_settings, Toast.LENGTH_SHORT).show();
                LoginActivity.this.finish();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();

                editTextLogin.setText("");
                editTextPassword.setText("");
                editTextServer.setText("");
                editTextPort.setText("");

                Toast.makeText(LoginActivity.this, R.string.toast_delete_settings, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Check for login
    public boolean isUserLoggedIn(){
        return appSharedPreferences.getBoolean(LOGIN_FLAG, false);
    }
}
