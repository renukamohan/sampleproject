package com.example.skilledanswers_d2.wallettransfer.flowactivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.skilledanswers_d2.wallettransfer.BaseActivity;
import com.example.skilledanswers_d2.wallettransfer.LocaleHelper;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.ServerConnection;
import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;
import com.example.skilledanswers_d2.wallettransfer.constant.CommFunc;
import com.example.skilledanswers_d2.wallettransfer.constant.Connectivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ContactUs extends BaseActivity {
    private AppCompatEditText editTextSubject=null;
    private AppCompatEditText editTextMessageBody=null;
    private ProgressDialog pDialog = null;
    String hindi,kannada,telugu,tamil,english;
    AppCompatButton buttonSubmit;
    AppCompatTextView appCompatTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.content_base);
        getLayoutInflater().inflate(R.layout.activity_contact_us,relativeLayout);
        editTextSubject= (AppCompatEditText) findViewById(R.id.contact_us_subject);
        editTextMessageBody= (AppCompatEditText) findViewById(R.id.contact_us_body);
        buttonSubmit= (AppCompatButton) findViewById(R.id.contact_us_submit);
        appCompatTextView=(AppCompatTextView)findViewById(R.id.contactusid);
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performBack();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextSubject.getText().toString().isEmpty() ||
                        editTextMessageBody.getText().toString().isEmpty())
                {
                    if (editTextSubject.getText().toString().isEmpty())
                    {
                        Toast.makeText(ContactUs.this, "Empty Subject", Toast.LENGTH_SHORT).show();
                    }
                    else if (editTextMessageBody.getText().toString().isEmpty())
                    {
                        Toast.makeText(ContactUs.this, "Empty Message Body", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ContactUs.this, "Nothing to send", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (Connectivity.isConnected(ContactUs.this))
                    {
//                        submitContactUsVolley(editTextSubject.getText().toString(),
//                                editTextMessageBody.getText().toString());
                        new SubmitContactUs().execute();
                    }
                    else {
                        CommFunc.NoInternetPrompt(ContactUs.this);
                    }
                }
            }
        });
    }
    private class SubmitContactUs extends AsyncTask<String, Void, String>
    {

        String _sub=null;
        String _body=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _sub=editTextSubject.getText().toString();
            _body=editTextMessageBody.getText().toString();
            showProgressDialog();
        }



        @Override
        protected String doInBackground(String... strings) {
                    HashMap<String,String> mapcontactUs=new HashMap<>();
        mapcontactUs.put("action","sendContactUsMessage");
        SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        mapcontactUs.put("mobileNumber",preferences.getString(ALLCONSTANTS.SESSION_KEY_MOBILE1,null));
        System.out.println("aaaaaaaaaaaaa---SESSION_KEY_MOBILE--------------"+preferences.getString(ALLCONSTANTS.SESSION_KEY_MOBILE1,null));

        mapcontactUs.put("name",preferences.getString(ALLCONSTANTS.SESSION_KEY_FNAME1,null)+" "+

        preferences.getString(ALLCONSTANTS.SESSION_KEY_LNAME1,null));
        System.out.println("aaaaaaaaaaaaa---NAME--------------"+preferences.getString(ALLCONSTANTS.SESSION_KEY_FNAME1,null)+" "+

                preferences.getString(ALLCONSTANTS.SESSION_KEY_LNAME1,null));

        mapcontactUs.put("subject",_sub);
        System.out.println("aaaaaaaaaaaaa---_sub------------"+_sub);

        mapcontactUs.put("email",preferences.getString(ALLCONSTANTS.SESSION_KEY_EMAIL1,null));
        System.out.println("aaaaaaaaaaaaa---SESSION_KEY_EMAIL-------------"+preferences.getString(ALLCONSTANTS.SESSION_KEY_EMAIL1,null));

        mapcontactUs.put("message",_body);
        System.out.println("aaaaaaaaaaaaa---_body-----------"+_body);
           return ServerConnection.performPostCall(ALLCONSTANTS.app_post_url,mapcontactUs);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dismissProgressDialog();
            System.out.println("oooooooooooooooo------------"+s);
            try {
                JSONObject object=new JSONObject(s);
                if (object.getBoolean("status"))
                {
                    Toast.makeText(ContactUs.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                    editTextSubject.setText("");
                    editTextMessageBody.setText("");
                }else {
                    Toast.makeText(ContactUs.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

//    private void submitContactUsVolley(String _sub,String _body)
//    {
//        showProgressDialog();
//        HashMap<String,String> mapcontactUs=new HashMap<>();
//        mapcontactUs.put("action","sendContactUsMessage");
//        SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME,MODE_PRIVATE);
//        mapcontactUs.put("mobileNumber",preferences.getString(ALLCONSTANTS.SESSION_KEY_MOBILE,null));
//        System.out.println("aaaaaaaaaaaaa---SESSION_KEY_MOBILE--------------"+preferences.getString(ALLCONSTANTS.SESSION_KEY_MOBILE,null));
//
//        mapcontactUs.put("name",preferences.getString(ALLCONSTANTS.SESSION_KEY_FNAME,null)+" "+
//
//        preferences.getString(ALLCONSTANTS.SESSION_KEY_LNAME,null));
//        System.out.println("aaaaaaaaaaaaa---NAME--------------"+preferences.getString(ALLCONSTANTS.SESSION_KEY_FNAME,null)+" "+
//
//                preferences.getString(ALLCONSTANTS.SESSION_KEY_LNAME,null));
//
//        mapcontactUs.put("subject",_sub);
//        System.out.println("aaaaaaaaaaaaa---_sub------------"+_sub);
//
//        mapcontactUs.put("email",preferences.getString(ALLCONSTANTS.SESSION_KEY_EMAIL,null));
//        System.out.println("aaaaaaaaaaaaa---SESSION_KEY_EMAIL-------------"+preferences.getString(ALLCONSTANTS.SESSION_KEY_EMAIL,null));
//
//        mapcontactUs.put("message",_body);
//        System.out.println("aaaaaaaaaaaaa---_body-----------"+_body);
//
//        CustomJsonReq jsonReqCOntactUs=new CustomJsonReq(Request.Method.POST, ALLCONSTANTS.app_post_url, mapcontactUs,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        dismissProgressDialog();
//                        System.out.println("aaaaaaaaaaaaa---contact us--------------"+response);
//                        try {
//                            if (response.getBoolean("status"))
//                            {
//                                Toast.makeText(ContactUs.this, ""+response.getString("message"),
//                                        Toast.LENGTH_SHORT).show();
//                            }else {
//                                Toast.makeText(ContactUs.this, ""+response.getString("message"),
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                dismissProgressDialog();
//                String body;
//                //get status code here
//
//                //get response body and parse with appropriate encoding
//                if(error.networkResponse.data!=null) {
//                    String statusCode = String.valueOf(error.networkResponse.statusCode);
//                    try {
//                        body = new String(error.networkResponse.data,"UTF-8");
//                        System.out.println("kkkkkkkkkkkkk-------error----"+body);
//                        System.out.println("kkkkkkkkkkkkk-------statusCode----"+statusCode);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        jsonReqCOntactUs.setTag("jsonReqCOntactUs");
//        SingelTonVollyQueue.getInstance(ContactUs.this).addToRequestQueue(jsonReqCOntactUs);
//    }
///////////////////////////////////////////////
    @Override
    public void onBackPressed() {

        performBack();
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    private void performBack()
    {
        if (isTaskRoot())
        {
            Intent intentLoadCash=new Intent(ContactUs.this, LoadOrPay.class);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentLoadCash);
        }
        else {
            finish();
        }
    }
    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(ContactUs.this);
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
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        editTextSubject.setText(resources.getString(R.string.subject));
        editTextMessageBody.setText(resources.getString(R.string.message1));
        buttonSubmit.setText(resources.getString(R.string.send));
        appCompatTextView.setText(resources.getString(R.string.contactus));


    }
}
