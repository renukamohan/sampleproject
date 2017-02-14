package com.example.skilledanswers_d2.wallettransfer.flowactivities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.skilledanswers_d2.wallettransfer.BaseActivity;
import com.example.skilledanswers_d2.wallettransfer.LocaleHelper;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;
import com.example.skilledanswers_d2.wallettransfer.constant.CommFunc;
import com.example.skilledanswers_d2.wallettransfer.constant.Connectivity;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.CustomJsonReq;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.SingelTonVollyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class AfterLoadOrPay extends BaseActivity {
    private AppCompatEditText editTextLoadCash=null;
    private AppCompatEditText editTextApplyPromo=null;
    //
    String hindi,kannada,telugu,tamil,english;
    AppCompatButton buttonLoadCash;
    private LinearLayoutCompat layoutCompatPromoAppliedDisplayLayout=null;
    private AppCompatTextView textViewPromoAppliedDisplayTextview=null;
    private LinearLayoutCompat layoutCompatPromoEditText=null;
    /////// volley classes
    private String VOLLEY_TAG_jsonReqApplyPromo="jsonReqApplyPromo";
    private String VOLLEY_TAG_jsonReqAddMonetToWallet="jsonReqAddMonetToWallet";

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.content_base);
        getLayoutInflater().inflate(R.layout.activity_after_load_or_pay,relativeLayout);
        //
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performBack();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        final SharedPreferences sharedPreferencesPromo=getSharedPreferences(ALLCONSTANTS.PROMO_PREFERENCE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferencesPromo.edit();
        editor.putString(ALLCONSTANTS.PROMO_PREFERENCE_APPLIED_KEY,null);
        editor.apply();
        //
        layoutCompatPromoAppliedDisplayLayout= (LinearLayoutCompat) findViewById(R.id.load_cash_promo_applied_layout);
        textViewPromoAppliedDisplayTextview= (AppCompatTextView) findViewById(R.id.load_cash_promo_text);
        AppCompatImageView imageViewCancelAppliedPromo = (AppCompatImageView) findViewById(R.id.load_cash_promo_cancel);
        layoutCompatPromoEditText= (LinearLayoutCompat) findViewById(R.id.load_cash_apply_promo_edit_layout);
        AppCompatTextView textViewRupeeSymbol = (AppCompatTextView) findViewById(R.id.load_cash_rupee_symbol);
        buttonLoadCash= (AppCompatButton) findViewById(R.id.load_cash_button);
        switch (getIntent().getExtras().getString("WHAT_ACTION"))
        {
            case "LOAD" :
                buttonLoadCash.setText("Load Cash");
                break;
            case "PAY":
                buttonLoadCash.setText("Pay Cash");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textViewRupeeSymbol.setText(Html.fromHtml("&#x20B9;"));
        }else {
            textViewRupeeSymbol.setText(Html.fromHtml("&#x20B9;"));
        }
        //
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
        editTextLoadCash= (AppCompatEditText) findViewById(R.id.load_cash_edit_money);
        editTextLoadCash.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextLoadCash.setSingleLine();
        editTextApplyPromo= (AppCompatEditText) findViewById(R.id.load_cash_apply_promo_edit);
        editTextApplyPromo.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextApplyPromo.setSingleLine();
        //
        AppCompatButton buttonApplyPromo= (AppCompatButton) findViewById(R.id.load_cash_apply_promo);
        buttonApplyPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected(AfterLoadOrPay.this)) {
                    if (!editTextApplyPromo.getText().toString().isEmpty()) {
                        applyPromoVolley(editTextApplyPromo.getText().toString());
                    } else {
                        Toast.makeText(AfterLoadOrPay.this, "Please enter the promo code", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    CommFunc.NoInternetPrompt(AfterLoadOrPay.this);
                }
            }
        });
        Typeface font= Typeface.createFromAsset(getAssets(), "FONT_AWSOM.TTF");
        editTextLoadCash.setTypeface(font);
        buttonLoadCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected(AfterLoadOrPay.this)) {
                    if (!editTextLoadCash.getText().toString().isEmpty()) {
                        switch (getIntent().getExtras().getString("WHAT_ACTION"))
                        {
                            case "LOAD": addMoneyToWallet(editTextLoadCash.getText().toString());
                                break;
                            case "PAY":
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (CommFunc.CameraCheckPermission(AfterLoadOrPay.this)) {
                                    startPayToActivity();
                                }
                                else {
                                    reqForCameeraPermisssion();
                                }
                            } else {
                                startPayToActivity();
                            }
                        }

                    } else {
                        Toast.makeText(AfterLoadOrPay.this, "Amount field is empty!!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    CommFunc.NoInternetPrompt(AfterLoadOrPay.this);
                }
            }
        });
        imageViewCancelAppliedPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor1=sharedPreferencesPromo.edit();
                editor1.putString(ALLCONSTANTS.PROMO_PREFERENCE_APPLIED_KEY,null);
                editor1.apply();
                layoutCompatPromoAppliedDisplayLayout.setVisibility(View.GONE);
                layoutCompatPromoEditText.setVisibility(View.VISIBLE);
                editTextApplyPromo.setText("");
            }
        });

    }

    private void performBack() {
        if (isTaskRoot())
        {
            Intent intent=new Intent(AfterLoadOrPay.this, LoadOrPay.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            finish();
        }
    }

    public  void reqForCameeraPermisssion()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (AfterLoadOrPay.this, Manifest.permission.CAMERA)) {

          startPayToActivity();
        } else {

            ActivityCompat.requestPermissions(AfterLoadOrPay.this,
                    new String[]{Manifest.permission.CAMERA
                    },
                    1);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if ((grantResults[0]  ) == PackageManager.PERMISSION_GRANTED) {
                    startPayToActivity();
                }

        }
    }
    private void startPayToActivity()
    {
        Intent intent=new Intent(AfterLoadOrPay.this,PayTo.class);
        intent.putExtra("AMOUNT_TO_PAY",editTextLoadCash.getText().toString());
        SharedPreferences sharedPreferencesPromo=getSharedPreferences(ALLCONSTANTS.PROMO_PREFERENCE_NAME,MODE_PRIVATE);
        intent.putExtra("PROMO_APPLIED",sharedPreferencesPromo.getString(ALLCONSTANTS.PROMO_PREFERENCE_APPLIED_KEY,null));
        startActivity(intent);
    }
    ///////////////////////////
    private void applyPromoVolley(final String _promo)
    {
        showProgressDialogGlobel(AfterLoadOrPay.this,"Please wait...");
        System.out.println("eeeeeeeeeeeeeeeeeeee------cmg promo-------"+_promo.toString());
        HashMap<String,String>  mapApplyPromo=new HashMap<String, String>();
        mapApplyPromo.put("action","applyPromocode");
        mapApplyPromo.put("promocode",_promo);
        if (getIntent().getExtras().getString("WHAT_ACTION").equals("LOAD"))
        {
            mapApplyPromo.put("couponType","wallet-in");
        }else {
            mapApplyPromo.put("couponType","wallet-out");
        }
        CustomJsonReq jsonReqApplyPromo=new CustomJsonReq(Request.Method.POST,
                ALLCONSTANTS.app_post_url, mapApplyPromo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("eeeeeeeeeeeeeeeeeeee-------promo resp-------"+response.toString());
                        dismissProgressDialogGlobel();
                        if (response!=null)
                        {
                            SharedPreferences sharedPreferencesPromo=getSharedPreferences(ALLCONSTANTS.PROMO_PREFERENCE_NAME,MODE_PRIVATE);
                            try {
                                if (response.getBoolean("status"))
                                {
                                    JSONObject jsonObject=response.getJSONObject("data");
                                ////  storing the coupon... .
                                    SharedPreferences.Editor editor=sharedPreferencesPromo.edit();
                                    editor.putString(ALLCONSTANTS.PROMO_PREFERENCE_APPLIED_KEY,_promo);
                                    editor.apply();
                                    layoutCompatPromoEditText.setVisibility(View.GONE);  //// make apply promo button invisible.... .
                                    layoutCompatPromoAppliedDisplayLayout.setVisibility(View.VISIBLE);
                                    textViewPromoAppliedDisplayTextview.setText(response.getString("message")+" "+
                                            jsonObject.getString("discountType")+" "+jsonObject.getString("discount")+" Applied.");

                                }else {
                                    CommFunc.commonDialog(AfterLoadOrPay.this,"Promo error",response.getString("message"));
                                    editTextApplyPromo.setText("");

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            CommFunc.NoInternetPrompt(AfterLoadOrPay.this);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialogGlobel();
                CommFunc.NoInternetPrompt(AfterLoadOrPay.this);
            }
        });
        jsonReqApplyPromo.setTag(VOLLEY_TAG_jsonReqApplyPromo);
        SingelTonVollyQueue.getInstance(AfterLoadOrPay.this).addToRequestQueue(jsonReqApplyPromo);
    }

    private void addMoneyToWallet(String amountTOAdd)
    {
        showProgressDialogGlobel(AfterLoadOrPay.this,"Please wait...");
        HashMap<String,String> mapAddMoneyToWallet=new HashMap<>();
        mapAddMoneyToWallet.put("action","walletRecharge");
        SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        mapAddMoneyToWallet.put("androidToken",preferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
        mapAddMoneyToWallet.put("amount",amountTOAdd);
        SharedPreferences sharedPreferencesPromo=getSharedPreferences(ALLCONSTANTS.PROMO_PREFERENCE_NAME,MODE_PRIVATE);
        System.out.println("******************--------"+sharedPreferencesPromo.getString(ALLCONSTANTS.PROMO_PREFERENCE_APPLIED_KEY,null));
        if (sharedPreferencesPromo.getString(ALLCONSTANTS.PROMO_PREFERENCE_APPLIED_KEY,null)!=null)
        {
            System.out.println("******************-----7---"+sharedPreferencesPromo.getString(ALLCONSTANTS.PROMO_PREFERENCE_APPLIED_KEY,null));
            mapAddMoneyToWallet.put("coupon",sharedPreferencesPromo.getString(ALLCONSTANTS.PROMO_PREFERENCE_APPLIED_KEY,null));
        }
        CustomJsonReq jsonReqAddMonetToWallet=new CustomJsonReq(Request.Method.POST, ALLCONSTANTS.app_post_url, mapAddMoneyToWallet,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("rrrrrrrrrrrrrrrrrr------"+response);
                        dismissProgressDialogGlobel();
                        if (response!=null)
                        {
                            try {
                                if (response.getBoolean("status"))
                                {
                                    JSONObject objectdata=response.getJSONObject("data");
                                    String makePostViaWebviewTOURl=objectdata.getString("formURL");
                                    JSONObject objectFormData=objectdata.getJSONObject("formData");
                                    Toast.makeText(AfterLoadOrPay.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                                    //
                                    Intent intent=new Intent(AfterLoadOrPay.this,WebViewForPay.class);
                                    intent.putExtra("formURL",makePostViaWebviewTOURl);
                                    intent.putExtra("jsonObject",objectFormData.toString());
                                    intent.putExtra("PAY_OR_LOAD","LOAD");
                                    startActivity(intent);

                                }
                                else {
                                    boolean isLoggedOut=false;
                                    if (response.optBoolean("isLoggedOut"))
                                    {
                                        isLoggedOut=true;
                                        CommFunc.goToSignupCommFuncPrompt(AfterLoadOrPay.this);

                                    }
                              if (!isLoggedOut) {
                                  CommFunc.commonDialog(AfterLoadOrPay.this, "Load cash error", response.getString("message"));
                              }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            CommFunc.NoInternetPrompt(AfterLoadOrPay.this);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialogGlobel();
              CommFunc.NoInternetPrompt(AfterLoadOrPay.this);

            }
        });
        jsonReqAddMonetToWallet.setTag(VOLLEY_TAG_jsonReqAddMonetToWallet);
        SingelTonVollyQueue.getInstance(AfterLoadOrPay.this).addToRequestQueue(jsonReqAddMonetToWallet);

    }

    @Override
    public void onBackPressed() {
     performBack();
    }
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("uuuuuuuuuuu----------------onpause");
        if (textViewToolbarAmountTextview!=null)
        {
            SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textViewToolbarAmountTextview.setText(Html.fromHtml("&#x20B9; ",0)+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,
                        null));
            }else {
                textViewToolbarAmountTextview.setText(Html.fromHtml("&#x20B9; ")+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,
                        null));
            }
        }
        SingelTonVollyQueue.getInstance(AfterLoadOrPay.this).getRequestQueue().cancelAll(VOLLEY_TAG_jsonReqAddMonetToWallet);
        SingelTonVollyQueue.getInstance(AfterLoadOrPay.this).getRequestQueue().cancelAll(VOLLEY_TAG_jsonReqApplyPromo);
        dismissProgressDialogGlobel();
        overridePendingTransition(0, 0);
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        buttonLoadCash.setText(resources.getString(R.string.load_money));




    }

}
