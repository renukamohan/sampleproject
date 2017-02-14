package com.example.skilledanswers_d2.wallettransfer.flowactivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.skilledanswers_d2.wallettransfer.BaseActivity;
import com.example.skilledanswers_d2.wallettransfer.LocaleHelper;
import com.example.skilledanswers_d2.wallettransfer.R;



public class LoadOrPay extends BaseActivity {
    AppCompatTextView appCompatTextView,appCompatTextView1;
    String hindi,kannada,telugu,tamil,english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.content_base);
        getLayoutInflater().inflate(R.layout.activity_load_or_pay,relativeLayout);
        appCompatTextView=(AppCompatTextView)findViewById(R.id.load_cash_textView);
        appCompatTextView=(AppCompatTextView)findViewById(R.id.load_cash_textView);
        appCompatTextView1=(AppCompatTextView)findViewById(R.id.pay_cash_textView);
        LinearLayoutCompat buttonLoad= (LinearLayoutCompat) findViewById(R.id.loadorpay_loadmoneyButton_id);
        LinearLayoutCompat buttonPay= (LinearLayoutCompat) findViewById(R.id.loadorpay_paymoneyButton_id);
        ////
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
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoadOrPay.this,AfterLoadOrPay.class);
                intent.putExtra("WHAT_ACTION","LOAD");
                startActivity(intent);
            }
        });
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoadOrPay.this,AfterLoadOrPay.class);
                intent.putExtra("WHAT_ACTION","PAY");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (isTaskRoot())
        {
            new AlertDialog.Builder(LoadOrPay.this)
                    .setTitle("Alert!!")

                    .setMessage("Do you want to close ?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }

                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }else {
            finish();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        appCompatTextView.setText(resources.getString(R.string.load_cash));
        appCompatTextView1.setText(resources.getString(R.string.pay_cash));



    }
}
