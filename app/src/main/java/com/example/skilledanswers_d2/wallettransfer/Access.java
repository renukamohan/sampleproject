package com.example.skilledanswers_d2.wallettransfer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;


public class Access extends Activity {

    Button button;
    private static final int REQUEST_PERMISSIONS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access);

        if (Access.Smspermissioncheck(Access.this)) {
            Access.reqForSmsPermisssion(Access.this, Access.this);
        } else {
            button = (Button) findViewById(R.id.getstarted);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Verify.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim, R.anim.right);
                }
            });

        }

    }

    public static boolean Smspermissioncheck(Context context) {
        //Call whatever you want
        return ContextCompat
                .checkSelfPermission(context,
                        android.Manifest.permission.READ_SMS) +
                ContextCompat
                        .checkSelfPermission(context,
                                android.Manifest.permission.RECEIVE_SMS)
                +
                ContextCompat
                        .checkSelfPermission(context,
                                Manifest.permission.CALL_PHONE)

                +
                ContextCompat
                        .checkSelfPermission(context,
                                Manifest.permission.READ_PHONE_STATE)
                +
                ContextCompat
                        .checkSelfPermission(context,
                                Manifest.permission.READ_CONTACTS)

                ==
                PackageManager.PERMISSION_GRANTED;
    }

    public static void reqForSmsPermisssion(final Context context, final Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (activity, android.Manifest.permission.READ_SMS)
                && ActivityCompat.shouldShowRequestPermissionRationale
                (activity, android.Manifest.permission.RECEIVE_SMS)
                && ActivityCompat.shouldShowRequestPermissionRationale
                (activity, Manifest.permission.CALL_PHONE)
                && ActivityCompat.shouldShowRequestPermissionRationale
                (activity, Manifest.permission.READ_PHONE_STATE)
                && ActivityCompat.shouldShowRequestPermissionRationale
                (activity, Manifest.permission.READ_CONTACTS)) {
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
                            Manifest.permission.READ_CONTACTS,
                            android.Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.CALL_PHONE
                    },
                    1);

        }

    }
}
