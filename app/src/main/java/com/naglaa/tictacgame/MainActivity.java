package com.naglaa.tictacgame;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WinDialog.RestartListener {

    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = new int[9];
    private int playerTurn = 1;
    private int totalSelectedBoxes = 0;
    private LinearLayout playerOneLayout, playerTwoLayout;
    private TextView playerOneName, playerTwoName;
    private ImageView[] imageViews = new ImageView[9];
    private String gameMode;
    private MediaPlayer mediaPlayer;
    private SharedPreferences sharedPreferences;
    private MediaPlayer clickSoundPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("GameSettings", MODE_PRIVATE);
        initializeMediaPlayers();
        initializeViews();
        setupCombinations();

        clickSoundPlayer = MediaPlayer.create(this, R.raw.main);

        String getPlayerOneName = getIntent().getStringExtra("Player One");
        String getPlayerTwoName = getIntent().getStringExtra("Player Two");
        gameMode = getIntent().getStringExtra("gameMode");

        playerOneName.setText(getPlayerOneName != null ? getPlayerOneName : getString(R.string.player_one));
        playerTwoName.setText(getPlayerTwoName != null ? getPlayerTwoName : getString(R.string.player_two));

        setOnClickListeners();
    }


    private void initializeMediaPlayers() {
        boolean isBackgroundMusicOn = sharedPreferences.getBoolean("backgroundMusic", true);

        if (isBackgroundMusicOn) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    private void initializeViews() {
        playerOneName = findViewById(R.id.ed_poneName);
        playerTwoName = findViewById(R.id.ed_poneTow);
        playerOneLayout = findViewById(R.id.playerOneLayout);
        playerTwoLayout = findViewById(R.id.playerTwoLayout);

        imageViews[0] = findViewById(R.id.image1);
        imageViews[1] = findViewById(R.id.image2);
        imageViews[2] = findViewById(R.id.image3);
        imageViews[3] = findViewById(R.id.image4);
        imageViews[4] = findViewById(R.id.image5);
        imageViews[5] = findViewById(R.id.image6);
        imageViews[6] = findViewById(R.id.image7);
        imageViews[7] = findViewById(R.id.image8);
        imageViews[8] = findViewById(R.id.image9);
    }

    private void setupCombinations() {
        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{2, 4, 6});
        combinationList.add(new int[]{0, 4, 8});
    }

    private void setOnClickListeners() {
        for (int i = 0; i < imageViews.length; i++) {
            final int position = i;
            imageViews[i].setOnClickListener(view -> handleBoxClick((ImageView) view, position));
        }
    }

    private void handleBoxClick(ImageView imageView, int boxPosition) {
        if (isSelectable(boxPosition)) {
            performAction(imageView, boxPosition);

            if (clickSoundPlayer != null) {
                clickSoundPlayer.start();
            }
        }
    }


    private void performAction(ImageView imageView, int selectBoxPosition) {
        boxPositions[selectBoxPosition] = playerTurn;
        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.close);
            if (checkWin()) {
                showWinDialog(getString(R.string.player_one_won, playerOneName.getText().toString()));
            } else if (totalSelectedBoxes == 8) {
                showWinDialog(getString(R.string.draw));
            } else {
                changePlayerTurn(2);
                totalSelectedBoxes++;
                if (gameMode != null && gameMode.equals("Computer")) {
                    computerMove();
                }
            }
        } else {
            imageView.setImageResource(R.drawable.o);
            if (checkWin()) {
                showWinDialog(getString(R.string.player_two_won, playerTwoName.getText().toString()));
            } else if (totalSelectedBoxes == 8) {
                showWinDialog(getString(R.string.draw));
            } else {
                changePlayerTurn(1);
                totalSelectedBoxes++;
            }
        }
    }

    private void computerMove() {
        List<Integer> emptyPositions = new ArrayList<>();
        for (int i = 0; i < boxPositions.length; i++) {
            if (boxPositions[i] == 0) {
                emptyPositions.add(i);
            }
        }

        if (!emptyPositions.isEmpty()) {
            int randomIndex = (int) (Math.random() * emptyPositions.size());
            int movePosition = emptyPositions.get(randomIndex);

            ImageView imageView = imageViews[movePosition];
            performAction(imageView, movePosition);
        }
    }

    private void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            playerOneLayout.setBackgroundResource(R.drawable.round_back_blue_border);
            playerTwoLayout.setBackgroundResource(R.drawable.round_back_dark_blue);
            // Removed Snackbar call
        } else {
            playerTwoLayout.setBackgroundResource(R.drawable.round_back_blue_border);
            playerOneLayout.setBackgroundResource(R.drawable.round_back_dark_blue);
            // Removed Snackbar call
        }
    }

    private boolean checkWin() {
        for (int[] combination : combinationList) {
            if (boxPositions[combination[0]] == playerTurn &&
                    boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) {
                return true;
            }
        }
        return false;
    }

    private boolean isSelectable(int boxPosition) {
        return boxPositions[boxPosition] == 0;
    }

    private void showWinDialog(String winnerMessage) {
        WinDialog winDialog = new WinDialog(this, winnerMessage, this);
        winDialog.show();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        restartMatch();
    }

    private void restartMatch() {
        boxPositions = new int[9];
        totalSelectedBoxes = 0;
        playerTurn = 1;
        changePlayerTurn(playerTurn);

        for (ImageView imageView : imageViews) {
            imageView.setImageResource(0);
        }

        if (sharedPreferences.getBoolean("backgroundMusic", true) && mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();  // Pause the music
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && sharedPreferences.getBoolean("backgroundMusic", true)) {
            mediaPlayer.start();  // Resume music if it is enabled
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();  // Stop the background music
            mediaPlayer.release();  // Release resources
            mediaPlayer = null;
        }
        if (clickSoundPlayer != null) {
            clickSoundPlayer.release();  // Release click sound resources
            clickSoundPlayer = null;
        }
    }

}
