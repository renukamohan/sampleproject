package com.example.skilledanswers_d2.wallettransfer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.example.skilledanswers_d2.wallettransfer.loginactivity.SendSMS;

public class AlertMsg extends Activity {
    AppCompatButton appCompatButtonSkip,appCompatButtonSet;
    AppCompatEditText appCompatEditText;
    AppCompatTextView appCompatTextView;
    String hindi,kannada,telugu,tamil,english;
    int count=0;
    String phone;
    String alertvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_msg);
        phone=getIntent().getStringExtra("phone");
        appCompatButtonSkip=(AppCompatButton)findViewById(R.id.skip_alert);
        appCompatButtonSet=(AppCompatButton)findViewById(R.id.set_alert);
        appCompatEditText=(AppCompatEditText)findViewById(R.id.alert_value);
        appCompatTextView=(AppCompatTextView)findViewById(R.id.mini);
        appCompatButtonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        appCompatButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count==0) {
                    appCompatEditText.setVisibility(View.VISIBLE);
                    alertvalue= appCompatEditText.getText().toString();
                    Log.e("skjwnjskdn",""+alertvalue);
                    count++;
                }
                else if(count==1)
                {
                    count--;
                    alertvalue= appCompatEditText.getText().toString();
                    Log.e("skjwnjskdn",""+alertvalue);
                    appCompatEditText.setText("");
                    appCompatEditText.setVisibility(View.GONE);
                    Intent intent=new Intent(getApplicationContext(), SendSMS.class);
                    intent.putExtra("phone",phone);
                    Log.e("skjwnjskdn1",""+alertvalue);
                    intent.putExtra("alertvalue",alertvalue);
                    startActivity(intent);
//                    Toast.makeText(getApplicationContext(),"Alert has been set",Toast.LENGTH_SHORT).show();
                }
            }
        });
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        english = pref.getString("english","");
        hindi = pref.getString("hindi","");
        kannada = pref.getString("kannada","");
        tamil = pref.getString("tamil","");
        telugu = pref.getString("telugu","");



        if(english.equalsIgnoreCase("1"))
        {
            Log.e("english",""+english);
            updateViews("en");
        }
        else if(hindi.equalsIgnoreCase("2"))
        {
            Log.e("hindi",""+hindi);
            updateViews("hi");

        }
        else if(kannada.equalsIgnoreCase("3"))
        {
            Log.e("kannada",""+kannada);
            updateViews("kn");
        }
        else if(tamil.equalsIgnoreCase("4"))
        {
            Log.e("tamil",""+tamil);
            updateViews("ta");

        }
        else if(telugu.equalsIgnoreCase("5"))
        {
            Log.e("telugu",""+telugu);

            updateViews("te");

        }


    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        appCompatButtonSkip.setText(resources.getString(R.string.skip));
        appCompatButtonSet.setText(resources.getString(R.string.setalert));
        appCompatTextView.setText(resources.getString(R.string.mini));


    }
    @Override
    public void onBackPressed() {
        performBack();
    }
    private void performBack()
    {
        if (isTaskRoot())
        {
            Intent intentLoadCash=new Intent(AlertMsg.this, ListViewForContact.class);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentLoadCash);
        }else {
            finish();
        }
    }
}