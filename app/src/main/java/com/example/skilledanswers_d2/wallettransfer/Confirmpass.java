package com.example.skilledanswers_d2.wallettransfer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skilledanswers_d2.wallettransfer.flowactivities.LoadOrPay;

public class Confirmpass extends Activity {
    EditText editText,editText1,editText2,editText3;
    Button button0 , button1 , button2 , button3 , button4 , button5 , button6 ,
            button7 , button8 , button9,back,nothng;
    TextView show,text;
    ImageView imageView,imageView1;
    int flag=0;
    String value1;
    String english,hindi,kannada,tamil,telugu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmpasscode);
        value1=getIntent().getStringExtra("value");
        editText=(EditText)findViewById(R.id.edit1);
        editText1=(EditText)findViewById(R.id.edit2);
        editText2=(EditText)findViewById(R.id.edit3);
        editText3=(EditText)findViewById(R.id.edit4);
        button0 = (Button) findViewById(R.id.zero);
        button1 = (Button) findViewById(R.id.one);
        button2 = (Button) findViewById(R.id.two);
        button3 = (Button) findViewById(R.id.three);
        button4 = (Button) findViewById(R.id.four);
        button5 = (Button) findViewById(R.id.five);
        button6 = (Button) findViewById(R.id.six);
        button7 = (Button) findViewById(R.id.seven);
        button8 = (Button) findViewById(R.id.eight);
        button9 = (Button) findViewById(R.id.nine);
        back = (Button) findViewById(R.id.back);
        imageView=(ImageView)findViewById(R.id.eye);
        imageView1=(ImageView)findViewById(R.id.eye1);
        nothng=(Button)findViewById(R.id.nothng);
        show=(TextView)findViewById(R.id.show);
        text=(TextView)findViewById(R.id.etNum1);
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        english = pref.getString("english","");
        hindi = pref.getString("hindi","");
        kannada = pref.getString("kannada","");
        telugu = pref.getString("telugu","");
        tamil = pref.getString("tamil","");
        Log.e("hin",""+hindi);
        Log.e("eng",""+english);
        if(english.equalsIgnoreCase("1")) {
            show.setText(getString(R.string.Englishshow));
            back.setText(getString(R.string.Englishback));
            nothng.setText(getString(R.string.EnglishEnter));
            text.setText("Register Passcode");
        }
        else if(hindi.equalsIgnoreCase("2"))
        {
            show.setText(getString(R.string.Hindishow));
            back.setText(getString(R.string.Hindiback));
            nothng.setText(getString(R.string.HindiEnter));
            text.setText( "रजिस्टर पासकोड");
        }
        else if(kannada.equalsIgnoreCase("3"))
        {
            show.setText(getString(R.string.Kannadashow));
            back.setText(getString(R.string.Kannadaback));
            nothng.setText(getString(R.string.kannadaenter));
            text.setText(
                    "ನೋಂದಣಿ ಪಾಸ್ಕೋಡ್");
        }
        else if(tamil.equalsIgnoreCase("4"))
        {
            show.setText(getString(R.string.Tamilshow));
            back.setText(getString(R.string.Tamilback));
            nothng.setText(getString(R.string.Tamilenter));
            text.setText(

                    "பதிவு கடவுக்குறியீட்டின்ಕೋಡ್");
        }
        else if(telugu.equalsIgnoreCase("5"))
        {
            show.setText(getString(R.string.Telugushow));
            back.setText(getString(R.string.Teluguback));
            nothng.setText(getString(R.string.Teluguenter));
            text.setText(

                    "నమోదు పాస్వర్డ్ಕೋಡ್");
        }
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editText1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editText3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0) {
                    if(english.equalsIgnoreCase("1")) {
                        show.setText(getString(R.string.Englishhide));
                        imageView1.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.GONE);
                    }
                    else if(hindi.equalsIgnoreCase("2"))
                    {
                        show.setText(getString(R.string.Hindihide));
                        imageView1.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.GONE);
                    }
                    else if(kannada.equalsIgnoreCase("3"))
                    {
                        show.setText(getString(R.string.KannadaHide));
                        imageView1.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.GONE);
                    }
                    else if(tamil.equalsIgnoreCase("4"))
                    {
                        show.setText(getString(R.string.Tamilhide));
                        imageView1.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.GONE);
                    }
                    else if(telugu.equalsIgnoreCase("5"))
                    {
                        show.setText(getString(R.string.Teluguhide));
                        imageView1.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.GONE);
                    }
                    flag++;
                    show.setText("Hide");
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    editText1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    editText2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    editText3.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    imageView1.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    Log.e("hide",""+flag);
                }
              else  if(flag==1)
                {
                    if(english.equalsIgnoreCase("1")) {
                        show.setText(getString(R.string.Englishshow));
                        imageView1.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                    else if(hindi.equalsIgnoreCase("2"))
                    {
                        show.setText(getString(R.string.Hindishow));
                        imageView1.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                    else if(kannada.equalsIgnoreCase("3"))
                    {
                        show.setText(getString(R.string.Kannadashow));
                        imageView1.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                    else if(tamil.equalsIgnoreCase("4"))
                    {
                        show.setText(getString(R.string.Tamilshow));
                        imageView1.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                    else if(telugu.equalsIgnoreCase("5"))
                    {
                        show.setText(getString(R.string.Telugushow));
                        imageView1.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                    show.setText("Show");
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editText1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editText3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Log.e("show",""+flag);
                    flag--;
                }

            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().length()==0&&editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0) {
                    editText.setText("1");
                }
                else if(editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText1.setText("1");
                }
                else if(editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText2.setText("1");
                }
                else
                {
                    editText3.setText("1");
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0&&editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0) {
                    editText.setText("2");
                }
                else if(editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText1.setText("2");
                }
                else if(editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText2.setText("2");
                }
                else
                {
                    editText3.setText("2");
                }

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0&&editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0) {
                    editText.setText("3");
                }
                else if(editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText1.setText("3");
                }
                else if(editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText2.setText("3");
                }
                else
                {
                    editText3.setText("3");
                }

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0&&editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0) {
                    editText.setText("4");
                }
                else if(editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText1.setText("4");
                }
                else if(editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText2.setText( "4");
                }
                else
                {
                    editText3.setText("4");
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0&&editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0) {
                    editText.setText( "5");
                }
                else if(editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText1.setText("5");
                }
                else if(editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText2.setText("5");
                }
                else
                {
                    editText3.setText("5");
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0&&editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0) {
                    editText.setText("6");
                }
                else if(editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText1.setText("6");
                }
                else if(editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText2.setText("6");
                }
                else
                {
                    editText3.setText("6");
                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0&&editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0) {
                    editText.setText( "7");
                }
                else if(editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText1.setText( "7");
                }
                else if(editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText2.setText("7");
                }
                else
                {
                    editText3.setText("7");
                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0&&editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0) {
                    editText.setText("8");
                }
                else if(editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText1.setText("8");
                }
                else if(editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText2.setText("8");
                }
                else
                {
                    editText3.setText("8");
                }
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0&&editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0) {
                    editText.setText("9");
                }
                else if(editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText1.setText("9");
                }
                else if(editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText2.setText("9");
                }
                else
                {
                    editText3.setText("9");
                }
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0&&editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0) {
                    editText.setText("0");
                }
                else if(editText1.getText().length()==0&&editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText1.setText("0");
                }
                else if(editText2.getText().length()==0&&editText3.getText().length()==0)
                {
                    editText2.setText("0");
                }
                else
                {
                    editText3.setText("0");
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");

            }
        });
        nothng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value=editText.getText().toString()+editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString();
                if(value1.equalsIgnoreCase(value))
                {
                    Intent intent=new Intent(getApplicationContext(),LoadOrPay.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Incorrect password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
    }
}
