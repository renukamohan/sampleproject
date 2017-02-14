package com.example.skilledanswers_d2.wallettransfer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SkilledAnswers-D2 on 06-01-2017.
 */

public class Welcome extends Activity {
    TextView button,heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);


        heading=(TextView)findViewById(R.id.weather_icon);

        button=(TextView)findViewById(R.id.nextactivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Secure.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim, R.anim.right);
            }
        });
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                setContentView(R.layout.nosim);
                final ImageView imageView=(ImageView)findViewById(R.id.nosim);
                Button button=(Button)findViewById(R.id.simbutton);
                Animation myFadeInAnimation = AnimationUtils.loadAnimation(Welcome.this, R.anim.zoomin);
                imageView.startAnimation(myFadeInAnimation);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(),Secure.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim, R.anim.right);
                    }
                });

                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                // do something
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                // do something
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                // do something
                break;
            case TelephonyManager.SIM_STATE_READY:
                // do something
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                // do something
                break;
        }
    }
}
