package com.naglaa.tictacgame;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class AddPlayers extends AppCompatActivity {
    private EditText PlayerOne, PlayerTwo;
    private Button btn_StartGame;
    private boolean isComputerPlaying = false;
    private boolean isBackgroundMusicOn = true;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        PlayerOne = findViewById(R.id.ed_pone);
        PlayerTwo = findViewById(R.id.ed_ptwo);
        btn_StartGame = findViewById(R.id.btn_stgame);

        sharedPreferences = getSharedPreferences("GameSettings", MODE_PRIVATE);
        loadPreferences(); // Load preferences on creation

        // Get the game mode from the intent
        String gameMode = getIntent().getStringExtra("gameMode");
        if (gameMode != null) {
            if ("PlayerVsComputer".equals(gameMode)) {
                PlayerTwo.setText(getString(R.string.computer)); // Set PlayerTwo to computer
                PlayerTwo.setEnabled(false);
                isComputerPlaying = true;
            } else if ("PlayerVsPlayer".equals(gameMode)) {
                PlayerTwo.setText("");
                PlayerTwo.setEnabled(true);
                isComputerPlaying = false;
            }
        } else {
            // Handle unexpected null value
            Snackbar.make(btn_StartGame, getString(R.string.enter_your_player), Snackbar.LENGTH_LONG).show();
        }

        btn_StartGame.setOnClickListener(view -> {
            final String getPlayerOneName = PlayerOne.getText().toString().trim();
            final String getPlayerTwoName = PlayerTwo.getText().toString().trim();

            if (getPlayerOneName.isEmpty()) {
                Snackbar.make(view, getString(R.string.enter_player_one_name), Snackbar.LENGTH_SHORT).show();
            } else if (!isComputerPlaying && getPlayerTwoName.isEmpty()) {
                Snackbar.make(view, getString(R.string.enter_player_two_name), Snackbar.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(AddPlayers.this, MainActivity.class);
                intent.putExtra("Player One", getPlayerOneName);
                intent.putExtra("Player Two", isComputerPlaying ? getString(R.string.computer) : getPlayerTwoName);
                intent.putExtra("isComputerPlaying", isComputerPlaying);
                intent.putExtra("gameMode", isComputerPlaying ? getString(R.string.computer) : getString(R.string.player));
                intent.putExtra("backgroundMusic", isBackgroundMusicOn);
                startActivity(intent);
            }
        });
    }

    private void loadPreferences() {
        isBackgroundMusicOn = sharedPreferences.getBoolean("backgroundMusic", true);
    }

    private void showSymbolDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);

        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroupSymbols);
        Button btnConfirm = dialog.findViewById(R.id.btn_confirm);
        Switch switchBackgroundMusic = dialog.findViewById(R.id.switch_background_music);

        switchBackgroundMusic.setChecked(isBackgroundMusicOn);

        // Update dialog UI components for the current language
        updateDialogUIForCurrentLanguage(dialog);

        btnConfirm.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Snackbar.make(v, getString(R.string.select_symbol), Snackbar.LENGTH_SHORT).show();
            } else {
                RadioButton selectedSymbol = dialog.findViewById(selectedId);
                String chosenSymbol = selectedSymbol.getText().toString();
                isBackgroundMusicOn = switchBackgroundMusic.isChecked();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("backgroundMusic", isBackgroundMusicOn);
                editor.apply();

                Snackbar.make(v, getString(R.string.you_chose) + ": " + chosenSymbol, Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void updateDialogUIForCurrentLanguage(Dialog dialog) {
        TextView dialogTitle = dialog.findViewById(R.id.messageText);
        Button btnConfirm = dialog.findViewById(R.id.btn_confirm);

        dialogTitle.setText(getString(R.string.select_symbol));
        btnConfirm.setText(getString(R.string.confirm));
    }
}
