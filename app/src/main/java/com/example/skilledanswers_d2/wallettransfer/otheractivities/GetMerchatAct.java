package com.example.skilledanswers_d2.wallettransfer.otheractivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.skilledanswers_d2.wallettransfer.BaseActivity;
import com.example.skilledanswers_d2.wallettransfer.Interfaces.PassMerchants;
import com.example.skilledanswers_d2.wallettransfer.LocaleHelper;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.adapters.MerchantsAdptr;
import com.example.skilledanswers_d2.wallettransfer.models.MerchantsModel;

import java.util.ArrayList;

public class GetMerchatAct extends BaseActivity implements PassMerchants {
    AppCompatTextView appCompatTextView;
    String hindi,kannada,telugu,tamil,english;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.content_base);
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userpressedBack();
            }
        });
        getLayoutInflater().inflate(R.layout.activity_get_merchat,relativeLayout);
        appCompatTextView=(AppCompatTextView)findViewById(R.id.activity_get_merchants_heading);
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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_get_merchants_recycleview_id);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(GetMerchatAct.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<MerchantsModel> merchantsModels=getIntent().getExtras().getParcelableArrayList("LIST_OF_MERCHANTS");
        MerchantsAdptr adptr=new MerchantsAdptr(GetMerchatAct.this,merchantsModels);
        recyclerView.setAdapter(adptr);

    }


    @Override
    public void merchantIs(String _id, String _name) {
        Intent intent=new Intent();
        intent.putExtra("MERCHANT_ID",_id);
        intent.putExtra("MERCHANT_NAME",_name);
        setResult(1,intent);
        finish();
    }
    private void userpressedBack()
    {
        Intent intent=new Intent();
        setResult(0,intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onBackPressed() {
        userpressedBack();
    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        appCompatTextView.setText(resources.getString(R.string.selectmerchant));



    }
}
