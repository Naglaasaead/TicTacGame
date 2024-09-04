/*
package com.naglaa.tictacgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private Switch switchBackgroundMusic;
    private Switch switchClickSound;
    private Spinner languageSpinner;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button btnSaveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchBackgroundMusic = findViewById(R.id.switch_background_music);
        switchClickSound = findViewById(R.id.switch_click_sound);
        btnSaveSettings = findViewById(R.id.btn_save_settings);
        languageSpinner = findViewById(R.id.spinner_language);

        sharedPreferences = getSharedPreferences("GameSettings", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Load saved settings
        boolean isMusicOn = sharedPreferences.getBoolean("backgroundMusic", true);
        boolean isSoundOn = sharedPreferences.getBoolean("clickSound", true);
        switchBackgroundMusic.setChecked(isMusicOn);
        switchClickSound.setChecked(isSoundOn);

        setupLanguageSpinner();

        // Save settings when the save button is clicked
        // SettingsActivity.java - جزء التغيير في الكود
        btnSaveSettings.setOnClickListener(view -> {
            boolean isMusicChecked = switchBackgroundMusic.isChecked();
            boolean isSoundChecked = switchClickSound.isChecked();
            String selectedLanguage = (String) languageSpinner.getSelectedItem();
            editor.putBoolean("backgroundMusic", isMusicChecked);
            editor.putBoolean("clickSound", isSoundChecked);
            editor.putString("selectedLanguage", selectedLanguage);
            editor.apply();

            // إعادة تحميل الواجهة لتطبيق اللغة المختارة
            changeLanguage(getLanguageCodeFromPosition(languageSpinner.getSelectedItemPosition()));

            // التنقل إلى HomePage
            Intent intent = new Intent(SettingsActivity.this, HomePage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // إنهاء SettingsActivity
        });

    }

    private void setupLanguageSpinner() {
        // Load the languages_array from resources
        String[] languages = getResources().getStringArray(R.array.languages_array);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        languageSpinner.setAdapter(adapter);

        // Set the spinner selection based on saved language preference
        String savedLanguage = sharedPreferences.getString("selectedLanguage", "English");
        int position = adapter.getPosition(savedLanguage);
        languageSpinner.setSelection(position);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = (String) parent.getItemAtPosition(position);
                String languageCode = getLanguageCodeFromPosition(position);
                changeLanguage(languageCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle the case when no item is selected
            }
        });
    }

    private String getLanguageCodeFromPosition(int position) {
        final String[] languageCodes = {"en", "ar", "fr", "es", "de"}; // Corresponding language codes
        return languageCodes[position];
    }

    private void changeLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
*/


package com.naglaa.tictacgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private Switch switchBackgroundMusic;
    private Switch switchClickSound;
    private Spinner languageSpinner;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button btnSaveSettings;
    private MediaPlayer backgroundMusicPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchBackgroundMusic = findViewById(R.id.switch_background_music);
        switchClickSound = findViewById(R.id.switch_click_sound);
        btnSaveSettings = findViewById(R.id.btn_save_settings);
        languageSpinner = findViewById(R.id.spinner_language);

        sharedPreferences = getSharedPreferences("GameSettings", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Load saved settings
        boolean isMusicOn = sharedPreferences.getBoolean("backgroundMusic", true);
        boolean isSoundOn = sharedPreferences.getBoolean("clickSound", true);
        switchBackgroundMusic.setChecked(isMusicOn);
        switchClickSound.setChecked(isSoundOn);

        toggleBackgroundMusic(isMusicOn);

        setupLanguageSpinner();

        // Save settings when the save button is clicked
        btnSaveSettings.setOnClickListener(view -> {
            boolean isMusicChecked = switchBackgroundMusic.isChecked();
            boolean isSoundChecked = switchClickSound.isChecked();
            String selectedLanguage = (String) languageSpinner.getSelectedItem();
            editor.putBoolean("backgroundMusic", isMusicChecked);
            editor.putBoolean("clickSound", isSoundChecked);
            editor.putString("selectedLanguage", selectedLanguage);
            editor.apply();

            toggleBackgroundMusic(isMusicChecked);

            changeLanguage(getLanguageCodeFromPosition(languageSpinner.getSelectedItemPosition()));

            Intent intent = new Intent(SettingsActivity.this, HomePage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

    }

    private void setupLanguageSpinner() {
        // Load the languages_array from resources
        String[] languages = getResources().getStringArray(R.array.languages_array);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        languageSpinner.setAdapter(adapter);

        // Set the spinner selection based on saved language preference
        String savedLanguage = sharedPreferences.getString("selectedLanguage", "English");
        int position = adapter.getPosition(savedLanguage);
        languageSpinner.setSelection(position);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = (String) parent.getItemAtPosition(position);
                String languageCode = getLanguageCodeFromPosition(position);
                changeLanguage(languageCode);
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle the case when no item is selected
            }
        });
    }

    private String getLanguageCodeFromPosition(int position) {
        final String[] languageCodes = {"en", "ar", "fr", "es", "de"}; // Corresponding language codes
        return languageCodes[position];
    }

    private void changeLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void toggleBackgroundMusic(boolean isMusicOn) {
        if (isMusicOn) {
            if (backgroundMusicPlayer == null) {
                backgroundMusicPlayer = MediaPlayer.create(this, R.raw.background_music);
                backgroundMusicPlayer.setLooping(true);
            }
            if (!backgroundMusicPlayer.isPlaying()) {
                backgroundMusicPlayer.start();
            }
        } else {
            if (backgroundMusicPlayer != null && backgroundMusicPlayer.isPlaying()) {
                backgroundMusicPlayer.pause();
            }
        }
    }

}
