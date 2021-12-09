package com.danielme.dialogfragmentform;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;


public class DialogueConView extends AppCompatDialogFragment {

    public static final String TAG = DialogueConView.class.getSimpleName();

    private static final String ARG_FIRSTNAME = "ARG_FIRSTNAME";
    private static final String ARG_LASTNAME = "ARG_LASTNAME";

    private TextInputLayout textInputLayoutFirstName;
    private EditText textInputFirstName;
    private EditText textInputLastName;
    private DialogueConView listener;

    public static DialogueConView newInstance(String firstName, String lastName) {
        Bundle args = new Bundle();
        args.putString(ARG_FIRSTNAME, firstName);
        args.putString(ARG_LASTNAME, lastName);

        DialogueConView frag = new DialogueConView();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FormDialogListener) {
            listener = (DialogueConView) context;
        } else {
            throw new IllegalArgumentException("context is not FormDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.fragment_form, null);

        setupContent(content);

        AlertDialog alertDialog = new MaterialAlertDialogBuilder(getContext())
                .setView(content)
                .setCancelable(true)
                .setNegativeButton(getString(R.string.cancel), null)
                .setTitle(R.string.edit)
                .setPositiveButton(getString(R.string.save), (dialogInterface, i) -> returnValues())
                .create();

        //asegura que se muestre el teclado con el diálogo completo
        alertDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return alertDialog;
    }

    private void returnValues() {
        listener.update(textInputFirstName.getText().toString(),
                textInputLastName.getText().toString());
    }

    private void setupContent(View content) {
        textInputLayoutFirstName = content.findViewById(R.id.textInputLayoutFirstName);
        textInputFirstName = content.findViewById(R.id.textInputFirstName);
        textInputLastName = content.findViewById(R.id.textInputLastName);
        textInputFirstName.setText(getArguments().getString(ARG_FIRSTNAME));
        textInputFirstName.setSelection(getArguments().getString(ARG_FIRSTNAME).length());
        textInputLastName.setText(getArguments().getString(ARG_LASTNAME));
        textInputLastName.setSelection(getArguments().getString(ARG_LASTNAME).length());

        textInputLastName.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                returnValues();
                dismiss();
                return true;
            }
            return false;
        });
    }

}