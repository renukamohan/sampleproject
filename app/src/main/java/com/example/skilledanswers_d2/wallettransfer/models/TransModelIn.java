package com.example.skilledanswers_d2.wallettransfer.models;

/**
 * Created by SkilledAnswers-D1 on 02-01-2017.
 */

public class TransModelIn {
    private boolean  isExpand=false;
    private String _reffId=null;
    private String _paymentId=null;
    private String _amount=null;
    private String _cashBack=null;
    private String _status=null;
    private String _timeStamp=null;

    public TransModelIn(boolean isExpand, String _reffId, String _paymentId, String _amount, String _cashBack, String _status, String _timeStamp) {
        this.isExpand = isExpand;
        this._reffId = _reffId;
        this._paymentId = _paymentId;
        this._amount = _amount;
        this._cashBack = _cashBack;
        this._status = _status;
        this._timeStamp = _timeStamp;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String get_reffId() {
        return _reffId;
    }

    public void set_reffId(String _reffId) {
        this._reffId = _reffId;
    }

    public String get_paymentId() {
        return _paymentId;
    }

    public void set_paymentId(String _paymentId) {
        this._paymentId = _paymentId;
    }

    public String get_amount() {
        return _amount;
    }

    public void set_amount(String _amount) {
        this._amount = _amount;
    }

    public String get_cashBack() {
        return _cashBack;
    }

    public void set_cashBack(String _cashBack) {
        this._cashBack = _cashBack;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_timeStamp() {
        return _timeStamp;
    }

    public void set_timeStamp(String _timeStamp) {
        this._timeStamp = _timeStamp;
    }
}
