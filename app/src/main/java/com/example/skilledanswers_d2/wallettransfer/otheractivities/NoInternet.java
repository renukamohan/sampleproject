package com.example.skilledanswers_d2.wallettransfer.otheractivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.skilledanswers_d2.wallettransfer.LocaleHelper;
import com.example.skilledanswers_d2.wallettransfer.MainActivity;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.constant.Connectivity;

public class NoInternet extends AppCompatActivity {

    private AppCompatButton buttonTryAgain=null;
    private AppCompatButton buttonEnableWifi=null;
    private AppCompatButton buttonEnableData=null;
    private AppCompatTextView textViewDataErrorText=null;
    private AppCompatImageView imageView=null;
    private boolean isEnableDataClicked=false;
    private ProgressDialog pDialog = null;
    String hindi,kannada,telugu,tamil,english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
          imageView= (AppCompatImageView)
                findViewById(R.id.no_internet_imageview);
        buttonTryAgain= (AppCompatButton) findViewById(R.id.no_internet_try_again);
        buttonEnableWifi= (AppCompatButton) findViewById(R.id.no_internet_enable_wifi);
        buttonEnableData= (AppCompatButton) findViewById(R.id.no_internet_enable_data);
        textViewDataErrorText= (AppCompatTextView) findViewById(R.id.no_internet_textview);

        if (!Connectivity.isConnected(getApplicationContext()))
        {
            imageView.setImageResource(R.drawable.no_internet);
//            buttonTryAgain.setVisibility(View.GONE);
            textViewDataErrorText.setText(R.string.no_internet_class_no_internet_msg);
        }else if (Connectivity.isConnected(getApplicationContext())
                && !Connectivity.isConnectedFast(getApplicationContext()))
        {
            imageView.setImageResource(R.drawable.low_internet);
            textViewDataErrorText.setText(R.string.no_internet_internet_not_proper_msg);
//            buttonEnableWifi.setVisibility(View.GONE);
//            buttonEnableData.setVisibility(View.GONE);

        }
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
        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryagain();
            }
        });
        buttonEnableWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifiManager ;
                wifiManager  = (WifiManager)getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
                wifiManager.setWifiEnabled(true);        //True - to enable WIFI connectivity .
                showProgressDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (Connectivity.isConnected(getApplicationContext()) &&
                                Connectivity.isConnectedFast(getApplicationContext()))
                        {
                            Intent intent=new Intent(NoInternet.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            if (!Connectivity.isConnected(getApplicationContext()))
                            {
                                imageView.setImageResource(R.drawable.no_internet);
                                textViewDataErrorText.setText(R.string.no_internet_class_no_internet_msg);
//                                buttonTryAgain.setVisibility(View.GONE);
                            }else if (Connectivity.isConnected(getApplicationContext())
                                    && !Connectivity.isConnectedFast(getApplicationContext()))
                            {
                                imageView.setImageResource(R.drawable.low_internet);
//                                buttonEnableWifi.setVisibility(View.GONE);
//                                buttonEnableData.setVisibility(View.GONE);
                                textViewDataErrorText.setText(R.string.no_internet_internet_not_proper_msg);
                            }
                        }

                       dismissProgressDialog();
                    }
                },5000);

            }
        });
        buttonEnableData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean runProgress=true;
//                ConnectivityManager dataManager;
//                dataManager  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//                Method dataMtd = null;
//                try {
//                    dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
//                    dataMtd.setAccessible(true);
//                } catch (NoSuchMethodException e) {
//                    runProgress=false;
//                    Toast.makeText(NoInternet.this, "Error.....", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//
//                try {
//                    dataMtd.invoke(dataManager, true);        //True - to enable data connectivity .
//                } catch (Exception e) {
//                    runProgress=false;
//                    Toast.makeText(NoInternet.this, "Error.....", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//                if (runProgress) {
//                    final ProgressDialog progressDialog = new ProgressDialog(NoInternet.this);
//                    progressDialog.setMessage("Please wait.....");
//                    progressDialog.setCanceledOnTouchOutside(false);
//                    progressDialog.show();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            if (Connectivity.isConnected(getApplicationContext()) &&
//                                    Connectivity.isConnectedFast(getApplicationContext())) {
//                                Intent intent = new Intent(NoInternet.this, SplashScreen.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            } else {
//                                if (!Connectivity.isConnected(getApplicationContext())) {
//                                    imageView.setImageResource(R.drawable.no_internet);
//                                    textViewDataErrorText.setText(R.string.no_internet_class_no_internet_msg);
//                                    buttonTryAgain.setVisibility(View.GONE);
//                                } else if (Connectivity.isConnected(getApplicationContext())
//                                        && !Connectivity.isConnectedFast(getApplicationContext())) {
//                                    imageView.setImageResource(R.drawable.low_internet);
//                                    buttonEnableWifi.setVisibility(View.GONE);
//                                    buttonEnableData.setVisibility(View.GONE);
//                                    textViewDataErrorText.setText(R.string.no_internet_internet_not_proper_msg);
//                                }
//                            }
//
//                            if (progressDialog.isShowing()) {
//                                progressDialog.dismiss();
//                            }
//                        }
//                    }, 5000);
//                }
//
                isEnableDataClicked=true;
                Intent myIntent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                startActivity(myIntent);
            }
        });

    }

    private void tryagain() {
        if (Connectivity.isConnected(getApplicationContext()) &&
                Connectivity.isConnectedFast(getApplicationContext()))
        {
            Intent intent=new Intent(NoInternet.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {

            if (!Connectivity.isConnected(getApplicationContext()))
            {
                Toast.makeText(NoInternet.this, "No Internet again", Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.no_internet);
                textViewDataErrorText.setText(R.string.no_internet_class_no_internet_msg);
//                buttonTryAgain.setVisibility(View.GONE);
            }else if (Connectivity.isConnected(getApplicationContext())
                    && !Connectivity.isConnectedFast(getApplicationContext()))
            {
                Toast.makeText(NoInternet.this, "Low Internet again", Toast.LENGTH_SHORT).show();

                imageView.setImageResource(R.drawable.low_internet);
//                buttonEnableWifi.setVisibility(View.GONE);
//                buttonEnableData.setVisibility(View.GONE);
                textViewDataErrorText.setText(R.string.no_internet_internet_not_proper_msg);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("ggggggggggggggg-----y---"+isEnableDataClicked);
        if (isEnableDataClicked)
        {
            tryagain();
            isEnableDataClicked=false;
        }

    }
    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(NoInternet.this);
            pDialog.setMessage("Please wait... .");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }
    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        buttonTryAgain.setText(resources.getString(R.string.no_internet_try_again));
        buttonEnableWifi.setText(resources.getString(R.string.no_internet_button_enable));
        buttonEnableData.setText(resources.getString(R.string.no_internet_button_enable_mobile_data));


    }
}
