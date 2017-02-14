package com.example.skilledanswers_d2.wallettransfer.loginactivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.skilledanswers_d2.wallettransfer.MainActivity;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    private AppCompatEditText editTextFname = null;
    private AppCompatEditText editTextLname = null;
    private AppCompatEditText editTextEmail = null;
    private AppCompatEditText editTextmobileNo = null;
    private AppCompatEditText editTextpwd = null;
    AppCompatTextView textView,loginmember;
    private TextInputLayout inputLayoutFname = null;
    private TextInputLayout inputLayoutLname = null;
    private TextInputLayout inputLayoutEmail = null;
    private TextInputLayout inputLayoutStatinIndianname = null;
    private TextInputLayout inputLayoutPwd = null;
    private ProgressDialog pDialog = null;
    Locale myLocale;
    String english;
    String hindi,kannada,telugu,tamil;
    //
    Configuration config ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        english = pref.getString("english","");
        hindi = pref.getString("hindi","");
        kannada = pref.getString("kannada","");
        tamil = pref.getString("tamil","");
        telugu = pref.getString("telugu","");
        editTextFname= (AppCompatEditText) findViewById(R.id.signUpActFirstNameEdittextID);
        editTextLname= (AppCompatEditText) findViewById(R.id.signUpActLastNameEdittextID);
        editTextEmail= (AppCompatEditText) findViewById(R.id.signUpActEmailEdittextID);
        editTextmobileNo= (AppCompatEditText) findViewById(R.id.signUpActPhNoEdittextID);
        editTextpwd= (AppCompatEditText) findViewById(R.id.signUpActpwdEdittext);
        textView=(AppCompatTextView) findViewById(R.id.member);
        loginmember=(AppCompatTextView) findViewById(R.id.loginmember);
        inputLayoutFname= (TextInputLayout) findViewById(R.id.signUpActFirstNameTextLayoutID);
        inputLayoutLname= (TextInputLayout) findViewById(R.id.signUpActLastNameTextLayoutID);
        inputLayoutEmail= (TextInputLayout) findViewById(R.id.signUpActEmailTextLayoutID);
        inputLayoutStatinIndianname= (TextInputLayout) findViewById(R.id.signUpActstaticTextLayoutID);
        inputLayoutPwd= (TextInputLayout) findViewById(R.id.signUpActPwdTextLayout);
        AppCompatButton appCompatButtonSignup= (AppCompatButton) findViewById(R.id.signUpActSignUpButtonID);
        AppCompatButton appCompatButtonClear= (AppCompatButton) findViewById(R.id.signUpCancleButtonID);
        ///
        LinearLayoutCompat textViewHaveAccount= (LinearLayoutCompat) findViewById(R.id.signUpActAlreadyAccountID);
        if(english.equalsIgnoreCase("1"))
        {
            Log.e("english-------",""+english);
            inputLayoutFname.setHint(
                    "First Name");
            inputLayoutLname.setHint("Last Name");
            inputLayoutEmail.setHint("Email");
            inputLayoutStatinIndianname.setHint("Mobile no");
            inputLayoutPwd.setHint("Password");
            appCompatButtonClear.setText("Clear");
            appCompatButtonSignup.setText("Sign up");
            textView.setText("Already a member ?");
            loginmember.setText("Login");
        }
       else if(hindi.equalsIgnoreCase("2"))
        {
            Log.e("hindi-------",""+hindi);
            inputLayoutLname.setHint(
                    "अंतिम नाम");
            inputLayoutFname.setHint("पहला नाम");
            inputLayoutEmail.setHint("ईमेल");
            inputLayoutStatinIndianname.setHint("मोबाइल नंबर");
            inputLayoutPwd.setHint("पासवर्ड");
            appCompatButtonClear.setText("स्पष्ट");
            appCompatButtonSignup.setText("साइन अप करें");
            textView.setText("पहले से सदस्य हैं ?");
            loginmember.setText("लॉग इन करें");

        }
       else if(kannada.equalsIgnoreCase("3"))
        {
            Log.e("kannada-----",""+kannada);
            inputLayoutFname.setHint(
                    "ಮೊದಲ ಹೆಸರು");
            inputLayoutLname.setHint("ಕೊನೆಯ ಹೆಸರು");
            inputLayoutEmail.setHint("ಇಮೇಲ್");
            inputLayoutStatinIndianname.setHint(
                    "ಮೊಬೈಲ್ ನಂಬರ");
            inputLayoutPwd.setHint("ಪಾಸ್ವರ್ಡ್");
            appCompatButtonClear.setText(
                    "ಸ್ಪಷ್ಟ");
            appCompatButtonSignup.setText("ಸೈನ್ ಅಪ್");
            textView.setText("ಈಗಾಗಲೇ ಸದಸ್ಯರೇ ?");
            loginmember.setText("ಲಾಗಿನ್");
        }
        else if(tamil.equalsIgnoreCase("4"))
        {
            Log.e("tamil-------",""+tamil);
            inputLayoutFname.setHint(
                    "முதல் பெயர்");
            inputLayoutLname.setHint("கடைசி பெயர்");
            inputLayoutEmail.setHint("மின்னஞ்சல்");
            inputLayoutStatinIndianname.setHint("மொபைல் எண்");
            inputLayoutPwd.setHint("கடவுச்சொல்");
            appCompatButtonClear.setText("தெளிவான");
            appCompatButtonSignup.setText("இணைவதற்குகு");
            textView.setText("ஏற்கனவே ஒரு உறுப்பினர் ?");
            loginmember.setText("உள் நுழை");
        }

      else if(telugu.equalsIgnoreCase("5"))
        {
            Log.e("telugu------",""+telugu);
            inputLayoutFname.setHint(
                    "మొదటి పేరు");
            inputLayoutLname.setHint("చివరి పేరు");
            inputLayoutEmail.setHint("ఇమెయిల");
            inputLayoutStatinIndianname.setHint("మొబైల్ సంఖ్య");
            inputLayoutPwd.setHint("పాస్వర్డ్");
            appCompatButtonClear.setText("స్పష్టమైన");
            appCompatButtonSignup.setText("చేరడం");
            textView.setText("ఇప్పటికే సభ్యులా ?");
            loginmember.setText("లాగిన్");

//            updateViews("hi");

        }




        ////////////////////////////////////////////////////////////////////////
        appCompatButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllData();
            }
        });
        appCompatButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isvalidateAllDetails())
            {
                if (Connectivity.isConnected(Signup.this)) {
                    new signupReqATask().execute(ALLCONSTANTS.app_post_url);
                }else {
                    CommFunc.NoInternetPrompt(Signup.this);
                }

            }


            }
        });
        textViewHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Signup.this,Login.class);
                startActivity(intent);
            }
        });


        editTextFname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (inputLayoutFname.isErrorEnabled())
                {
                    inputLayoutFname.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextLname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (inputLayoutLname.isErrorEnabled())
                {
                    inputLayoutLname.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        editTextmobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (inputLayoutStatinIndianname.isErrorEnabled())
                {
                    inputLayoutStatinIndianname.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextpwd.addTextChangedListener(new TextWatcher() {
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
 private class signupReqATask extends AsyncTask<String, Void, String>
 {
     String _fname=null;
     String _lname=null;
     String _mobileNo=null;
     String _email=null;
     String _pwd=null;
     ///
     String _signupReqResponse=null;

     @Override
     protected void onPreExecute() {
         super.onPreExecute();
         _fname=editTextFname.getText().toString();
         _lname=editTextLname.getText().toString();
         _mobileNo=editTextmobileNo.getText().toString();
         _email=editTextEmail.getText().toString();
         _pwd=editTextpwd.getText().toString();
         showProgressDialog();
     }


     @Override
     protected String doInBackground(String... strings) {
         String cookieValue = null;
         String[] fields;
         HashMap<String,String> mapPost=new HashMap<>();
         mapPost.put("action","register");
         mapPost.put("mobileNumber",_mobileNo);
         mapPost.put("firstName",_fname);
         mapPost.put("lastName",_lname);
         mapPost.put("email",_email);
         mapPost.put("password",_pwd);
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
             writer.write(CommFunc.getPostDataString(mapPost));
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
                 _signupReqResponse = builder.toString();
                 Log.e("response", _signupReqResponse);
             } else {
                 _signupReqResponse = "Server not responding try later";
             }
             connection.disconnect();
         } catch (IOException e) {
             e.printStackTrace();
         }
         return _signupReqResponse;
     }

     @Override
     protected void onPostExecute(String s) {
         super.onPostExecute(s);
         System.out.println("llllll---------------"+s);
         dismissProgressDialog();
         try {
             JSONObject object=new JSONObject(s);
             if (object.getBoolean("status"))
             {
                 Toast.makeText(Signup.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                 Intent intent=new Intent(Signup.this,Otp.class);
                 startActivity(intent);
             }else {
                 Toast.makeText(Signup.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
             }
         } catch (JSONException e) {
             e.printStackTrace();
         }

     }
     }


////////////////////////////////////////////////////////////////////////
    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(Signup.this);
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



    private void clearAllData()
    {
        editTextFname.setText("");
        editTextLname.setText("");
        editTextEmail.setText("");
        editTextmobileNo.setText("");
        editTextpwd.setText("");
        //
        inputLayoutFname.setErrorEnabled(false);
        inputLayoutLname.setErrorEnabled(false);
        inputLayoutEmail.setErrorEnabled(false);
        inputLayoutStatinIndianname.setErrorEnabled(false);
        inputLayoutPwd.setErrorEnabled(false);

    }


    private boolean validateFirstName()
    {
        if (editTextFname.getText().toString().trim().isEmpty())
        {
            inputLayoutFname.setError("FIRST NAME NEEDED!!");
            requestFocus(editTextFname);
            return false;
        }else
        {
            inputLayoutFname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateLastName()
    {
        if (editTextLname.getText().toString().trim().isEmpty())
        {
            inputLayoutLname.setError("LAST NAME NEEDED!!");
            requestFocus(editTextLname);
            return false;
        }else
        {
            inputLayoutLname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail()
    {
        if (editTextEmail.getText().toString().trim().isEmpty())
        {
            inputLayoutEmail.setError("EMAIL NEEDED!!");
            requestFocus(editTextEmail);
            return false;
        }else if (isValidEmail(editTextEmail.getText().toString().trim())){
            inputLayoutEmail.setErrorEnabled(false);
            return true;
        }else {
            inputLayoutEmail.setError("INVALID EMAIL!!");
            return false;
        }
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validateMobileNo()
    {
        if (editTextmobileNo.getText().toString().trim().isEmpty())
        {
            inputLayoutStatinIndianname.setError("MOBILE NO NEEDED!!");
            requestFocus(editTextmobileNo);
            return false;
        }else if (editTextmobileNo.getText().toString().trim().length()>10 ||
                editTextmobileNo.getText().toString().trim().length()<10){
            inputLayoutStatinIndianname.setError("INVALID MOBILE NO!!");
            requestFocus(editTextmobileNo);
            return false;
        }else {
            inputLayoutStatinIndianname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword()
    {
        if (editTextpwd.getText().toString().trim().isEmpty())
        {
            inputLayoutPwd.setError("PASSWORD NEEDED!!");
            requestFocus(editTextpwd);
            return false;
        }else if (editTextpwd.getText().toString().trim().length()<5){
            inputLayoutPwd.setError("PASSWORD SHOULD BE OF MIN 5 CHARACTER!!");
            requestFocus(editTextpwd);
            return false;
        }else {
            inputLayoutPwd.setErrorEnabled(false);
            return true;
        }
    }

    private boolean isvalidateAllDetails()
    {

        return validateFirstName() && validateLastName()
                && validateEmail() && validateMobileNo()
                && validatePassword() ;
    }
    /////// fuction for focusing the softinput when data is nopt given from the user
    protected void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    ///


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
