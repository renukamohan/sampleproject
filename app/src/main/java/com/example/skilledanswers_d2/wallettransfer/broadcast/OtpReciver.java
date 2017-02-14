package com.example.skilledanswers_d2.wallettransfer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by SkilledAnswers-D1 on 18-08-2016.
 */
public class OtpReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null)
            {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                if (pdusObj != null) {
                    for (int i = 0; i < pdusObj .length; i++)
                    {
                        String format=bundle.getString("format");
                        SmsMessage currentMessage = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                        }
                        else {
                            currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        }
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                        String senderNum = phoneNumber ;
                        String message = currentMessage .getDisplayMessageBody();
                        try
                        {
                            if (senderNum .contains("CPEDIA"))
                            {
                               Log.e("otp","iiiiiiiiiiii-------"+message);

                                Intent intent1=new Intent("broadcastname");
                                intent1.putExtra("messge",message.replaceAll("[^0-9]",""));
                                context.sendBroadcast(intent1);
                            }
                        }
                        catch(Exception ignored){}

                    }
                }
            }

        } catch (Exception e)
        {

        }
    }
}
