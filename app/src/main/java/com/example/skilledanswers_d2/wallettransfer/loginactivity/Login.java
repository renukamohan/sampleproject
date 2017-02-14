package com.example.skilledanswers_d2.wallettransfer.loginactivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.skilledanswers_d2.wallettransfer.PassCodeSkip;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;
import com.example.skilledanswers_d2.wallettransfer.constant.CommFunc;
import com.example.skilledanswers_d2.wallettransfer.constant.Connectivity;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.CustomJsonReq;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.SingelTonVollyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    private TextInputLayout inputLayoutEmail=null;
    private TextInputLayout inputLayoutPwd=null;
    private AppCompatEditText editTextEmail=null;
    private AppCompatEditText editTextPwd=null;
    private ProgressDialog pDialog=null;
    String english;
    String hindi,kannada,telugu,tamil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        english = pref.getString("english","");
        hindi = pref.getString("hindi","");
        kannada = pref.getString("kannada","");
        telugu = pref.getString("telugu","");
        tamil = pref.getString("tamil","");
        inputLayoutEmail= (TextInputLayout) findViewById(R.id.login_activity_emailTextInputLayoutID);
        inputLayoutPwd= (TextInputLayout) findViewById(R.id.login_activity_pwdTextInputLayoutID);
        editTextEmail= (AppCompatEditText) findViewById(R.id.login_activity_emailEdittextID);
        editTextPwd= (AppCompatEditText) findViewById(R.id.login_activity_pwdEdittextID);
        AppCompatTextView appCompatTextViewForgotPwd = (AppCompatTextView)
                findViewById(R.id.login_activity_forgotPwdTextviewButtonID);
        AppCompatButton buttonLogin = (AppCompatButton)
                findViewById(R.id.login_activity_loginButtonID);
        if(english.equalsIgnoreCase("1"))
        {
            Log.e("english",""+english);

            inputLayoutEmail.setHint("Mobile no");
            inputLayoutPwd.setHint("Password");
            appCompatTextViewForgotPwd.setText("Forgot Password");
            buttonLogin.setText("LOGIN");

        }
        else if(hindi.equalsIgnoreCase("2"))
        {
            Log.e("hindi",""+hindi);

            inputLayoutEmail.setHint("मोबाइल नंबर");
            inputLayoutPwd.setHint("पासवर्ड");
            appCompatTextViewForgotPwd.setText("पासवर्ड भूल गए");
            buttonLogin.setText("लॉग इन करें");
        }
        else if(kannada.equalsIgnoreCase("3"))
        {
            Log.e("kannada",""+kannada);

            inputLayoutEmail.setHint("ಮೊಬೈಲ್ ನಂಬರ");
            inputLayoutPwd.setHint("ಪಾಸ್ವರ್ಡ್");
            appCompatTextViewForgotPwd.setText("ಪಾಸ್ವರ್ಡ್ ಮರೆತಿರಾ");
            buttonLogin.setText("ಲಾಗಿನ್");
        }
        else if(tamil.equalsIgnoreCase("4"))
        {
            Log.e("tamil",""+tamil);

            inputLayoutEmail.setHint("மொபைல் எண்");
            inputLayoutPwd.setHint("கடவுச்சொல்");
            appCompatTextViewForgotPwd.setText("கடவுச்சொல்லை மறந்துவிட்டேன்");
            buttonLogin.setText("உள் நுழை");
        }
        else if(telugu.equalsIgnoreCase("5"))
        {
            Log.e("telugu",""+telugu);

            inputLayoutEmail.setHint("మొబైల్ సంఖ్య");
            inputLayoutPwd.setHint("పాస్వర్డ్");
            appCompatTextViewForgotPwd.setText("పాస్వర్డ్ మరిచిపోయారా");
            buttonLogin.setText("లాగిన్");

        }
        appCompatTextViewForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected(Login.this))
                {
                    forgetPwdDialog();
                }else {
                    CommFunc.NoInternetPrompt(Login.this);
                }
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            Intent intent=new Intent(Login.this, LoadCash.class);
//                startActivity(intent);
                if (Connectivity.isConnected(Login.this)) {
                    if (validateAll()) {
                        showProgressDialog();
                        LoginVolley();
                    }
                }else {
                    CommFunc.NoInternetPrompt(Login.this);
                }
            }
        });
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (inputLayoutEmail.isErrorEnabled())
                {
                    inputLayoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (inputLayoutPwd.isErrorEnabled())
                {
                    inputLayoutPwd.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void LoginVolley()
    {
        HashMap<String,String> mapLogin =new HashMap<>();
        mapLogin.put("action","login");
        mapLogin.put("mobileNumber",editTextEmail.getText().toString().trim());
        mapLogin.put("password",editTextPwd.getText().toString());

        CustomJsonReq customJsonReqForLogin=new CustomJsonReq(Request.Method.POST,
                ALLCONSTANTS.app_post_url, mapLogin, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    Toast.makeText(Login.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    if (response.getBoolean("status"))
                    {
                        Log.e("Response","xxxxxxxxxxxxxxx--------"+response);
                        JSONObject objectDate=response.getJSONObject("data");
                        SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,
                                MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString(ALLCONSTANTS.SESSION_KEY_FNAME1,objectDate.getString("firstName"));
                        editor.putString(ALLCONSTANTS.SESSION_KEY_LNAME1,objectDate.getString("lastName"));
                        editor.putString(ALLCONSTANTS.SESSION_KEY_TOKEN1,objectDate.getString("androidToken"));
                        Log.e("sjnjansjsandjsad",""+objectDate.getString("androidToken"));
                        editor.putString(ALLCONSTANTS.SESSION_KEY_WALLET1,objectDate.getString("walletAmount"));
                        editor.putString(ALLCONSTANTS.SESSION_KEY_MOBILE1,objectDate.getString("mobileNumber"));
                        editor.putString(ALLCONSTANTS.SESSION_KEY_EMAIL1,objectDate.getString("email"));
                        editor.apply();
                        Intent intent=new Intent(Login.this,PassCodeSkip.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                System.out.println("iiiiiiiiiiii------log error--"+error);

            }
        });
        customJsonReqForLogin.setTag("customJsonReqForLogin");
        SingelTonVollyQueue.getInstance(Login.this).addToRequestQueue(customJsonReqForLogin);
    }
    private void forgetPwdVolley(String _phnoTOReset)
    {
        HashMap<String,String> mapForgetPwd=new HashMap<>();
        mapForgetPwd.put("action","forgotPassword");
        mapForgetPwd.put("mobileNumber",_phnoTOReset);
        CustomJsonReq customJsonReqFrgtPwd=new CustomJsonReq(Request.Method.POST,
                ALLCONSTANTS.app_post_url, mapForgetPwd, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    if (response.getBoolean("status")){
                        Toast.makeText(Login.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Login.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(Login.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        customJsonReqFrgtPwd.setTag("customJsonReqFrgtPwd");
        SingelTonVollyQueue.getInstance(Login.this).addToRequestQueue(customJsonReqFrgtPwd);
    }
    private void forgetPwdDialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(Login.this);
        edittext.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        InputFilter[] inputFilters=new InputFilter[1];
        inputFilters[0]=  new InputFilter.LengthFilter(10);
        edittext.setFilters(inputFilters);
        edittext.setLines(1);
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edittext.getText().length()==10)
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        alert.setMessage("Please enter your Registered mobile number.");
        alert.setIcon(android.R.drawable.ic_partial_secure);
        alert.setCancelable(false);
        alert.setTitle("Password Reset.");
        alert.setView(edittext);
        alert.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (edittext.getText().toString().length()==10 &&
                        edittext.getText().toString().matches("[0-9]+")) {
                    showProgressDialog();
                    forgetPwdVolley(edittext.getText().toString().trim());
                    dialogInterface.dismiss();
                }else {
                    if (edittext.getText().toString().length()<10)
                    {
                        Toast.makeText(Login.this, "Mobile no is too short..", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Login.this, "Invalid mobile no", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            }
        });
        alert.show();


    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(Login.this);
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

    private boolean validateMobileNo()
    {
        if (editTextEmail.getText().toString().trim().isEmpty())
        {
            inputLayoutEmail.setError("MOBILE NO NEEDED!!");
            requestFocus(editTextEmail);
            return false;
        }else if (editTextEmail.getText().toString().trim().length()>10 ||
                editTextEmail.getText().toString().trim().length()<10){
            inputLayoutEmail.setError("INVALID MOBILE NO!!");
            requestFocus(editTextEmail);
            return false;
        }else {
            inputLayoutEmail.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePwd()
    {
        if (editTextPwd.getText().toString().trim().isEmpty())
        {
            inputLayoutPwd.setError("PLEASE PROVIDE PASSWORD!!");
            requestFocus(editTextPwd);
            return false;
        }else {
            inputLayoutPwd.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateAll()
    {
        return validateMobileNo() && validatePwd();
    }
    protected void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot())
        {
            Intent intent=new Intent(Login.this,Signup.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            finish();
        }
    }
}
