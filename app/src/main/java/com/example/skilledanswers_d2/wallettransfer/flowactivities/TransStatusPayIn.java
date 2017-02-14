package com.example.skilledanswers_d2.wallettransfer.flowactivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.skilledanswers_d2.wallettransfer.LocaleHelper;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;
import com.example.skilledanswers_d2.wallettransfer.constant.Connectivity;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.CustomJsonReq;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.SingelTonVollyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class TransStatusPayIn extends AppCompatActivity {
    String hindi,kannada,telugu,tamil,english;
    private ProgressDialog pDialog = null;
    private AppCompatTextView textViewCurrentBal=null;
    AppCompatButton buttonPAy;
    AppCompatButton buttonAddMore;

    /////////// volley bal
    private String VOLLEY_TAG_TRANSSTATUS_PAY_IN_WALLET_BAL="VOLLEY_TAG_TRANSSTATUS_PAY_IN_WALLET_BAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_status_in);
        AppCompatImageView imageView= (AppCompatImageView)
                findViewById(R.id.trans_status_imageview);
        textViewCurrentBal= (AppCompatTextView) findViewById(R.id.trans_status_avilable_balance);
        AppCompatTextView textViewMessage= (AppCompatTextView) findViewById(R.id.trans_status_message);
        AppCompatTextView textViewRefid= (AppCompatTextView) findViewById(R.id.trans_status_ref_id);
        AppCompatTextView textViewPaymentId= (AppCompatTextView) findViewById(R.id.trans_status_payment_id);
        AppCompatTextView textViewAmount= (AppCompatTextView) findViewById(R.id.trans_status_amount);
         buttonAddMore= (AppCompatButton) findViewById(R.id.trans_status_add_more);
        buttonPAy= (AppCompatButton) findViewById(R.id.trans_status_pay);
        Bundle bundle=getIntent().getExtras();
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
        buttonAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadCash=new Intent(TransStatusPayIn.this, AfterLoadOrPay.class);
                intentLoadCash.putExtra("WHAT_ACTION","LOAD");
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentLoadCash);
            }
        });
        buttonPAy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadCash=new Intent(TransStatusPayIn.this, AfterLoadOrPay.class);
                intentLoadCash.putExtra("WHAT_ACTION","PAY");
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentLoadCash);
            }
        });
        if (bundle.getBoolean("STATUS"))
        {
            imageView.setImageResource(R.drawable.ic_successnew);
        }else {
            imageView.setImageResource(R.drawable.ic_failnew);
        }
        Animation animation= AnimationUtils.loadAnimation(TransStatusPayIn.this,R.anim.zoom_image);
        imageView.startAnimation(animation);

        ///////////////////////////////////////////////////////////////////
        textViewMessage.setText(bundle.getString("MESSAGE"));
        if (!bundle.getString("REF_ID").equals(""))
        {
            textViewRefid.setText("Reference id: "+bundle.getString("REF_ID"));
        }else {
            textViewRefid.setText("Reference id: -");
        }
        textViewPaymentId.setText("Payment id: "+bundle.getString("PAYMENT_ID"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textViewAmount.setText(Html.fromHtml("&#x20B9; ")+bundle.getString("AMOUNT"));
        }else {
            textViewAmount.setText(Html.fromHtml("&#x20B9; ")+bundle.getString("AMOUNT"));
        }
        if (Connectivity.isConnected(TransStatusPayIn.this)) {
            getWalletBalVolley();
        }else {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
        }




    }

    private void performBack() {

            Intent intentLoadCash=new Intent(TransStatusPayIn.this, LoadOrPay.class);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentLoadCash);

    }

    private void getWalletBalVolley()
    {
        showProgressDialog();
        HashMap<String,String> mapGetWalletBal=new HashMap<>();
        mapGetWalletBal.put("action","getWalletAmount");
        final SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        mapGetWalletBal.put("androidToken",preferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
        System.out.println("bbbbbbbbbbbbbb-------SESSION_KEY_TOKEN------"+preferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
        CustomJsonReq jsonReqGetWalletBal=new CustomJsonReq(Request.Method.POST, ALLCONSTANTS.app_post_url,
                mapGetWalletBal, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                if (response!=null)
                {
                    try {
                        if (response.getBoolean("status"))
                        {
                            JSONObject object=response.getJSONObject("data");
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString(ALLCONSTANTS.SESSION_KEY_WALLET1,object.getString("walletAmount"));
                            editor.apply();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                textViewCurrentBal.setText(Html.fromHtml("Available Wallet Balance "+"&#x20B9;&nbsp;"+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,null),0));
                            }else {
                                textViewCurrentBal.setText(Html.fromHtml("Available Wallet Balance "+"&#x20B9;&nbsp;"+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,null)));
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
            }
        });
        jsonReqGetWalletBal.setTag(VOLLEY_TAG_TRANSSTATUS_PAY_IN_WALLET_BAL);
        SingelTonVollyQueue.getInstance(TransStatusPayIn.this).addToRequestQueue(jsonReqGetWalletBal);
    }
    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(TransStatusPayIn.this);
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
    protected void onPause() {
        super.onPause();
        SingelTonVollyQueue.getInstance(TransStatusPayIn.this).getRequestQueue().cancelAll(VOLLEY_TAG_TRANSSTATUS_PAY_IN_WALLET_BAL);
        dismissProgressDialog();
    }

    @Override
    public void onBackPressed() {
        performBack();

    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        buttonPAy.setText(resources.getString(R.string.pay_will_be_added_soon));
        buttonAddMore.setText(resources.getString(R.string.add_more));



    }
}
