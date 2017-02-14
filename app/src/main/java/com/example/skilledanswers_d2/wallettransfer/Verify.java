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
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.skilledanswers_d2.wallettransfer.loginactivity.Signup;

import java.util.Locale;


public class Verify extends Activity {
    TextView button,sim1,sim2,heading,desc,sample,nextbutton;
    String english,hindi,kannada,tamil,telugu;
    Locale myLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify);

            button = (TextView) findViewById(R.id.nextverify);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Verify.Smspermissioncheck(Verify.this)) {
                        Verify.reqForSmsPermisssion(Verify.this, Verify.this);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), Signup.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim, R.anim.right);
                    }
                }
            });


        heading=(TextView)findViewById(R.id.city_field);
        nextbutton=(TextView)findViewById(R.id.nextverify);
        desc=(TextView)findViewById(R.id.textfield);
        sample=(TextView)findViewById(R.id.sampletext);
        english=getIntent().getStringExtra("english");
        hindi=getIntent().getStringExtra("hindi");
        kannada=getIntent().getStringExtra("kannada");
        tamil=getIntent().getStringExtra("tamil");
        telugu=getIntent().getStringExtra("telugu");
        Log.e("verifyhin",""+hindi);
        Log.e("verifyeng",""+english);
        Log.e("verifytamil",""+tamil);
        Log.e("verifytelugu",""+telugu);
        Log.e("verifykannada",""+kannada);
        if(english.equalsIgnoreCase("1")) {
            desc.setText(getString(R.string.descriptiion));
            heading.setText(getString(R.string.headingenglish));
            sample.setText(getString(R.string.message));
            nextbutton.setText(getString(R.string.NextEnglish));
        }
      else if(hindi.equalsIgnoreCase("2"))
        {
            desc.setText(getString(R.string.descriptiionhindi));
            heading.setText(getString(R.string.headinghindi));
            sample.setText(getString(R.string.messagehindi));
            nextbutton.setText(getString(R.string.Nexthindi));
        }
        else if(kannada.equalsIgnoreCase("3"))
        {
            desc.setText(getString(R.string.descriptuonkannada));
            heading.setText(getString(R.string.headingkannada));
            sample.setText(getString(R.string.messagekannada));
            nextbutton.setText(getString(R.string.nextkannada));
        }
        else if(tamil.equalsIgnoreCase("4"))
        {
            desc.setText(getString(R.string.descriptiontamil));
            heading.setText(getString(R.string.headingtamil));
            sample.setText(getString(R.string.messagetamil));
            nextbutton.setText(getString(R.string.nexttamil));
        }
        else if(telugu.equalsIgnoreCase("5"))
        {
            desc.setText(getString(R.string.descriptiontelugu));
            heading.setText(getString(R.string.headingtelugu));
            sample.setText(getString(R.string.messagetelugu));
            nextbutton.setText(getString(R.string.nexttelugu));
        }

        sim1=(TextView)findViewById(R.id.sim1);
        sim2=(TextView)findViewById(R.id.sim2);



        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telephonyManager.getSimState();

        switch (simState) {

            case (TelephonyManager.SIM_STATE_ABSENT):
                break;
            case (TelephonyManager.SIM_STATE_NETWORK_LOCKED):
                break;
            case (TelephonyManager.SIM_STATE_PIN_REQUIRED):
                break;
            case (TelephonyManager.SIM_STATE_PUK_REQUIRED):
                break;
            case (TelephonyManager.SIM_STATE_UNKNOWN):
                break;
            case (TelephonyManager.SIM_STATE_READY): {

                // Get the SIM country ISO code
                String simCountry = telephonyManager.getSimCountryIso();

                // Get the operator code of the active SIM (MCC + MNC)
                String simOperatorCode = telephonyManager.getSimOperator();

                // Get the name of the SIM operator
                String simOperatorName = telephonyManager.getSimOperatorName();

                // Get the SIM’s serial number
//                String simSerial = telephonyManager.getSimSerialNumber();
                sim1.setText("SIM 1 :"+simOperatorName);

//                sim2.setText(""+simOperatorName);
                Log.e("jhfkjfdhj", "" + simOperatorName);
//                Log.e("jhfkjfdhj", "" + simSerial);
                Log.e("jhfkjfdhj", "" + simOperatorCode);
                Log.e("jhfkjfdhj", "" + simCountry);

            }
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

