package com.example.foodmanager.models;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import interfaces.deleteInterface;

import android.content.DialogInterface;

import com.example.foodmanager.R;


public class DeleteDialogFragment extends DialogFragment {
    private deleteInterface.Datable datable;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        datable = (deleteInterface.Datable) context;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String product = getArguments().getString("selectedProduct");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Удаление")
                .setIcon(R.drawable.deletecross)
                .setMessage("Вы действительно хотите удалить \"" + product + "\" ?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datable.remove(product);
                    }
                })
                .setNegativeButton("Нет", null)
                .create();
    }
}
