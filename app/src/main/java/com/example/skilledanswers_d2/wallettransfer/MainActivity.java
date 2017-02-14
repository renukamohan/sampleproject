package com.example.skilledanswers_d2.wallettransfer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
Button button;
    TextView textView1,textView2,textView3,textView4,textView5;
    ImageView imageView1,imageView2;
    public static String english = "";
    public static String hindi = "";
    public static String kannada = "";
    public static String tamil = "";
    public static String telugu = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


     button=(Button)findViewById(R.id.next);
        textView1=(TextView)findViewById(R.id.english);
        textView2=(TextView)findViewById(R.id.hindi);
        textView3=(TextView)findViewById(R.id.Kannada);
        textView4=(TextView)findViewById(R.id.Tamil);
        textView5=(TextView)findViewById(R.id.Telugu);
        textView2.setText(getString(R.string.language));
        textView3.setText("ಕನ್ನಡ");
        textView4.setText("தமிழ்");
        textView5.setText("తెలుగు");
        imageView1=(ImageView)findViewById(R.id.image0);
        imageView2=(ImageView)findViewById(R.id.image1);
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit = pref.edit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkApplication("com.example.skilledanswers_d2.wallettransfer");


            }
        });

                textView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        english="1";
                        edit.putString("english", "1");
                        edit.putString("hindi", "");
                        edit.putString("tamil", "");
                        edit.putString("telugu", "");
                        edit.putString("kannada", "");
                        edit.commit();
                        imageView1.setVisibility(View.GONE);
                        imageView2.setVisibility(View.GONE);
                        Intent intent=new Intent(getApplicationContext(),Verify.class);
                        intent.putExtra("english","1");
                        intent.putExtra("hindi","");
                        intent.putExtra("tamil","");
                        intent.putExtra("kannada","");
                        intent.putExtra("telugu","");
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim, R.anim.right);
                    }
                });
                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hindi="2";
                        edit.putString("hindi", "2");
                        edit.putString("english", "");
                        edit.putString("tamil", "");
                        edit.putString("kannada", "");
                        edit.putString("telugu", "");
                        edit.commit();
                        imageView2.setVisibility(View.GONE);
                        imageView1.setVisibility(View.GONE);
                        Intent intent=new Intent(getApplicationContext(),Verify.class);
                        intent.putExtra("hindi","2");
                        intent.putExtra("english","");
                        intent.putExtra("telugu","");
                        intent.putExtra("tamil","");
                        intent.putExtra("kannada","");

                        startActivity(intent);
                        overridePendingTransition(R.anim.anim, R.anim.right);
                    }
                });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kannada="3";
                edit.putString("kannada", "3");
                edit.putString("hindi", "");
                edit.putString("tamil", "");
                edit.putString("telugu", "");
                edit.putString("english", "");
                edit.commit();
                imageView2.setVisibility(View.GONE);
                imageView1.setVisibility(View.GONE);
                Intent intent=new Intent(getApplicationContext(),Verify.class);
                intent.putExtra("kannada","3");
                intent.putExtra("english","");
                intent.putExtra("telugu","");
                intent.putExtra("tamil","");
                intent.putExtra("hindi","");
                startActivity(intent);
                overridePendingTransition(R.anim.anim, R.anim.right);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putString("tamil","4");
                edit.putString("hindi","");
                edit.putString("telugu","");
                edit.putString("kannada","");
                edit.putString("english","");
                edit.commit();
                imageView2.setVisibility(View.GONE);
                imageView1.setVisibility(View.GONE);
                Intent intent=new Intent(getApplicationContext(),Verify.class);
                tamil="4";
                intent.putExtra("tamil","4");
                intent.putExtra("english","");
                intent.putExtra("telugu","");
                intent.putExtra("kannada","");
                intent.putExtra("hindi","");
                startActivity(intent);
                overridePendingTransition(R.anim.anim, R.anim.right);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putString("telugu","5");
                edit.putString("hindi","");
                edit.putString("tamil","");
                edit.putString("kannada","");
                edit.putString("english","");
                edit.commit();
                imageView2.setVisibility(View.GONE);
                imageView1.setVisibility(View.GONE);
                Intent intent=new Intent(getApplicationContext(),Verify.class);
                telugu="5";
                intent.putExtra("telugu","5");
                intent.putExtra("english","");
                intent.putExtra("tamil","");
                intent.putExtra("kannada","");
                intent.putExtra("hindi","");
                startActivity(intent);
                overridePendingTransition(R.anim.anim, R.anim.right);
            }
        });


    }
    public void checkApplication(String packageName) {
        PackageManager packageManager = getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo("com.example.skilledanswers_d2.wallettransfer", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (applicationInfo == null) {

            Toast.makeText(getApplicationContext(),"App is not installed lease install the app",Toast.LENGTH_SHORT).show();

        } else {
            Log.e("opps","App is not installed");
        }
    }

}
