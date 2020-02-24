package com.example.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    ImageView ivAppTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ivAppTitle = findViewById(R.id.ivAppTitle);
        ivAppTitle.setImageResource(R.drawable.nav_logo_whiteout);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = etUsername.getText().toString();
                String pass = etPassword.getText().toString();
                loginUser(user, pass);
            }
        });
    }

    public void loginUser(String user, String pass){
        Log.i(TAG, "loginUser: Trying to log user in.");
        //Want this on teh background thread, not on the main thread or UI thread.
        //would prevent user from doing anything until the thread is done.
        ParseUser.logInInBackground(user, pass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "done: Problem with login: " + e.getMessage());
                    Log.e(TAG, "done: ParseException: " + e);
                    Log.e(TAG, "done: " + e.getCause());
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "There was a problem logging in. Please try again later.", Toast.LENGTH_LONG).show();
                    return;
                }
                launchMainActivity();
                // better user experience:
                // Don't want the appearance of being logged out when pressing back
                finish();
            }
        });

    }

    private void launchMainActivity() {
        Intent gotoMain = new Intent(this, MainActivity.class);
        startActivity(gotoMain);
    }
}
