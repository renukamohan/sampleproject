package com.example.skilledanswers_d2.wallettransfer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.example.skilledanswers_d2.wallettransfer.flowactivities.LoadOrPay;

/**
 * Created by SkilledAnswers-D2 on 25-01-2017.
 */

public class PassCodeSkip extends Activity {
    AppCompatButton appCompatButtonSkip,appCompatButtonSet;
    String hindi,kannada,telugu,tamil,english;
    AppCompatTextView appCompatTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skip_passcode);
        appCompatButtonSkip = (AppCompatButton) findViewById(R.id.skip_passcode);
        appCompatButtonSet = (AppCompatButton) findViewById(R.id.set_passcode);
        appCompatTextView=(AppCompatTextView)findViewById(R.id.passcodeheading);
        appCompatButtonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoadOrPay.class);
                startActivity(intent);
            }
        });
        appCompatButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Passcode.class);
                startActivity(intent);
            }
        });


        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        english = pref.getString("english", "");
        hindi = pref.getString("hindi", "");
        kannada = pref.getString("kannada", "");
        tamil = pref.getString("tamil", "");
        telugu = pref.getString("telugu", "");


        if (english.equalsIgnoreCase("1")) {
            Log.e("english", "" + english);
            updateViews("en");
        } else if (hindi.equalsIgnoreCase("2")) {
            Log.e("hindi", "" + hindi);
            updateViews("hi");

        } else if (kannada.equalsIgnoreCase("3")) {
            Log.e("kannada", "" + kannada);
            updateViews("kn");
        } else if (tamil.equalsIgnoreCase("4")) {
            Log.e("tamil", "" + tamil);
            updateViews("ta");

        } else if (telugu.equalsIgnoreCase("5")) {
            Log.e("telugu", "" + telugu);

            updateViews("te");

        }
    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        appCompatButtonSkip.setText(resources.getString(R.string.skip));
        appCompatTextView.setText(resources.getString(R.string.passcodesetting));
        appCompatButtonSet.setText(resources.getString(R.string.setpasscode));


    }
}
