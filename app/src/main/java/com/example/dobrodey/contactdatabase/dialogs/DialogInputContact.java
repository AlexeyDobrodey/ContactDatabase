package com.example.dobrodey.contactdatabase.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.dobrodey.contactdatabase.R;
import com.example.dobrodey.contactdatabase.database.Contact;


public class DialogInputContact extends DialogFragment{

    private static final String ARGS_USER_ID = "user_id";
    public static final String EXTRA_CONTACT = "extra_contact";


    private EditText mInputFirstName, mInputLastName, mInputPhone, mInputEmail;
    private String mUserId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mUserId = getArguments().getString(ARGS_USER_ID);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_input_contact, null);
        mInputFirstName = (EditText) view.findViewById(R.id.inputFirstName);
        mInputLastName = (EditText) view.findViewById(R.id.inputLastName);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Add contact")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String firstName = mInputFirstName.getText().toString();
                        String lastName = mInputLastName.getText().toString();
                        Contact c = new Contact(firstName, lastName, mUserId);
                        sendResult(Activity.RESULT_OK, c);
                    }
                })
                .create();
    }

    public void sendResult(int resultCode, Contact c) {
        if(getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_CONTACT, c);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    public static DialogInputContact newInstance(String userId) {
        Bundle args = new Bundle();
        args.putString(ARGS_USER_ID, userId);

        DialogInputContact dialogInputContact = new DialogInputContact();
        dialogInputContact.setArguments(args);
        return dialogInputContact;
    }
}
