package com.example.skilledanswers_d2.wallettransfer.flowactivities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.CustomJsonReq;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.SingelTonVollyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MyProfile extends BaseActivity {
    private AppCompatTextView textviewFName =null;
    private AppCompatTextView textViewOld =null;
    private AppCompatTextView textViewNew =null;
    private AppCompatTextView textViewConfirm =null;
    private AppCompatTextView textviewLname =null;
    private AppCompatTextView myProfileFName =null;
    private AppCompatTextView myProfileLName =null;
    private AppCompatEditText editTextOldPwd=null;
    private AppCompatEditText editTextNewPwd=null;
    private AppCompatEditText editTextConfirm=null;
    private AppCompatTextView textViewProfile=null;
    AppCompatButton buttonEditProfile;
    AppCompatButton buttonChangePwd;
    private TextInputLayout inputLayoutEditFname =null;
    private TextInputLayout inputLayoutEditLname =null;
    private AppCompatEditText editTextEditFname =null;
    private AppCompatEditText editTextEditLname =null;
    String hindi,kannada,telugu,tamil,english;
    //
    private Dialog dialogEditProfile =null;
    private ProgressDialog pDialog=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.content_base);
        getLayoutInflater().inflate(R.layout.activity_my_profile,relativeLayout);
        //
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performBack();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textviewFName = (AppCompatTextView) findViewById(R.id.my_profile_fname);
        textviewLname = (AppCompatTextView) findViewById(R.id.my_profile_lname);
        myProfileFName=(AppCompatTextView)findViewById(R.id.myprofilefristname);
        myProfileLName=(AppCompatTextView)findViewById(R.id.myprofilelastname);
        textViewProfile=(AppCompatTextView)findViewById(R.id.MyProfileTextView);
        buttonEditProfile = (AppCompatButton) findViewById(R.id.my_profile_edit_profile_button);
        buttonChangePwd= (AppCompatButton) findViewById(R.id.my_profile_change_pwd);
        textViewOld=(AppCompatTextView)findViewById(R.id.old);
        textViewNew=(AppCompatTextView)findViewById(R.id.newtext);
        textViewConfirm=(AppCompatTextView)findViewById(R.id.confirm);
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

        SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        textviewFName.setText(preferences.getString(ALLCONSTANTS.SESSION_KEY_FNAME1,null));
        textviewLname.setText(preferences.getString(ALLCONSTANTS.SESSION_KEY_LNAME1,null));
        //
        editTextOldPwd= (AppCompatEditText) findViewById(R.id.my_profile_pwd_old);
        editTextNewPwd= (AppCompatEditText) findViewById(R.id.my_profile_pwd_new);
        editTextConfirm= (AppCompatEditText) findViewById(R.id.my_profile_pwd_confirm);
        //
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfileDailog(textviewFName.getText().toString(), textviewLname.getText().toString());
            }
        });
        buttonChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordChangeValidated())
                {
                    showProgressDialog();
                    changePwdVolley(editTextOldPwd.getText().toString(),
                            editTextConfirm.getText().toString());
                }
            }
        });
        editTextConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editTextConfirm.getText().toString().equals(editTextNewPwd.getText().toString()))
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        editTextConfirm.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.pwd_match,null),null);
                        editTextNewPwd.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.pwd_match,null),null);
                    }
                    else {
                        editTextConfirm.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.pwd_match),null);
                        editTextNewPwd.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.pwd_match),null);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void editProfileDailog(final String fname, String lname)
    {
        dialogEditProfile =new Dialog(MyProfile.this);
        dialogEditProfile.setCancelable(true);
        dialogEditProfile.setCanceledOnTouchOutside(true);
        dialogEditProfile.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEditProfile.setContentView(R.layout.edit_profile);
        inputLayoutEditFname = (TextInputLayout) dialogEditProfile.findViewById(R.id.edit_profile_input_layout_fname);
        inputLayoutEditLname = (TextInputLayout) dialogEditProfile.findViewById(R.id.edit_profile_input_layout_lname);
        editTextEditFname = (AppCompatEditText) dialogEditProfile.findViewById(R.id.edit_profile_edittext_fname);
        editTextEditLname = (AppCompatEditText) dialogEditProfile.findViewById(R.id.edit_profile_edittext_lname);
        editTextEditFname.setText(fname);
        editTextEditLname.setText(lname);
        editTextEditFname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (inputLayoutEditFname.isErrorEnabled())
                {
                    inputLayoutEditFname.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextEditLname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (inputLayoutEditLname.isErrorEnabled())
                {
                    inputLayoutEditLname.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        AppCompatButton buttonSaveChanges = (AppCompatButton) dialogEditProfile.findViewById(R.id.edit_profile_save_changes);
        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAllEdited())
                {
                    showProgressDialog();
                    changeNameVolley(editTextEditFname.getText().toString().trim()
                            , editTextEditLname.getText().toString().trim());
                }
            }
        });
        dialogEditProfile.show();

    }
    private void changePwdVolley(String oldPwd,String newPwd)
    {
        HashMap<String,String> mapChangePwd=new HashMap<>();
        mapChangePwd.put("action","changePassword");
        SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        System.out.println("qqqqqqqqqqq---key-----"+preferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
        mapChangePwd.put("androidToken",preferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
        mapChangePwd.put("password",oldPwd);
        mapChangePwd.put("newPassword",newPwd);
        mapChangePwd.put("confirmNewPassword",newPwd);
        CustomJsonReq jsonReqChangePwd=new CustomJsonReq(Request.Method.POST, ALLCONSTANTS.app_post_url,
                mapChangePwd, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    if (response.getBoolean("status"))
                    {
                        Toast.makeText(MyProfile.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MyProfile.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                        if (response.getBoolean("isLoggedOut"))
                        {
                            CommFunc.goToSignupCommFunc(MyProfile.this);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(MyProfile.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        jsonReqChangePwd.setTag("jsonReqChangePwd");
        SingelTonVollyQueue.getInstance(MyProfile.this).addToRequestQueue(jsonReqChangePwd);


    }
    private void changeNameVolley(final String changedFname, final String changedLname)
    {
        HashMap<String,String> mapChangeName=new HashMap<>();
        mapChangeName.put("action","updateName");
        final SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        mapChangeName.put("androidToken",preferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
        mapChangeName.put("firstName",changedFname);
        mapChangeName.put("lastName",changedLname);
        CustomJsonReq customJsonReqEditProfile=new CustomJsonReq(Request.Method.POST, ALLCONSTANTS.app_post_url, mapChangeName,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissProgressDialog();
                        dialogEditProfile.dismiss();
                        try {
                            if (response.getBoolean("status"))
                            {
                                Toast.makeText(MyProfile.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString(ALLCONSTANTS.SESSION_KEY_FNAME1,changedFname);
                                editor.putString(ALLCONSTANTS.SESSION_KEY_LNAME1,changedLname);
                                editor.apply();
                                textViewNavHeaderName.setText(preferences.getString(ALLCONSTANTS.SESSION_KEY_FNAME1,null)+" "
                                        +preferences.getString(ALLCONSTANTS.SESSION_KEY_LNAME1,null));
                                textviewFName.setText(preferences.getString(ALLCONSTANTS.SESSION_KEY_FNAME1,null));
                                textviewLname.setText(preferences.getString(ALLCONSTANTS.SESSION_KEY_LNAME1,null));

                            }
                            else {
                                Toast.makeText(MyProfile.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                                if (response.getBoolean("isLoggedOut"))
                                {
                                    CommFunc.goToSignupCommFunc(MyProfile.this);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                dialogEditProfile.dismiss();
                Toast.makeText(MyProfile.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        customJsonReqEditProfile.setTag("customJsonReqEditProfile");
        SingelTonVollyQueue.getInstance(MyProfile.this).addToRequestQueue(customJsonReqEditProfile);
    }

    ////////////////////////////////////////////////////////////////////////////



    private boolean validateEditedFName()
    {
        if (editTextEditFname.getText().toString().trim().isEmpty())
        {
            inputLayoutEditFname.setError("FIRST NAME NEEDED!!");
            requestFocusFromBaseActivity(editTextEditFname);
            return false;
        }else
        {
            inputLayoutEditFname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEditedLName()
    {
        if (editTextEditLname.getText().toString().trim().isEmpty())
        {
            inputLayoutEditLname.setError("LAST NAME NEEDED!!");
            requestFocusFromBaseActivity(editTextEditLname);
            return false;
        }else
        {
            inputLayoutEditLname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateAllEdited()
    {
        return validateEditedFName() && validateEditedLName() ;
    }
    ////////////////////
    private boolean validateOldPwd()
    {
        if (editTextOldPwd.getText().toString().isEmpty())
        {
            Toast.makeText(MyProfile.this, "Old password field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    private boolean validateNewPwd()
    {
        if (editTextNewPwd.getText().toString().isEmpty())
        {
            Toast.makeText(MyProfile.this, "New password field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    private boolean validateConfirmPwd()
    {
        if (editTextConfirm.getText().toString().isEmpty())
        {
            Toast.makeText(MyProfile.this, "Confirm password field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!editTextConfirm.getText().toString().equals(editTextNewPwd.getText().toString()))
        {
            Toast.makeText(MyProfile.this, "Password miss-match", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    private boolean isPasswordChangeValidated()
    {
        return validateOldPwd() && validateNewPwd() && validateConfirmPwd();
    }
    @Override
    public void onBackPressed() {
        performBack();
    }
    private void performBack()
    {
        if (isTaskRoot())
        {
            Intent intentLoadCash=new Intent(MyProfile.this, LoadOrPay.class);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentLoadCash);
        }else {
            finish();
        }
    }
    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(MyProfile.this);
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

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        myProfileFName.setText(resources.getString(R.string.FirstName));
        myProfileLName.setText(resources.getString(R.string.LastName));
        textViewOld.setText(resources.getString(R.string.Old));
        textViewNew.setText(resources.getString(R.string.New));
        textViewConfirm.setText(resources.getString(R.string.Confirm));
        buttonEditProfile.setText(resources.getString(R.string.exitprofile));
        buttonChangePwd.setText(resources.getString(R.string.changepassword));
        textViewProfile.setText(resources.getString(R.string.myprofile));

    }
}
