package com.example.skilledanswers_d2.wallettransfer;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;

import java.util.ArrayList;


public class MiniPercentage extends BaseActivity {
    CheckBox checkBox1,checkBox2,checkBox3;

    String name,phone;
    Button button;
    int count;
    String[] tokens;
    int value;
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    ArrayList<DataObject> modelMains;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.content_base);
        getLayoutInflater().inflate(R.layout.mini_percentage,relativeLayout);
        button=(Button)findViewById(R.id.next);
        name=getIntent().getStringExtra("name").replace("[", "").replace("]", "").replace(",","");
        phone=getIntent().getStringExtra("phone").replace("[", "").replace("]", "").replace(",","");
        String []ar=name.split("\n");
        mAdapter = new MyRecyclerView(getDataSet());
//        mRecyclerView.setAdapter(mAdapter);
        count=phone.length();
        Log.e("ddd,djk",""+ count);
        mAdapter = new MyRecyclerView();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new MyRecyclerView(getDataSet());


        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performBack();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkBox1=(CheckBox)findViewById(R.id.checkBox1);
        checkBox2=(CheckBox)findViewById(R.id.checkBox2);
//        editText1=(AppCompatEditText)findViewById(R.id.edit_percentage2);
//        editText2=(AppCompatEditText)findViewById(R.id.edit_percentage3);
//        editText3=(AppCompatEditText)findViewById(R.id.edit_percentage4);
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



                checkBox2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (checkBox2.isChecked()) {

                            int total=100;
                            int totalmembers=tokens.length;
                            int s=total/totalmembers;
                            Log.e("length", "" + totalmembers);
//                            value=String.valueOf(s)+"%";
                            value=s;
                            Log.e("value", "" + value);

                                mAdapter = new MyRecyclerView(getDataSet());
                                mRecyclerView.setAdapter(mAdapter);

                        }
                        else {
                           value=0;
                            mAdapter = new MyRecyclerView(getDataSet());
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }

                });




        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox1.isChecked()) {

                   value=0;
                    mAdapter = new MyRecyclerView(getDataSet());
                    mRecyclerView.setAdapter(mAdapter);
                    Toast.makeText(getApplicationContext(),"Enter value for each contact",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAdapter = new MyRecyclerView(getDataSet());
                    mRecyclerView.setAdapter(mAdapter);
                }

            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int value1=MyRecyclerView.totalvalue;
                Log.e("toatlvalue------",""+value);
                if(value1!=100)
                {
                    value=0;
                    mAdapter = new MyRecyclerView(getDataSet());
                    mRecyclerView.setAdapter(mAdapter);
                    Toast.makeText(getApplicationContext(),"Total should be 100",Toast.LENGTH_SHORT).show();
                }
                else {
                    value=0;
                    mAdapter = new MyRecyclerView(getDataSet());
                    mRecyclerView.setAdapter(mAdapter);
                    PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0,
                            new Intent(SENT), 0);

                    PendingIntent deliveredPI = PendingIntent.getBroadcast(getApplicationContext(), 0,
                            new Intent(DELIVERED), 0);

//---when the SMS has been sent---
                    registerReceiver(new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context arg0, Intent arg1) {
                            switch (getResultCode()) {
                                case Activity.RESULT_OK:
                                    Toast.makeText(getBaseContext(), "SMS sent",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                    Toast.makeText(getBaseContext(), "Generic failure",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case SmsManager.RESULT_ERROR_NO_SERVICE:
                                    Toast.makeText(getBaseContext(), "No service",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case SmsManager.RESULT_ERROR_NULL_PDU:
                                    Toast.makeText(getBaseContext(), "Null PDU",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case SmsManager.RESULT_ERROR_RADIO_OFF:
                                    Toast.makeText(getBaseContext(), "Radio off",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }, new IntentFilter(SENT));

                    SmsManager sms = SmsManager.getDefault();

                    SharedPreferences preferences = getSharedPreferences(ALLCONSTANTS.PASSCODE, MODE_PRIVATE);
                    String passcode = preferences.getString("passcode", null);
                    Log.e("passoce", "" + passcode);
                    String message = "Your passcode is" + passcode + "please enter the passcode";
                    sms.sendTextMessage(phone, null, message, sentPI, deliveredPI);
                    Intent intent = new Intent(getApplicationContext(), AlertMsg.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        performBack();
    }

    private void performBack()
    {
        if (isTaskRoot())
        {
            Intent intentLoadCash=new Intent(MiniPercentage.this,ListViewForContact .class);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentLoadCash);
        }else {
            finish();
        }
    }
   /* @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerView) mAdapter).setOnItemClickListener(new MyRecyclerView
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("lkj", " Clicked on Item " + position);
            }
        });
    }*/

    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
      String allname= name.concat("Self");
        Log.e("hkjkhkh",""+allname);
       tokens = allname.split("\n");
        for(String s:tokens) {

            DataObject obj = new DataObject(s,value);
            results.add(obj);
            Log.e("value",""+value);


        }
        return results;
    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        checkBox1.setText(resources.getString(R.string.settingpercentage));
        checkBox2.setText(resources.getString(R.string.equalpercentage));
        button.setText(resources.getString(R.string.next));


    }
}
