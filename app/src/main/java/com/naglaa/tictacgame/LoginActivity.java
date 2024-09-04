package com.naglaa.tictacgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.naglaa.tictacgame.DatabaseHelper;
import com.naglaa.tictacgame.HomePage;
import com.naglaa.tictacgame.R;
import com.naglaa.tictacgame.SignupActivity;

public class LoginActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private EditText loginEmail;
    private EditText loginPass;
    private Button btnLogin;
    private TextView tvSignup;
    private CircularProgressIndicator progressIndicator; // Add this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_pass);
        btnLogin = findViewById(R.id.btn_login);
        tvSignup = findViewById(R.id.tv_signup);
        progressIndicator = findViewById(R.id.circular_progress_indicator); // Initialize progress indicator

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emails = loginEmail.getText().toString();
                String passwords = loginPass.getText().toString();

                if (emails.equals("") || passwords.equals("")) {
                    showSnackbar(view, "All fields are mandatory");
                } else {
                    boolean checkCredentials = false;
                    try {
                        // Show progress indicator
                        progressIndicator.setVisibility(View.VISIBLE);

                        checkCredentials = databaseHelper.checkEmailPassword(emails, passwords);
                    } catch (Exception e) {
                        e.printStackTrace();
                        showSnackbar(view, "Error: " + e.getMessage());
                    } finally {
                        // Hide progress indicator
                        progressIndicator.setVisibility(View.GONE);
                    }

                    if (checkCredentials) {
                        showSnackbar(view, "Login Successfully");

                        // Delay for 2 seconds before navigating to HomePage
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getBaseContext(), HomePage.class);
                                startActivity(intent);
                                finish(); // Optional: close the LoginActivity
                            }
                        }, 1000); // 2000 milliseconds = 2 seconds
                    } else {
                        showSnackbar(view, "Login Failed");
                    }
                }
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        loginPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (loginPass.getRight() - loginPass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Toggle password visibility
                        if (loginPass.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                            loginPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            loginPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_red_eye_24, 0);
                        } else {
                            loginPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            loginPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_nearby_off_24, 0);
                        }
                        // Move the cursor to the end of the text
                        loginPass.setSelection(loginPass.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
