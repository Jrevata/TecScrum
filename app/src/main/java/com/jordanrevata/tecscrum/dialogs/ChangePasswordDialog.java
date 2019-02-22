package com.jordanrevata.tecscrum.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;

import com.jordanrevata.tecscrum.R;

public class ChangePasswordDialog extends DialogFragment {

    private EditText editText_old_password;
    private EditText editText_new_password;
    private EditText editText_confirm_password;
    private Button button_changePassword;
    private Button button_cancel_changePassword;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_change_password, null));
                // Add action buttons

        return builder.create();
    }

}
