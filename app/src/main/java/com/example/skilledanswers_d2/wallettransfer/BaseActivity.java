package com.example.skilledanswers_d2.wallettransfer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;
import com.example.skilledanswers_d2.wallettransfer.constant.CommFunc;
import com.example.skilledanswers_d2.wallettransfer.constant.CustomTypefaceSpan;
import com.example.skilledanswers_d2.wallettransfer.flowactivities.About_Us;
import com.example.skilledanswers_d2.wallettransfer.flowactivities.ContactUs;
import com.example.skilledanswers_d2.wallettransfer.flowactivities.LoadOrPay;
import com.example.skilledanswers_d2.wallettransfer.flowactivities.MyProfile;
import com.example.skilledanswers_d2.wallettransfer.flowactivities.Transaction;
import com.example.skilledanswers_d2.wallettransfer.flowactivities.WalletActivity;

;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    protected AppCompatTextView textViewToolbarAmountTextview=null;
    protected AppCompatTextView textViewNavHeaderName=null;
    //
    protected DrawerLayout drawer=null;
    protected ActionBarDrawerToggle toggle=null;
    protected Toolbar toolbar=null;
    String hindi,kannada,telugu,tamil,english;
    Menu menu;
    protected ProgressDialog pDialogGlobel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu=navigationView.getMenu();
        for(int i=0;i<menu.size();i++)
        {
            MenuItem mi = menu.getItem(i);


            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);
        ///
        textViewToolbarAmountTextview= (AppCompatTextView) findViewById(R.id.toolbar_amount_textview);
        ImageView imageViewToolbarImageView= (ImageView) findViewById(R.id.toolbar_imageview);
        textViewNavHeaderName= (AppCompatTextView) headerView.findViewById(R.id.nav_header_name_id);
        AppCompatTextView textViewHeaderMobile= (AppCompatTextView) headerView.findViewById(R.id.nav_header_mobile_id);
        AppCompatTextView textViewHeaderEmail= (AppCompatTextView) headerView.findViewById(R.id.nav_header_email_id);

        SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textViewToolbarAmountTextview.setText(Html.fromHtml("&#x20B9; ")+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,
                    null));
        }else {
            textViewToolbarAmountTextview.setText(Html.fromHtml("&#x20B9; ")+preferences.getString(ALLCONSTANTS.SESSION_KEY_WALLET1,
                    null));
        }
        textViewNavHeaderName.setText(preferences.getString(ALLCONSTANTS.SESSION_KEY_FNAME1,null)+" "
                +preferences.getString(ALLCONSTANTS.SESSION_KEY_LNAME1,null));
        textViewHeaderMobile.setText(preferences.getString(ALLCONSTANTS.SESSION_KEY_MOBILE1,null));
        textViewHeaderEmail.setText(preferences.getString(ALLCONSTANTS.SESSION_KEY_EMAIL1,null));
        imageViewToolbarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseActivity.this instanceof About_Us)
                {
                    Toast.makeText(BaseActivity.this, "You are in the same page", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intentAboutUs=new Intent(BaseActivity.this, About_Us.class);
                    intentAboutUs.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentAboutUs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentAboutUs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentAboutUs.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentAboutUs);
                }
            }
        });
        textViewToolbarAmountTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseActivity.this instanceof WalletActivity)
                {
                    Toast.makeText(BaseActivity.this, "You are in the same page", Toast.LENGTH_SHORT).show();

                }else {
                    Intent intentWallet=new Intent(BaseActivity.this, WalletActivity.class);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentWallet);
                }
            }
        });

    }
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "HELVETICT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_home:
                if (BaseActivity.this instanceof LoadOrPay)
                {
                    Toast.makeText(BaseActivity.this, "You are in the same page", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intentLoadCash=new Intent(BaseActivity.this, LoadOrPay.class);
                    intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentLoadCash);
                }

                break;
            case R.id.nav_profile:
                if (BaseActivity.this instanceof MyProfile)
                {
                    Toast.makeText(BaseActivity.this, "You are in the same page", Toast.LENGTH_SHORT).show();

                }else {
                    Intent intentProfile=new Intent(BaseActivity.this, MyProfile.class);
                    intentProfile.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentProfile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentProfile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentProfile);
                }

                break;
            case R.id.nav_wallet:
                if (BaseActivity.this instanceof WalletActivity)
                {
                    Toast.makeText(BaseActivity.this, "You are in the same page", Toast.LENGTH_SHORT).show();

                }else {
                    Intent intentWallet=new Intent(BaseActivity.this, WalletActivity.class);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentWallet);
                }
                break;
            case R.id.add_user:
                if (BaseActivity.this instanceof ListViewForContact)
                {
                    Toast.makeText(BaseActivity.this, "You are in the same page", Toast.LENGTH_SHORT).show();

                }else {
                    Intent intentWallet=new Intent(BaseActivity.this, ListViewForContact.class);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentWallet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentWallet);
                }
                break;
            case R.id.nav_trans:
                if (BaseActivity.this instanceof Transaction)
                {
                    Toast.makeText(BaseActivity.this, "You are in the same page", Toast.LENGTH_SHORT).show();

                }else {
                    Intent intentTrans=new Intent(BaseActivity.this, Transaction.class);
                    intentTrans.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentTrans.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentTrans.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentTrans.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentTrans);
                }

                break;
            case R.id.nav_contact_us:
                if (BaseActivity.this instanceof ContactUs)
                {
                    Toast.makeText(BaseActivity.this, "You are in the same page", Toast.LENGTH_SHORT).show();

                }else {
                    Intent intentCOntactUs=new Intent(BaseActivity.this, ContactUs.class);
                    intentCOntactUs.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentCOntactUs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentCOntactUs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentCOntactUs.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentCOntactUs);
                }
                break;
            case R.id.nav_logout:
                new AlertDialog.Builder(BaseActivity.this)
                        .setTitle("Alert!!")

                        .setMessage("Do you want to Logout ?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                CommFunc.goToSignupCommFunc(BaseActivity.this);
                            }

                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
                break;
            case R.id.nav_about_us:
                if (BaseActivity.this instanceof About_Us)
                {
                    Toast.makeText(this, "You are in the same page", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intentAboutUs=new Intent(BaseActivity.this, About_Us.class);
                    intentAboutUs.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentAboutUs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentAboutUs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentAboutUs.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentAboutUs);
                }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void requestFocusFromBaseActivity(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        menu.findItem(R.id.nav_home).setTitle(resources.getString(R.string.home));
        menu.findItem(R.id.nav_profile).setTitle(resources.getString(R.string.myprofile));
        menu.findItem(R.id.nav_wallet).setTitle(resources.getString(R.string.wallet));
        menu.findItem(R.id.add_user).setTitle(resources.getString(R.string.adduser));
        menu.findItem(R.id.nav_trans).setTitle(resources.getString(R.string.trans));
        menu.findItem(R.id.nav_contact_us).setTitle(resources.getString(R.string.contactus));
        menu.findItem(R.id.nav_about_us).setTitle(resources.getString(R.string.aboutus));
        menu.findItem(R.id.nav_logout).setTitle(resources.getString(R.string.logout));


    }
    protected void showProgressDialogGlobel(Context context, String message) {
        if (pDialogGlobel == null) {
            pDialogGlobel = new ProgressDialog(context);
            pDialogGlobel.setMessage(message);
            pDialogGlobel.setIndeterminate(false);
            pDialogGlobel.setCancelable(false);
        }
        pDialogGlobel.show();
    }
    protected void dismissProgressDialogGlobel() {
        if (pDialogGlobel != null && pDialogGlobel.isShowing()) {
            pDialogGlobel.dismiss();
        }
    }
    protected void hideSoftInput(View view)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
