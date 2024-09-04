package com.naglaa.tictacgame;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class WinDialog extends Dialog {

    private final String message;
    private final RestartListener restartListener;

    public interface RestartListener {
        void onRestart();
    }

    public WinDialog(@NonNull Context context, String message, RestartListener restartListener) {
        super(context);
        this.message = message;
        this.restartListener = restartListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog_layout);

        TextView messageText = findViewById(R.id.messageText);
        Button btnAgain = findViewById(R.id.btn_stagain);

        // Set the message
        messageText.setText(message);

        // Set up the button listener
        btnAgain.setOnClickListener(view -> {
            if (restartListener != null) {
                restartListener.onRestart();
            }
            dismiss();
        });
    }
}
