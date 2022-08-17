package com.example.kakjayadi_warkopsharelok.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.example.kakjayadi_warkopsharelok.R;

public class LoadingAlertDialog {
    AlertDialog dialog;
    public void alertDialogLoading(Context context){
        View view = View.inflate(context, R.layout.alert_dialog_loading, null);

        AlertDialog.Builder alertDialogLoadingBuilder = new AlertDialog.Builder(context);
        alertDialogLoadingBuilder.setView(view);

        dialog = alertDialogLoadingBuilder.create();
        dialog.setCancelable(false);
        dialog.show();

    }
    public void alertDialogCancel(){
        dialog.dismiss();
    }
}
