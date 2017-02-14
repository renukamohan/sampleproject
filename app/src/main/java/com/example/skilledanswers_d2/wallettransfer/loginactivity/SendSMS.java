package com.example.skilledanswers_d2.wallettransfer.loginactivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;

import java.util.List;

public class SendSMS extends Activity {

    public static final String ACTION_SMS_SENT = "com.techblogon.android.apis.os.SMS_SENT_ACTION";
    AppCompatTextView recipientTextEdit = null;
    AppCompatTextView contentTextEdit =null;
    AppCompatTextView perecentageValue=null;
    AppCompatTextView appCompatTextView=null;
    TextView titleTextView =null;
    String phone,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_send);
        phone=getIntent().getStringExtra("phone");
        value=getIntent().getStringExtra("alertvalue");
        Log.e("jlkjdlkjdlklkdlk",""+value);
        //Get GUI controls instance from here
        recipientTextEdit = (AppCompatTextView)this
                .findViewById(R.id.editTextEnterReceipents);
        contentTextEdit = (AppCompatTextView) this
                .findViewById(R.id.editTextCompose);
        perecentageValue = (AppCompatTextView) this
                .findViewById(R.id.editTextCompose1);
        titleTextView = (TextView)this.findViewById(R.id.textViewTitle);
        appCompatTextView=(AppCompatTextView) this.findViewById(R.id.editTextCompose2);
        perecentageValue.setText(value);
        SharedPreferences preferences=getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,
                MODE_PRIVATE);

        recipientTextEdit.setText(preferences.getString(ALLCONSTANTS.SESSION_KEY_MOBILE1,null));
        // Register broadcast receivers for SMS sent and delivered intents
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = null;
                boolean error = true;
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        message = "Message sent!";
                        error = false;
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        message = "Error.";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        message = "Error: No service.";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        message = "Error: Null PDU.";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        message = "Error: Radio off.";
                        break;
                }


                contentTextEdit.setText("");
                titleTextView.setText(message);
                titleTextView.setTextColor(error ? Color.RED : Color.GREEN);

            }
        }, new IntentFilter(ACTION_SMS_SENT));
    }

    public void onClickSend(View v)
    {
        //Get recipient from user and check for null
        if (TextUtils.isEmpty(recipientTextEdit.getText())) {
            titleTextView.setText("Enter Receipent");
            titleTextView.setTextColor(Color.RED);
            return;
        }

        //Get content and check for null
        if (TextUtils.isEmpty(contentTextEdit.getText())) {
            titleTextView.setText("Empty Content");
            titleTextView.setTextColor(Color.RED);
            return;
        }
        //sms body coming from user input
        String strSMSBody = contentTextEdit.getText().toString();
        String strSMSBody1=perecentageValue.getText().toString();
        String strSMSBody2=appCompatTextView.getText().toString();
        String fullmsg=strSMSBody+strSMSBody1+" "+strSMSBody2;
        //sms recipient added by user from the activity screen
        Log.e("percentage",""+fullmsg);
        String strReceipentsList = recipientTextEdit.getText().toString();
        SmsManager sms = SmsManager.getDefault();
        List<String> messages = sms.divideMessage(fullmsg);
        for (String message : messages) {
            sms.sendTextMessage(strReceipentsList, null, message, PendingIntent.getBroadcast(
                    this, 0, new Intent(ACTION_SMS_SENT), 0), null);
        }
    }

}
