package com.naglaa.tictacgame;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText signupEmail;
    private EditText signupPass;
    private EditText signupConfirmPass;
    private Button btnSignup;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        databaseHelper = new DatabaseHelper(this);

        signupEmail = findViewById(R.id.signup_email);
        signupPass = findViewById(R.id.signup_pass);
        signupConfirmPass = findViewById(R.id.signup_confirmpass);
        btnSignup = findViewById(R.id.btn_signup);
        tvLogin = findViewById(R.id.tv_login);

        // Set up eye icon click listeners
        setEyeIconClickListener(signupPass);
        setEyeIconClickListener(signupConfirmPass);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signupEmail.getText().toString();
                String password = signupPass.getText().toString();
                String confirmPassword = signupConfirmPass.getText().toString();

                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getBaseContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        boolean checkUserEmail = databaseHelper.checkEmail(email);
                        if (!checkUserEmail) {
                            boolean insert = databaseHelper.insertData(email, password);
                            if (insert) {
                                Toast.makeText(getBaseContext(), "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                startActivity(intent);
                                finish(); // Optional: Close the SignupActivity
                            } else {
                                Toast.makeText(getBaseContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "User already exists, Please Login", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setEyeIconClickListener(final EditText editText) {
        Drawable eyeIcon = getResources().getDrawable(R.drawable.baseline_nearby_off_24);
        Drawable eyeOpenIcon = getResources().getDrawable(R.drawable.baseline_remove_red_eye_24);

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int right = (int) (editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width());
                    if (event.getRawX() >= right) {
                        if (editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
                            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeOpenIcon, null);
                        } else {
                            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeIcon, null);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
