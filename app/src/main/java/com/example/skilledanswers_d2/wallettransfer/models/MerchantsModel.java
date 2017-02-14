package com.example.skilledanswers_d2.wallettransfer.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 1/23/2017.
 */

public class MerchantsModel implements Parcelable{

    private String _id=null;
    private String _name=null;

    public MerchantsModel(String _id, String _name) {
        this._id = _id;
        this._name = _name;
    }

    protected MerchantsModel(Parcel in) {
        _id = in.readString();
        _name = in.readString();
    }

    public static final Creator<MerchantsModel> CREATOR = new Creator<MerchantsModel>() {
        @Override
        public MerchantsModel createFromParcel(Parcel in) {
            return new MerchantsModel(in);
        }

        @Override
        public MerchantsModel[] newArray(int size) {
            return new MerchantsModel[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(_name);
    }
}
