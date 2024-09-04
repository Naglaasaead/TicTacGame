package com.naglaa.tictacgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    private Button btnPlayerVsPlayer;
    private Button btnPlayerVsComputer;
    private Button btnSaveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnPlayerVsPlayer = findViewById(R.id.btn_player_vs_player);
        btnPlayerVsComputer = findViewById(R.id.btn_player_vs_computer);
        btnSaveSettings = findViewById(R.id.btn_save_settings);

        btnPlayerVsPlayer.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, AddPlayers.class);
            intent.putExtra("gameMode", "PlayerVsPlayer");
            startActivity(intent);
        });

        btnPlayerVsComputer.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, AddPlayers.class);
            intent.putExtra("gameMode", "PlayerVsComputer");
            startActivity(intent);
        });

        btnSaveSettings.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
