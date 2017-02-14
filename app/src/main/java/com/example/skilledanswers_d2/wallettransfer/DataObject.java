package com.example.skilledanswers_d2.wallettransfer;

import android.util.Log;

public class DataObject {
    private String mText1;
    private int mText2;


    DataObject (String text1,int text2){
        mText1 = text1;
        mText2=text2;

    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public int getmText2() {
        Log.e("values",""+mText2);
        return mText2;

    }

    public void setmText2(int mText2) {
        this.mText2 = mText2;
    }


}