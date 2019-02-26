package com.android.lucid.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

/**
 * Created by Arash
 */

public class BaseView extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static final int RC_SIGN_IN = 1;
    public static final int REQUEST_AUTH = 2;

    protected ProgressDialog modalLoading;

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    public void showAlert(String message, DialogInterface.OnClickListener onPositiveClickListener) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes", onPositiveClickListener);
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public AlertDialog showAlert(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
        return alert11;
    }

    public void showLoading() {
        modalLoading = new ProgressDialog(this);
        modalLoading.setCancelable(false);
        modalLoading.setMessage("Please waite" );
        modalLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        modalLoading.show();
    }

    public void hideLoading() {
        if (modalLoading != null && modalLoading.isShowing())
            modalLoading.dismiss();
    }

}
