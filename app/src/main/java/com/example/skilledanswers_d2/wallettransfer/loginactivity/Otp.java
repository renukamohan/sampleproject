package com.example.skilledanswers_d2.wallettransfer.loginactivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;
import com.example.skilledanswers_d2.wallettransfer.constant.CommFunc;
import com.example.skilledanswers_d2.wallettransfer.constant.Connectivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Otp extends AppCompatActivity {


    private CountDownTimer countDownTimer=null;
    private AppCompatEditText editText1=null;
    private AppCompatEditText editText2=null;
    private AppCompatEditText editText3=null;
    private AppCompatEditText editText4=null;
    private AppCompatEditText editText5=null;
    private AppCompatEditText editText6=null;
    //
    private LinearLayoutCompat layoutCompatTimer=null;
    //
    private AppCompatTextView textViewTimeDisplay=null;
    //
    private LinearLayoutCompat layoutCompatResend=null;
    //
    private final long startTime = 30 * 1000;
    private final long interval = 1000;
    //
    private boolean isBroadcastRegistred=false;
    private ProgressDialog pDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        if (CommFunc.Smspermissioncheck(Otp.this))
        {
            CommFunc.reqForSmsPermisssion(Otp.this,Otp.this);
        }
        else {

            registerReceiver(broadcastReceiver,new IntentFilter("broadcastname"));
            isBroadcastRegistred=true;
        }
        editText1= (AppCompatEditText) findViewById(R.id.edittext1);
        editText2= (AppCompatEditText) findViewById(R.id.edittext2);
        editText3= (AppCompatEditText) findViewById(R.id.edittext3);
        editText4= (AppCompatEditText) findViewById(R.id.edittext4);
        editText5= (AppCompatEditText) findViewById(R.id.edittext5);
        editText6= (AppCompatEditText) findViewById(R.id.edittext6);
        //
        layoutCompatTimer= (LinearLayoutCompat) findViewById(R.id.otp_timer_layout_id);
        //
        textViewTimeDisplay= (AppCompatTextView) findViewById(R.id.otp_timer_text_id);
        //
        layoutCompatResend= (LinearLayoutCompat) findViewById(R.id.otp_resend_layout_id);
        //
        AppCompatButton buttonSubmit= (AppCompatButton) findViewById(R.id.submit_otp_button);
        //
        countDownTimer=new CountDownTimer(startTime,interval);
        countDownTimer.start();
        editText1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = editText1.getText().length();

                if (textlength1 == 1) {
                    editText2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }
        });
        editText2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength2 = editText2.getText().length();

                if (textlength2 == 1) {
                    editText3.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }
        });
        editText3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength3 = editText3.getText().length();

                if (textlength3 == 1) {
                    editText4.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }
        });
        editText4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength4 = editText4.getText().length();


                if (textlength4 == 1) {
                    editText5.requestFocus();


                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }
        });
        editText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer textlength5 = editText5.getText().length();


                if (textlength5== 1) {
                    editText6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editText6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer textlength6=editText6.getText().toString().length();
                if (textlength6 == 1)
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText4.getWindowToken(), 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected(Otp.this)) {
                    if (isAllEdittetxtFilled()) {
                        String enteredOtp = editText1.getText().toString().trim() +
                                editText2.getText().toString().trim() +
                                editText3.getText().toString().trim() +
                                editText4.getText().toString().trim() +
                                editText5.getText().toString().trim() +
                                editText6.getText().toString().trim();
                        new verifyOtp().execute(enteredOtp);

                    }
                }else {
                    CommFunc.NoInternetPrompt(Otp.this);
                }
            }
        });
        layoutCompatResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText("");editText2.setText("");editText3.setText("");
                editText4.setText("");editText5.setText("");editText6.setText("");
                new resendOtp().execute(ALLCONSTANTS.app_post_url);
            }
        });

    }
    private class verifyOtp extends AsyncTask<String, Void, String>
    {
        String _response=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> mapVerifyOtp=new HashMap<>();
            mapVerifyOtp.put("action","verifySMSOTP");
            mapVerifyOtp.put("otp",strings[0]);
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(""+ ALLCONSTANTS.app_post_url).openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(50000);
                connection.setReadTimeout(50000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.addRequestProperty("X-Requested-With","com.traknpay.wallet");
                SharedPreferences preferences=getSharedPreferences("SIGNUP_REQ_SESSION",MODE_PRIVATE);
                connection.setRequestProperty("cookie",preferences.getString("cookie",null));
                Log.e("cookie",preferences.getString("cookie",null));
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(CommFunc.getPostDataString(mapVerifyOtp));
                writer.flush();
                writer.close();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String line;
                    StringBuilder builder = new StringBuilder();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    _response = builder.toString();
                    Log.e("response", _response);
                } else {
                    _response = "Server not responding try later";
                }
                connection.disconnect();
            } catch (MalformedURLException | UnsupportedEncodingException | ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
            return _response;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dismissProgressDialog();
            try {
                JSONObject object=new JSONObject(s);
                if (object.getBoolean("status"))
                {
                    Toast.makeText(Otp.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Otp.this,Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else {
                    Toast.makeText(Otp.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class resendOtp extends AsyncTask<String, Void, String>
    {
        String _resonseResend=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }
        @Override
        protected String doInBackground(String... strings) {
            String cookieValue = null;
            String[] fields;
            HashMap<String,String> mapResendOtp=new HashMap<>();
            mapResendOtp.put("action","resendSMSOTP");
            try {

                HttpURLConnection connection = (HttpURLConnection) new URL(""+strings[0]).openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(50000);
                connection.setReadTimeout(50000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.addRequestProperty("X-Requested-With","com.traknpay.wallet");
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(CommFunc.getPostDataString(mapResendOtp));
                writer.flush();
                writer.close();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    // get cookies form header
                    Map<String, List<String>> headerFields = connection.getHeaderFields();

                    Set<String> headerFieldsSet = headerFields.keySet();
                    Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();
                    while (hearerFieldsIter.hasNext()) {

                        String headerFieldKey = hearerFieldsIter.next();

                        if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {

                            List<String> headerFieldValue = headerFields.get(headerFieldKey);

                            for (String headerValue : headerFieldValue) {


                                fields = headerValue.split(";/s*");

                                cookieValue = fields[0];
                                String expires = null;
                                String path = null;
                                String domain = null;
                                boolean secure = false;

                                // Parse each field
                                for (int j = 1; j < fields.length; j++) {
                                    if ("secure".equalsIgnoreCase(fields[j])) {
                                        secure = true;
                                    } else if (fields[j].indexOf('=') > 0) {
                                        String[] f = fields[j].split("=");
                                        if ("expires".equalsIgnoreCase(f[0])) {
                                            expires = f[1];
                                        } else if ("domain".equalsIgnoreCase(f[0])) {
                                            domain = f[1];
                                        } else if ("path".equalsIgnoreCase(f[0])) {
                                            path = f[1];
                                        }
                                    }

                                }
                            }

                        }

                    }
                    SharedPreferences preferences = getSharedPreferences("SIGNUP_REQ_SESSION", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("cookie", cookieValue);
                    editor.apply();

                    String line;
                    StringBuilder builder = new StringBuilder();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    _resonseResend = builder.toString();
                    Log.e("response", _resonseResend);
                } else {
                    _resonseResend = "Server not responding try later";
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dismissProgressDialog();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getBoolean("status"))
                {
                    countDownTimer.start();
                    Toast.makeText(Otp.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Otp.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    private boolean isAllEdittetxtFilled()
    {
        return !(editText1.getText().toString().isEmpty() ||
                editText2.getText().toString().isEmpty() ||
                editText3.getText().toString().isEmpty() ||
                editText4.getText().toString().isEmpty() ||
                editText5.getText().toString().isEmpty() ||
                editText6.getText().toString().isEmpty());
    }
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundleFromReciver=intent.getExtras();
            String otp=bundleFromReciver.getString("messge");
            if (otp != null) {
                for(int i=0;i<otp.length();i++)
                {
                    int j=Character.digit(otp.charAt(i),10);
                    switch (i)
                    {
                        case 0:editText1.setText(""+j);
                            break;
                        case 1:editText2.setText(""+j);
                            break;
                        case 2:editText3.setText(""+j);
                            break;
                        case 3:editText4.setText(""+j);
                            break;
                        case 4:editText5.setText(""+j);
                            break;
                        case 5:editText6.setText(""+j);
                    }
                }
                if (Connectivity.isConnected(Otp.this)) {
                    new verifyOtp().execute(otp);
                }else {
                    CommFunc.NoInternetPrompt(Otp.this);
                }

            }
        }
    };
    public class CountDownTimer extends android.os.CountDownTimer
    {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textViewTimeDisplay.setText(""+millisUntilFinished/1000);

        }

        @Override
        public void onFinish() {
            layoutCompatTimer.setVisibility(View.GONE);
            layoutCompatResend.setVisibility(View.VISIBLE);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

//        Toast.makeText(this, ""+PackageManager.PERMISSION_GRANTED, Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case 1:
                if ((grantResults[0] +
                        grantResults[1]  ) == PackageManager.PERMISSION_GRANTED) {
                    registerReceiver(broadcastReceiver,new IntentFilter("broadcastname"));
                    isBroadcastRegistred=true;
                }else {
                    Toast.makeText(this, "You have not granted us SMS permission... please enter OTP manually", Toast.LENGTH_SHORT).show();
                }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBroadcastRegistred)
        {
            unregisterReceiver(broadcastReceiver);
        }
    }

    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(Otp.this);
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

}
