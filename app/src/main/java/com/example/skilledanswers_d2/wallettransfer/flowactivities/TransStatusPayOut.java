
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

import java.util.Calendar;
import java.util.HashMap;


public class TransStatusPayOut extends AppCompatActivity {
    private AppCompatTextView textViewtrans_status_avilable_balance_out=null;
    private ProgressDialog pDialog = null;
    AppCompatButton buttontrans_status_add_more;
    AppCompatButton buttontrans_status_pay;
    //// asyncTask
    private String VOLLEY_TAG_TRANSSTATUS_PAY_OUT_WALLET_BAL="VOLLEY_TAG_TRANSSTATUS_PAY_OUT_WALLET_BAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_status_out);
        AppCompatImageView imageViewStatus = (AppCompatImageView) findViewById(R.id.trans_status_imageview_out);
        AppCompatTextView textViewtrans_status_amount_out = (AppCompatTextView) findViewById(R.id.trans_status_amount_out);
        AppCompatTextView textViewtrans_status_out_time = (AppCompatTextView) findViewById(R.id.trans_status_out_time);
        AppCompatTextView textViewtrans_status_out_merchant_name = (AppCompatTextView) findViewById(R.id.trans_status_out_merchant_name);
        textViewtrans_status_avilable_balance_out= (AppCompatTextView) findViewById(R.id.trans_status_avilable_balance_out);
        AppCompatTextView textViewStatusMessage = (AppCompatTextView) findViewById(R.id.trans_status_message_out);
      buttontrans_status_add_more = (AppCompatButton) findViewById(R.id.trans_status_out_add_more);
        buttontrans_status_pay = (AppCompatButton) findViewById(R.id.trans_out_status_pay);
        ////////////////////
        buttontrans_status_add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadCash=new Intent(TransStatusPayOut.this, AfterLoadOrPay.class);
                intentLoadCash.putExtra("WHAT_ACTION","LOAD");
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentLoadCash);
            }
        });
        buttontrans_status_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadCash=new Intent(TransStatusPayOut.this, AfterLoadOrPay.class);
                intentLoadCash.putExtra("WHAT_ACTION","PAY");
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentLoadCash);
            }
        });
        ////////////////////
        if (getIntent().getExtras().getBoolean("isPaid"))
        {
            imageViewStatus.setImageResource(R.drawable.ic_successnew);
        }else {
            imageViewStatus.setImageResource(R.drawable.ic_failnew);
        }
        Animation animation= AnimationUtils.loadAnimation(TransStatusPayOut.this,R.anim.zoom_image);
        imageViewStatus.startAnimation(animation);
        textViewStatusMessage.setText(getIntent().getExtras().getString("message"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textViewtrans_status_amount_out.setText(Html.fromHtml("&#x20B9; "+getIntent().getExtras().getString("amount")));
        }else {
            textViewtrans_status_amount_out.setText(Html.fromHtml("&#x20B9; "+getIntent().getExtras().getString("amount")));

        }
        String currentDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        textViewtrans_status_out_time.setText(currentDate);
        textViewtrans_status_out_merchant_name.setText(getIntent().getExtras().getString("merchantName"));
        if (Connectivity.isConnected(TransStatusPayOut.this)) {
            getWalletBalVolley();
        }else {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
        }


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
                                textViewtrans_status_avilable_balance_out.setText(Html.fromHtml("Available Wallet Balance "+"&#x20B9;&nbsp;"+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,null),0));
                            }else {
                                textViewtrans_status_avilable_balance_out.setText(Html.fromHtml("Available Wallet Balance "+"&#x20B9;&nbsp;"+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,null)));
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
        jsonReqGetWalletBal.setTag(VOLLEY_TAG_TRANSSTATUS_PAY_OUT_WALLET_BAL);
        SingelTonVollyQueue.getInstance(TransStatusPayOut.this).addToRequestQueue(jsonReqGetWalletBal);
    }
    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(TransStatusPayOut.this);
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
        SingelTonVollyQueue.getInstance(TransStatusPayOut.this).getRequestQueue().cancelAll(VOLLEY_TAG_TRANSSTATUS_PAY_OUT_WALLET_BAL);
        dismissProgressDialog();
    }

    @Override
    public void onBackPressed() {

        performBack();
    }
    private void performBack() {

        Intent intentLoadCash=new Intent(TransStatusPayOut.this, LoadOrPay.class);
        intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentLoadCash);

    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        buttontrans_status_add_more.setText(resources.getString(R.string.add_more));
        buttontrans_status_pay.setText(resources.getString(R.string.pay_will_be_added_soon));



    }
}
