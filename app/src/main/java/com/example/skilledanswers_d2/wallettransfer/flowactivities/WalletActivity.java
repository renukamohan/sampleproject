package com.example.skilledanswers_d2.wallettransfer.flowactivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.skilledanswers_d2.wallettransfer.BaseActivity;
import com.example.skilledanswers_d2.wallettransfer.LocaleHelper;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.CustomJsonReq;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.SingelTonVollyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class WalletActivity extends BaseActivity {

    private ProgressDialog pDialog = null;
    private AppCompatTextView textViewWallet=null;
    String hindi,kannada,telugu,tamil,english;
    AppCompatButton buttonAddmoney;
    AppCompatButton buttonPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.content_base);
        getLayoutInflater().inflate(R.layout.activity_wallet,relativeLayout);
        textViewWallet= (AppCompatTextView) findViewById(R.id.wallet_balance);
        buttonAddmoney= (AppCompatButton) findViewById(R.id.wallet_add_money);
        buttonPay= (AppCompatButton) findViewById(R.id.wallet_add_pay);
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        english = pref.getString("english","");
        hindi = pref.getString("hindi","");
        kannada = pref.getString("kannada","");
        telugu = pref.getString("telugu","");
        tamil = pref.getString("tamil","");

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
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performBack();
            }
        });

        buttonAddmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadCash=new Intent(WalletActivity.this, LoadOrPay.class);
                startActivity(intentLoadCash);
            }
        });
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WalletActivity.this, "Will be added soon!!", Toast.LENGTH_SHORT).show();
            }
        });
        getWalletBalVolley();

    }
    private void getWalletBalVolley()
    {
        showProgressDialog();
        HashMap<String,String> mapGetWalletBal=new HashMap<>();
        mapGetWalletBal.put("action","getWalletAmount");
        final SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        mapGetWalletBal.put("androidToken",preferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
        System.out.println("bbbbbbbbbbbbbb-------SESSION_KEY_TOKEN------"+preferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
        CustomJsonReq  jsonReqGetWalletBal=new CustomJsonReq(Request.Method.POST, ALLCONSTANTS.app_post_url,
                mapGetWalletBal, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    if (response.getBoolean("status"))
                    {
                        Toast.makeText(WalletActivity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONObject object=response.getJSONObject("data");
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString(ALLCONSTANTS.SESSION_KEY_WALLET1,object.getString("walletAmount"));
                        editor.apply();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            textViewToolbarAmountTextview.setText(Html.fromHtml("&#x20B9; "+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,null)));
                            textViewWallet.setText(Html.fromHtml("&#x20B9; "+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,null)));
                        }
                        else {
                            textViewToolbarAmountTextview.setText(Html.fromHtml("&#x20B9; "+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,null)));
                            textViewWallet.setText(Html.fromHtml("&#x20B9; "+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,null)));

                        }
                    }else {
                        Toast.makeText(WalletActivity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(WalletActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        jsonReqGetWalletBal.setTag("jsonReqGetWalletBal");
        SingelTonVollyQueue.getInstance(WalletActivity.this).addToRequestQueue(jsonReqGetWalletBal);
    }

    @Override
    public void onBackPressed() {
        performBack();
    }
    private void performBack()
    {
        if (isTaskRoot())
        {
            Intent intentLoadCash=new Intent(WalletActivity.this, LoadOrPay.class);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentLoadCash);
        }else {
            finish();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(WalletActivity.this);
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
        buttonAddmoney.setText(resources.getString(R.string.addmoney));
        buttonPay.setText(resources.getString(R.string.paywillbadded));


    }
}
