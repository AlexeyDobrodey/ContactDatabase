package com.example.dobrodey.contactdatabase.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.dobrodey.contactdatabase.R;
import com.example.dobrodey.contactdatabase.models.ValidationEmail;
import com.example.dobrodey.contactdatabase.models.Validation;
import com.example.dobrodey.contactdatabase.models.ValidationPhone;


public class DialogInputData extends DialogFragment {

    public static final String ARGS_TITLE = "extar_title";
    public static final String ARGS_HINT = "extar_hint";

    public static final String EXTRA_DATA = "extra_data";
    private int mIdTitle;
    private int mIdHint;
    private EditText mInputData;

    private Validation mValidation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mIdTitle = getArguments().getInt(ARGS_TITLE);
            mIdHint  = getArguments().getInt(ARGS_HINT);
        }

        if(mIdTitle == R.string.dialog_title_phone) {
            mValidation = new ValidationPhone();
        }else{
            mValidation = new ValidationEmail();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_input_data, null);
        mInputData = (EditText) view.findViewById(R.id.inpuData);

        final TextInputLayout txtInputLayout = (TextInputLayout) view.findViewById(R.id.inputDataTextLayout);
        txtInputLayout.setHint(getResources().getString(mIdHint));
        txtInputLayout.setError("Not correct");
        mInputData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mValidation.validate(s.toString())) {
                    txtInputLayout.setErrorEnabled(false);
                }else{
                    txtInputLayout.setErrorEnabled(true);
                }
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setTitle(mIdTitle)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int result = txtInputLayout.isErrorEnabled() ? Activity.RESULT_CANCELED : Activity.RESULT_OK;
                        sendResult(result, mInputData.getText().toString());
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, String data) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATA, data);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }


    public static DialogInputData newInstance(int idTitle, int idInputHint) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_TITLE, idTitle);
        bundle.putInt(ARGS_HINT, idInputHint);

        DialogInputData dialogInputData = new DialogInputData();
        dialogInputData.setArguments(bundle);
        return dialogInputData;
    }
}
