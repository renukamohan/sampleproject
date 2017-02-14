package com.example.skilledanswers_d2.wallettransfer.flowactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.skilledanswers_d2.wallettransfer.BaseActivity;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;
import com.example.skilledanswers_d2.wallettransfer.constant.CommFunc;
import com.example.skilledanswers_d2.wallettransfer.constant.Connectivity;
import com.example.skilledanswers_d2.wallettransfer.models.MerchantsModel;
import com.example.skilledanswers_d2.wallettransfer.otheractivities.GetMerchatAct;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.CustomJsonReq;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.SingelTonVollyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowSelBranch extends BaseActivity {
    private TextInputLayout inputLayoutSelectBranch=null;
    private TextInputLayout inputLayoutAmount=null;
    //
    private AppCompatAutoCompleteTextView autoCompleteTextViewSearchBranch=null;
    private AppCompatEditText editTextAmount=null;

    //
    private ArrayList<MerchantsModel> merchantsModels=new ArrayList<>();
    //// volley tags
    private String VOLLEY_SHOW_SEL_BRANCH_GET_MERCHANTS="VOLLEY_SHOW_SEL_BRANCH_GET_MERCHANTS";
    private String VOLLEY_SHOW_SEL_BRANCH_PAY_OUT="VOLLEY_SHOW_SEL_BRANCH_PAY_OUT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout layout= (RelativeLayout) findViewById(R.id.content_base);
        getLayoutInflater().inflate(R.layout.activity_show_sel_branch,layout);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Toast.makeText(ShowSelBranch.this, ""+getIntent().getExtras().getString("PASS_BARCODE"), Toast.LENGTH_LONG).show();
        inputLayoutSelectBranch= (TextInputLayout) findViewById(R.id.show_sel_branch_branch_inputlayout);
        inputLayoutAmount= (TextInputLayout) findViewById(R.id.show_sel_branch_amount_input_layout);
        //
        AppCompatImageView imageViewSelFromList = (AppCompatImageView) findViewById(R.id.show_sel_branch_branch_list_imageview);
        //
        autoCompleteTextViewSearchBranch= (AppCompatAutoCompleteTextView) findViewById(R.id.show_sel_branch_branch_autocomplete);

        //
        editTextAmount= (AppCompatEditText) findViewById(R.id.show_sel_branch_amount_edittext);


        //
        AppCompatButton buttonPay= (AppCompatButton) findViewById(R.id.show_sel_branch_pay_button);
        //////////////////
        getMerchantsVolley(false,false);
        Bundle bundle=getIntent().getExtras();
        if (bundle.getBoolean("IS_FROM_CAMERA"))
        {
            autoCompleteTextViewSearchBranch.setText(bundle.getString("PASS_MERCHANT_NAME"));
            autoCompleteTextViewSearchBranch.setSelection(autoCompleteTextViewSearchBranch.getText().length());
        }
        editTextAmount.setText(bundle.getString("AMOUNT_TO_PAY"));
        autoCompleteTextViewSearchBranch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (editTextAmount.getText().toString().isEmpty())
                {
                    editTextAmount.requestFocus();
                }else {
                    hideSoftInput(view);
                }
            }
        });

        imageViewSelFromList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Connectivity.isConnected(ShowSelBranch.this))
                {
                    getMerchantsVolley(true,false);
                }else {
                    CommFunc.NoInternetPrompt(ShowSelBranch.this);
                }
            }
        });
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateAll())
                {
                    payOutVolley();
                }
            }
        });

        editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (inputLayoutAmount.isErrorEnabled())
                {
                    inputLayoutAmount.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        autoCompleteTextViewSearchBranch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (inputLayoutSelectBranch.isErrorEnabled())
                {
                    inputLayoutSelectBranch.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void setAutoCompleteTextes()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getbranches());
        autoCompleteTextViewSearchBranch.setAdapter(adapter);
    }

    private ArrayList<String> getbranches() {
        ArrayList<String> strings=new ArrayList<String>();
        if (merchantsModels.size()>0)
        {
            for(int i=0;i<merchantsModels.size();i++)
            {
                strings.add(merchantsModels.get(i).get_name());
            }
        }

        return strings;
    }





    private void getMerchantsVolley(final boolean isForList, final boolean isForListWhilePayment)
    // isForListWhilePayment when arraylsit is empty while payment button
    {
        merchantsModels.clear();
        showProgressDialogGlobel(ShowSelBranch.this,"Please wait...");
        HashMap<String,String> map=new HashMap<>();
        map.put("action","getMerchantsList");
        SharedPreferences sharedPreferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        map.put("androidToken",sharedPreferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
        CustomJsonReq jsonReq=new CustomJsonReq(Request.Method.POST, ALLCONSTANTS.app_post_url, map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissProgressDialogGlobel();
                        if (response!=null)
                        {
                            try {
                                if (response.getBoolean("status"))
                                {
//                                    Toast.makeText(ShowSelBranch.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                                    JSONObject jsonObject=response.getJSONObject("data");
                                    JSONArray jsonArray=jsonObject.getJSONArray("merchants");
                                    for(int i=0;i<jsonArray.length();i++)
                                    {
                                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                        merchantsModels.add(new MerchantsModel(jsonObject1.getString("id"),jsonObject1.getString("name")));
                                    }
                                    if (isForList) {
                                        if (!isForListWhilePayment) {
                                            Intent intent = new Intent(ShowSelBranch.this, GetMerchatAct.class);
                                            intent.putExtra("LIST_OF_MERCHANTS", merchantsModels);
                                            startActivityForResult(intent, 1);
                                        }
                                        else {
                                            ///// arraylist of merchants was empty while payment
                                            payOutVolley();
                                        }
                                    }else {
                                        setAutoCompleteTextes(); ///// here/////
                                    }

                                }else {
                                    CommFunc.commonDialog(ShowSelBranch.this,"Error",response.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            CommFunc.NoInternetPrompt(ShowSelBranch.this);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialogGlobel();
                CommFunc.NoInternetPrompt(ShowSelBranch.this);
            }
        });
        jsonReq.setTag(VOLLEY_SHOW_SEL_BRANCH_GET_MERCHANTS);
        SingelTonVollyQueue.getInstance(ShowSelBranch.this).addToRequestQueue(jsonReq);
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1)
        {
            if (resultCode==1)
            {
                autoCompleteTextViewSearchBranch.setText(data.getStringExtra("MERCHANT_NAME"));
                autoCompleteTextViewSearchBranch.dismissDropDown();
                if (editTextAmount.getText().toString().isEmpty())
                {
                    editTextAmount.requestFocus();
                }else {
                    hideSoftInput(autoCompleteTextViewSearchBranch);
                }

            }
        }
    }

    private void payOutVolley()
    {

        if (merchantsModels.isEmpty())
        {
            getMerchantsVolley(true,true);  ///// fill the empty array list and come again after success... .

        }else {

            HashMap<String,String> mapForPost=new HashMap<>();
            mapForPost.put("action","payment");
            SharedPreferences sharedPreferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
            mapForPost.put("androidToken",sharedPreferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
            for(int i=0;i<merchantsModels.size();i++)
            {
                if (autoCompleteTextViewSearchBranch.getText().toString().equals(merchantsModels.get(i).get_name()))
                {
                    mapForPost.put("merchantId",merchantsModels.get(i).get_id());
                    break;
                }
            }
            mapForPost.put("amount",editTextAmount.getText().toString());
            if (getIntent().getExtras().getString("PROMO_APPLIED")!=null)
            {
                mapForPost.put("coupon",getIntent().getExtras().getString("PROMO_APPLIED"));
            }else {
                mapForPost.put("coupon","");
            }

            if (mapForPost.size()!=5)
            {
                Toast.makeText(ShowSelBranch.this, "Merchant Not available", Toast.LENGTH_SHORT).show();
                setIme();
            }else {
                showProgressDialogGlobel(ShowSelBranch.this,"Please wait...");
                CustomJsonReq jsonReq=new CustomJsonReq(Request.Method.POST, ALLCONSTANTS.app_post_url, mapForPost,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dismissProgressDialogGlobel();
                                System.out.println("yyyyyyyyyyyyyyyyy--------------response------------"+response.toString());

                                if (response!=null)
                                {
                                    try {
                                        if (response.getBoolean("status"))
                                        {
                                            JSONObject objectCheckIsPaid=response.getJSONObject("data");
                                            if (objectCheckIsPaid.getBoolean("isPaid"))
                                            {
                                                // when wallet has sufficient amount.... .
                                                Intent intent=new Intent(ShowSelBranch.this,TransStatusPayOut.class);
                                                intent.putExtra("message",response.getString("message"));
                                                intent.putExtra("amount",objectCheckIsPaid.getString("amount"));
                                                intent.putExtra("walletAmount",objectCheckIsPaid.getString("walletAmount"));
                                                intent.putExtra("merchantName",objectCheckIsPaid.getString("merchantName"));
                                                intent.putExtra("isPaid",objectCheckIsPaid.getBoolean("isPaid"));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                System.out.println("yyyyyyyyyyyyyyyyy--------------1------------"+response.getString("message"));
                                            }else {
                                                //// wehen wallet has low cash than bill
                                                System.out.println("yyyyyyyyyyyyyyyyy--------------2------------"+response.getString("message"));

                                                Toast.makeText(ShowSelBranch.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

                                                String makePostViaWebviewTOURl=objectCheckIsPaid.getString("formURL");
                                                JSONObject objectFormData=objectCheckIsPaid.getJSONObject("formData");
                                                //
                                                Intent intent=new Intent(ShowSelBranch.this,WebViewForPay.class);
                                                intent.putExtra("formURL",makePostViaWebviewTOURl);
                                                intent.putExtra("jsonObject",objectFormData.toString());
                                                intent.putExtra("PAY_OR_LOAD","PAY");
                                                startActivity(intent);
                                            }

                                        }else {

                                            if (response.has("isLoggedOut"))
                                            {
                                                if (response.getBoolean("isLoggedOut"))
                                                {
                                                    System.out.println("yyyyyyyyyyyyyyyyy--------------3------------"+response.getString("message"));

                                                    CommFunc.goToSignupCommFuncPrompt(ShowSelBranch.this);
                                                }
                                            }else {
                                                System.out.println("yyyyyyyyyyyyyyyyy--------------4------------"+response.getString("message"));

                                             CommFunc.commonDialog(ShowSelBranch.this,"Payment Error",response.getString("message"));
                                              setIme();
                                            }

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    CommFunc.NoInternetPrompt(ShowSelBranch.this);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissProgressDialogGlobel();

                    }
                });
                jsonReq.setTag(VOLLEY_SHOW_SEL_BRANCH_PAY_OUT);
                SingelTonVollyQueue.getInstance(ShowSelBranch.this).addToRequestQueue(jsonReq);
            }

        }
    }


    private boolean validateBranchName()
    {
        if (autoCompleteTextViewSearchBranch.getText().toString().trim().isEmpty())
        {
            inputLayoutSelectBranch.setError("PLEASE SELECT BRANCH NAME!!");
            requestFocusFromBaseActivity(autoCompleteTextViewSearchBranch);
            return false;
        }
        else
        {
            inputLayoutSelectBranch.setErrorEnabled(false);
            return true;
        }
    }
    //
    private boolean validateAmount()
    {
        if (editTextAmount.getText().toString().isEmpty())
        {
            inputLayoutAmount.setError("PLEASE ADD AMOUNT TO PAY!!");
            requestFocusFromBaseActivity(editTextAmount);
            return false;
        }else {
            inputLayoutAmount.setEnabled(false);
            return true;
        }
    }

    private boolean validateAll()
    {
        return validateBranchName() && validateAmount();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SingelTonVollyQueue.getInstance(ShowSelBranch.this).getRequestQueue().cancelAll(VOLLEY_SHOW_SEL_BRANCH_GET_MERCHANTS);
        SingelTonVollyQueue.getInstance(ShowSelBranch.this).getRequestQueue().cancelAll(VOLLEY_SHOW_SEL_BRANCH_PAY_OUT);
        dismissProgressDialogGlobel();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setIme();


    }
    private void setIme()
    {
        autoCompleteTextViewSearchBranch.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        autoCompleteTextViewSearchBranch.setSingleLine();
        editTextAmount.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextAmount.setSingleLine();
        editTextAmount.setEnabled(true);
    }
}
