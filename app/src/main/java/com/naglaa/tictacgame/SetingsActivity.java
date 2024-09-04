/*
package com.naglaa.tictacgame;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SetingsActivity extends AppCompatActivity {

    private Switch switchBackgroundMusic;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchBackgroundMusic = findViewById(R.id.switch_background_music);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnSave = findViewById(R.id.btn_save);

        sharedPreferences = getSharedPreferences("GameSettings", MODE_PRIVATE);

        // Load saved settings
        boolean isBackgroundMusicOn = sharedPreferences.getBoolean("backgroundMusic", true);
        switchBackgroundMusic.setChecked(isBackgroundMusicOn);

        btnSave.setOnClickListener(v -> {
            boolean isMusicOn = switchBackgroundMusic.isChecked();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("backgroundMusic", isMusicOn);
            editor.apply();
            finish(); // Close the activity
        });
    }
}
*/
