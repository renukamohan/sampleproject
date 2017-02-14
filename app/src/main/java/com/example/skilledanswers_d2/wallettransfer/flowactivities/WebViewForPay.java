package com.example.skilledanswers_d2.wallettransfer.flowactivities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.skilledanswers_d2.wallettransfer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class WebViewForPay extends AppCompatActivity {
    private static final String TAG = "TNPRequestDebugTag";
    private ProgressDialog pDialog = null;
    WebView webView;
    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_for_pay);
        webView= (WebView) findViewById(R.id.webView_for_pay);

        Bundle bundle=getIntent().getExtras();
//        HashMap<String,String> map=new HashMap<>();
//        map.put("addressLane1",bundle.getString("address_line_1"));
//        System.out.println("kkkkkkkkkkkkkkk---------address_line_1----"+bundle.getString("address_line_1"));
//        map.put("addressLane2",bundle.getString("address_line_2"));
//        System.out.println("kkkkkkkkkkkkkkk---------address_line_2----"+bundle.getString("address_line_2"));
//        map.put("amount",bundle.getString("amount"));
//        System.out.println("kkkkkkkkkkkkkkk---------amount----"+bundle.getString("amount"));
//        map.put("api_key",bundle.getString("api_key"));
//        System.out.println("kkkkkkkkkkkkkkk---------api_key----"+bundle.getString("api_key"));
//        map.put("city",bundle.getString("city"));
//        System.out.println("kkkkkkkkkkkkkkk---------city----"+bundle.getString("city"));
//        map.put("country",bundle.getString("country"));
//        System.out.println("kkkkkkkkkkkkkkk---------country----"+bundle.getString("country"));
//        map.put("currency",bundle.getString("currency"));
//        System.out.println("kkkkkkkkkkkkkkk---------currency----"+bundle.getString("currency"));
//        map.put("description",bundle.getString("description"));
//        System.out.println("kkkkkkkkkkkkkkk---------description----"+bundle.getString("description"));
//        map.put("email",bundle.getString("email"));
//        System.out.println("kkkkkkkkkkkkkkk---------email----"+bundle.getString("email"));
//        map.put("mode",bundle.getString("mode"));
//        System.out.println("kkkkkkkkkkkkkkk---------mode----"+bundle.getString("mode"));
//        map.put("name",bundle.getString("name"));
//        System.out.println("kkkkkkkkkkkkkkk---------name----"+bundle.getString("name"));
//        map.put("order_id",bundle.getString("order_id"));
//        System.out.println("kkkkkkkkkkkkkkk---------order_id----"+bundle.getString("order_id"));
//        map.put("phone",bundle.getString("phone"));
//        System.out.println("kkkkkkkkkkkkkkk---------phone----"+bundle.getString("phone"));
//        map.put("return_url",bundle.getString("return_url"));
//        System.out.println("kkkkkkkkkkkkkkk---------return_url----"+bundle.getString("return_url"));
//        map.put("state",bundle.getString("state"));
//        System.out.println("kkkkkkkkkkkkkkk---------state----"+bundle.getString("state"));
//        map.put("udf1",bundle.getString("udf1"));
//        System.out.println("kkkkkkkkkkkkkkk---------udf1----"+bundle.getString("udf1"));
//        map.put("udf2",bundle.getString("udf2"));
//        System.out.println("kkkkkkkkkkkkkkk---------udf2----"+bundle.getString("udf2"));
//        map.put("udf3",bundle.getString("udf3"));
//        System.out.println("kkkkkkkkkkkkkkk---------udf3----"+bundle.getString("udf3"));
//        map.put("udf4",bundle.getString("udf4"));
//        System.out.println("kkkkkkkkkkkkkkk---------udf4----"+bundle.getString("udf4"));
//        map.put("udf5",bundle.getString("udf5"));
//        System.out.println("kkkkkkkkkkkkkkk---------udf5----"+bundle.getString("udf5"));
//        map.put("zip_code",bundle.getString("zip_code"));
//        System.out.println("kkkkkkkkkkkkkkk---------zip_code----"+bundle.getString("zip_code"));
//        map.put("hash",bundle.getString("hash"));
//        System.out.println("kkkkkkkkkkkkkkk---------hash----"+bundle.getString("hash"));

        String postTOUrl=bundle.getString("formURL");
        String jsonObject=bundle.getString("jsonObject");
        try {
            JSONObject objectFormData=new JSONObject(jsonObject);
            String api_key = objectFormData.getString("api_key");
            String return_url = objectFormData.getString("return_url");
            System.out.println("ooooooo-----return_url---"+return_url);

            String mode = objectFormData.getString("mode");

            String order_id =objectFormData.getString("order_id");
            String amount = objectFormData.getString("amount");
            String currency =objectFormData.getString("currency");
            String description =objectFormData.getString("description");
            String name =objectFormData.getString("name");
            String email = objectFormData.getString("email");
            String phone = objectFormData.getString("phone");
            String address_line_1 = objectFormData.getString("address_line_1");
            String address_line_2 = objectFormData.getString("address_line_2");
            String city = objectFormData.getString("city");
            String state = objectFormData.getString("state");
            String zip_code = objectFormData.getString("zip_code");
            String country = objectFormData.getString("country");
            String udf1 = objectFormData.getString("udf1");
            String udf2 =objectFormData.getString("udf2");
            String udf3 = objectFormData.getString("udf3");
            String udf4 = objectFormData.getString("udf4");
            String udf5 = objectFormData.getString("udf5");
            //
            String hash=objectFormData.getString("hash");
            //
            Map<String, String> map = new HashMap<String, String>();
            map.put("api_key", api_key);
            map.put("return_url", return_url);
            map.put("mode", mode);
            map.put("order_id", order_id);
            map.put("amount", amount);
            map.put("currency", currency);
            map.put("description", description);
            map.put("name", name);
            map.put("email", email);
            map.put("phone", phone);
            map.put("address_line_1", address_line_1);
            map.put("address_line_2", address_line_2);
            map.put("city", city);
            map.put("state", state);
            map.put("zip_code", zip_code);
            map.put("country", country);
            map.put("udf1", udf1);
            map.put("udf2", udf2);
            map.put("udf3", udf3);
            map.put("udf4", udf4);
            map.put("udf5", udf5);
            map.put("hash",hash);

            String postData = "";

            // Sort the map by key and create the hashData and postData
            for (String key : new TreeSet<String>(map.keySet())) {
                if (!map.get(key).equals("")) {
//                    hashData = hashData + "|" + map.get(key);
                    postData = postData + key + "=" + map.get(key) + "&";
                }
            }
            // Generate the hash key using hashdata and append the has to postData query string
//            String hash = generateSha512Hash(hashData).toUpperCase();
            postData = postData + "hash=" + hash;

            Log.d(TAG, "HashData: " + hash);
            Log.d(TAG, "Hash: " + hash);
            Log.d(TAG, "PostData: " + postData);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setDatabaseEnabled(true);
//        webSettings.setDatabasePath("/data/data/" + getPackageName() + "/databases/");
//        webSettings.setAppCacheMaxSize(1024*1024*8);
//        webSettings.setAppCachePath("/data/data/" + getPackageName() + "/cache/");
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setLightTouchEnabled(true);
            webSettings.setBuiltInZoomControls(true);
//        webView.setWebChromeClient(new WebChromeClient());
            if (Build.VERSION.SDK_INT >= 21)
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            webView.setWebViewClient(new WebViewClient());
            webView.postUrl(postTOUrl, postData.getBytes());
            webView.addJavascriptInterface(new MyJavaScriptInterface(this),"Android");
            webView.setWebViewClient(new WebViewClient()
            {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    showProgressDialog();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    dismissProgressDialog();
                }
            });





        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     *  Interface to bind Javascript from WebView with Android
     */
    public class MyJavaScriptInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        MyJavaScriptInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void getPaymentResponse(String jsonResponse) {

            System.out.println("ooooooooooooooooo----------getPaymentResponse----------" +jsonResponse);
            Log.e("POST_RESP",jsonResponse);
            if (jsonResponse != null) {
                try {
                    JSONObject object = new JSONObject(jsonResponse);
                    String refid=null,paymentID=null,amount=null,message=null;

                    if (object.getBoolean("status"))
                    {


                        if (getIntent().getExtras().getString("PAY_OR_LOAD").equals("LOAD"))
                        {
                            JSONObject object1=object.getJSONObject("data");
                            refid=object1.optString("refid");
                            paymentID=object1.getString("paymentID");
                            amount=object1.getString("amount");
                            message=object.getString("message");
                            Intent intent=new Intent(WebViewForPay.this,TransStatusPayIn.class);
                            if (refid!=null)
                            {
                                intent.putExtra("REF_ID",refid);
                            }else {
                                intent.putExtra("REF_ID","");
                            }

                            intent.putExtra("PAYMENT_ID",paymentID);
                            intent.putExtra("AMOUNT",amount);
                            intent.putExtra("MESSAGE",message);
                            intent.putExtra("STATUS",true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            JSONObject jsonObject=object.getJSONObject("data");
                            JSONObject jsonObject1=jsonObject.getJSONObject("walletOut");
                            Intent intent=new Intent(WebViewForPay.this,TransStatusPayOut.class);
                            intent.putExtra("message",object.getString("message"));
                            intent.putExtra("amount",jsonObject1.getString("amount"));
                            intent.putExtra("walletAmount",jsonObject1.getString("walletAmount"));
                            intent.putExtra("merchantName",jsonObject1.getString("merchantName"));
                            intent.putExtra("isPaid",jsonObject1.getBoolean("isPaid"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);



                        }
                    }
                    else {
                        if (getIntent().getExtras().getString("PAY_OR_LOAD").equals("LOAD"))
                        {
                            JSONObject object1=object.getJSONObject("data");
                            refid=object1.optString("refid");
                            paymentID=object1.getString("paymentID");
                            amount=object1.getString("amount");
                            message=object.getString("message");
                            Intent intent=new Intent(WebViewForPay.this,TransStatusPayIn.class);
                            if (refid!=null)
                            {
                                intent.putExtra("REF_ID",refid);
                            }else {
                                intent.putExtra("REF_ID","");
                            }

                            intent.putExtra("PAYMENT_ID",paymentID);
                            intent.putExtra("AMOUNT",amount);
                            intent.putExtra("MESSAGE",message);
                            intent.putExtra("STATUS",false);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            JSONObject jsonObject=object.getJSONObject("data");
                            JSONObject jsonObject1=jsonObject.getJSONObject("walletOut");
                            Intent intent=new Intent(WebViewForPay.this,TransStatusPayOut.class);
                            intent.putExtra("message",object.getString("message"));
                            intent.putExtra("amount",jsonObject1.getString("amount"));
                            intent.putExtra("walletAmount",jsonObject1.getString("walletAmount"));
                            intent.putExtra("merchantName",jsonObject1.getString("merchantName"));
                            intent.putExtra("isPaid",jsonObject1.getBoolean("isPaid"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(mContext, "Problem with network.. try after some time", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(WebViewForPay.this);
            pDialog.setMessage("Please wait... .");
            pDialog.setCanceledOnTouchOutside(false);
        }
        pDialog.show();
    }
    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
   /* @Override
    public void onDestroy() {

        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(false);


        }  }*/
}
