package com.magiccodeinc.porcencalbeta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Crea una instancia del constructor de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Configura el mensaje que deseas mostrar en el diálogo
        builder.setMessage(getString(R.string.mensaje));

        // Agrega un botón "Aceptar" al diálogo para cerrarlo
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss(); // Cierra el diálogo
            }
        });

        // Crea el diálogo y lo devuelve
        return builder.create();
    }
}



