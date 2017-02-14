package com.example.skilledanswers_d2.wallettransfer.models;

/**
 * Created by user on 1/23/2017.
 */

public class TransModelOut {
    private  boolean isExpand=false;
    private String _refid=null;
    private String _merchantName=null;
    private String _amount=null;
    private String _cashback=null;
    private String _status=null;
    private String _created=null;
    private String _updated=null;

    public TransModelOut(boolean isExpand, String _refid, String _merchantName, String _amount,
                         String _cashback, String _status, String _created, String _updated) {
        this.isExpand = isExpand;
        this._refid = _refid;
        this._merchantName = _merchantName;
        this._amount = _amount;
        this._cashback = _cashback;
        this._status = _status;
        this._created = _created;
        this._updated = _updated;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String get_refid() {
        return _refid;
    }

    public void set_refid(String _refid) {
        this._refid = _refid;
    }

    public String get_merchantName() {
        return _merchantName;
    }

    public void set_merchantName(String _merchantName) {
        this._merchantName = _merchantName;
    }

    public String get_amount() {
        return _amount;
    }

    public void set_amount(String _amount) {
        this._amount = _amount;
    }

    public String get_cashback() {
        return _cashback;
    }

    public void set_cashback(String _cashback) {
        this._cashback = _cashback;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_created() {
        return _created;
    }

    public void set_created(String _created) {
        this._created = _created;
    }

    public String get_updated() {
        return _updated;
    }

    public void set_updated(String _updated) {
        this._updated = _updated;
    }
}
