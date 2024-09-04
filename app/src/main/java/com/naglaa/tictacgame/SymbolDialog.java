/*
package com.naglaa.tictacgame;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SymbolDialog extends AppCompatDialogFragment {

    private RadioGroup radioGroupSymbols;
    private Switch switchBackgroundMusic;
    private Button btnConfirm;
    private DialogInterface.OnDismissListener onDismissListener;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create the dialog and inflate the custom layout
        Dialog dialog = new Dialog(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_layout, null);

        dialog.setContentView(view);
        dialog.setTitle("Select Symbol");

        // Initialize views
        radioGroupSymbols = view.findViewById(R.id.radioGroupSymbols);
        switchBackgroundMusic = view.findViewById(R.id.switch_background_music);
        btnConfirm = view.findViewById(R.id.btn_confirm);

        // Handle dialog arguments
        Bundle args = getArguments();
        if (args != null) {
            String gameMode = args.getString("gameMode");
            // Use gameMode if needed
        }

        // Set confirm button click listener
        btnConfirm.setOnClickListener(v -> {
            int selectedId = radioGroupSymbols.getCheckedRadioButtonId();
            RadioButton selectedButton = view.findViewById(selectedId);

            String selectedSymbol = selectedButton != null ? selectedButton.getText().toString() : null;
            boolean isMusicOn = switchBackgroundMusic.isChecked();

            // Pass the selected symbol and background music preference back to the activity
            if (getActivity() instanceof AddPlayers) {
                AddPlayers targetActivity = (AddPlayers) getActivity();
                targetActivity.onSymbolSelected(selectedSymbol, isMusicOn);
            }
            dismiss();
        });

        dialog.setOnDismissListener(dialogInterface -> {
            if (onDismissListener != null) {
                onDismissListener.onDismiss(dialogInterface);
            }
        });

        return dialog;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.onDismissListener = listener;
    }
}
*/
