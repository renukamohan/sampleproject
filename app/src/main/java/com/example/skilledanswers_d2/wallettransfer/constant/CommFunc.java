package com.example.skilledanswers_d2.wallettransfer.constant;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;

import com.example.skilledanswers_d2.wallettransfer.MainActivity;
import com.example.skilledanswers_d2.wallettransfer.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SkilledAnswers-D1 on 04-01-2017.
 */

public class CommFunc {

    public static boolean Smspermissioncheck(Context  context) {
        //Call whatever you want
        return ContextCompat
                .checkSelfPermission(context,
                        android.Manifest.permission.READ_SMS) +
                ContextCompat
                        .checkSelfPermission(context,
                                android.Manifest.permission.RECEIVE_SMS)

                ==
                PackageManager.PERMISSION_GRANTED;
    }
    public static void reqForSmsPermisssion(final Context context, final Activity activity)
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (activity, android.Manifest.permission.READ_SMS)
                && ActivityCompat.shouldShowRequestPermissionRationale
                (activity, android.Manifest.permission.RECEIVE_SMS)) {
            Snackbar.make(activity.findViewById(android.R.id.content), "We need SMS permission for Auto OTP verification",
                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            context.startActivity(intent);
                        }
                    }).show();
        } else {

            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission
                            .READ_PHONE_STATE, android.Manifest.permission.READ_SMS,
                            android.Manifest.permission.RECEIVE_SMS
                    },
                    1);

        }
    }


    public static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        //   Log.e("url", result.toString());
        return result.toString();
    }


    public static void NoInternetPrompt(final Context context)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setIcon(R.drawable.no_internet);
        dialog.setMessage("Seems like Internet not available or Not enabled");
        dialog.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(Settings.ACTION_SETTINGS);
                context.startActivity(myIntent);
                //get gps
            }
        });
        dialog.show();
    }
    public static void goToSignupCommFunc(Context context)
    {
        SharedPreferences preferencesSession=context
                .getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        SharedPreferences.Editor editorSession=preferencesSession.edit();
        editorSession.putString(ALLCONSTANTS.SESSION_KEY_FNAME1,null);
        editorSession.putString(ALLCONSTANTS.SESSION_KEY_LNAME1,null);
        editorSession.putString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null);
        editorSession.putString(ALLCONSTANTS.SESSION_KEY_WALLET1,null);
        editorSession.putString(ALLCONSTANTS.SESSION_KEY_MOBILE1,null);
        editorSession.putString(ALLCONSTANTS.SESSION_KEY_EMAIL1,null);
        editorSession.apply();
        Intent intent=new Intent(context,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void commonDialog(Context context,String title,String subjuct)
    {
        final Dialog dialog=new Dialog(context,R.style.comm_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.common_dialog);
        AppCompatTextView textViewTitle= (AppCompatTextView) dialog.findViewById(R.id.commom_dialog_title_id);
        AppCompatTextView textViewSubjuct= (AppCompatTextView) dialog.findViewById(R.id.commom_dialog_subjuct_id);
        AppCompatButton buttonSubmit= (AppCompatButton) dialog.findViewById(R.id.commom_dialog_button_id);
        textViewTitle.setText(title);
        textViewSubjuct.setText(subjuct);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public static void goToSignupCommFuncPrompt(final Context context)
    {

        final Dialog dialog=new Dialog(context,R.style.comm_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.common_dialog);
        AppCompatTextView  textViewTitle= (AppCompatTextView) dialog.findViewById(R.id.commom_dialog_title_id);
        AppCompatTextView textViewSubjuct= (AppCompatTextView) dialog.findViewById(R.id.commom_dialog_subjuct_id);
        AppCompatButton buttonSubmit= (AppCompatButton) dialog.findViewById(R.id.commom_dialog_button_id);
        textViewTitle.setText("Multi-Session Error");
        textViewSubjuct.setText("Your account is open in multiple devices.. Please Login again");
        buttonSubmit.setText("Login");
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                goToSignupCommFunc(context);
            }
        });
        dialog.show();
    }
    public static boolean CameraCheckPermission(Context context)
    {
        return ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
}
