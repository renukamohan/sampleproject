package com.example.skilledanswers_d2.wallettransfer.flowactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.skilledanswers_d2.wallettransfer.BaseActivity;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.adapters.ViewpagerAdapter;
import com.example.skilledanswers_d2.wallettransfer.fragments.TransactionFragIn;
import com.example.skilledanswers_d2.wallettransfer.fragments.TransactionFragOut;

public class Transaction extends BaseActivity {
    String[] frag_titles={"PAY-IN","PAY-OUT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.content_base);
        getLayoutInflater().inflate(R.layout.activity_transaction,relativeLayout);
        //
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performBack();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPager viewPager = (ViewPager) findViewById(R.id.transaction_pager_viewpager_id);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.transaction_pager_tablayout_id);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(2);
        }
        setupViewPager(viewPager);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    public void setupViewPager(ViewPager viewPager)
    {
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        Fragment fragment=new TransactionFragIn();
        adapter.addFrag(fragment,frag_titles[0]);
        Fragment fragment1=new TransactionFragOut();
        adapter.addFrag(fragment1,frag_titles[1]);
        viewPager.setAdapter(adapter);
    }





    @Override
    public void onBackPressed() {
        performBack();
    }

    private void performBack()
    {
        if (isTaskRoot())
        {
            Intent intentLoadCash=new Intent(Transaction.this, LoadOrPay.class);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentLoadCash);
        }else {
            finish();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}