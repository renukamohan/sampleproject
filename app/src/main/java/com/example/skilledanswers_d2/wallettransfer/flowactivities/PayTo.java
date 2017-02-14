package com.example.skilledanswers_d2.wallettransfer.flowactivities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.skilledanswers_d2.wallettransfer.BaseActivity;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.barcode.BarcodeTracker;
import com.example.skilledanswers_d2.wallettransfer.barcode.BarcodeTrackerFactory;
import com.example.skilledanswers_d2.wallettransfer.camera.CameraSource;
import com.example.skilledanswers_d2.wallettransfer.camera.CameraSourcePreview;
import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;
import com.example.skilledanswers_d2.wallettransfer.constant.CommFunc;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.CustomJsonReq;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.SingelTonVollyQueue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class PayTo extends BaseActivity implements BarcodeTracker.BarcodeGraphicTrackerCallback {
    private static final String TAG = "Barcode-reader";
    // Intent request code to handle updating play services if needed.
    private static final int RC_HANDLE_GMS = 9001;
    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private LinearLayoutCompat layoutCompatCameraEnablelayout=null;
    private AppCompatButton buttonEnableCamera=null;
    private AppCompatImageView imageViewShopListButtonImageview=null;
    private AppCompatEditText autoCompleteTextView=null;
    private AppCompatTextView payto_scan_qr_code_textview=null;
    boolean autoFocus = true;
    boolean useFlash = false;
    ////
    private boolean isQrCodeDetectionVolleyIsFree=true;
    ///
    private Dialog dialogInvalidQrCode=null;
    ////////////////////////////////////////////////// ayyncTask
    private String VOLLEY_TAG_getMerchantsFromQrCode="getMerchantsFromQrCode";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.content_base);
        getLayoutInflater().inflate(R.layout.activity_pay_to,relativeLayout);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mPreview = (CameraSourcePreview) findViewById(R.id.pay_to_camera_preview);
        layoutCompatCameraEnablelayout= (LinearLayoutCompat) findViewById(R.id.pay_to_permission_layout);
        buttonEnableCamera= (AppCompatButton) findViewById(R.id.payto_enable_camera_button);
        imageViewShopListButtonImageview= (AppCompatImageView) findViewById(R.id.pay_to_shop_list_imageview);
        autoCompleteTextView= (AppCompatEditText) findViewById(R.id.pay_to_autoCompleteTextView);
        payto_scan_qr_code_textview= (AppCompatTextView) findViewById(R.id.payto_scan_qr_code_textview_id);

        //
        buttonEnableCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        imageViewShopListButtonImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShowSelBranchWithoutCamera();
            }
        });
        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShowSelBranchWithoutCamera();
            }
        });

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CommFunc.CameraCheckPermission(PayTo.this)) {
                createCameraSource(autoFocus, useFlash);
            }else {
                layoutCompatCameraEnablelayout.setVisibility(View.VISIBLE); // show cam enable layout
                payto_scan_qr_code_textview.setVisibility(View.GONE);
            }
        }else {
            createCameraSource(autoFocus, useFlash);
        }


    }

    ///// bar code detected
    @Override
    public void onDetectedQrCode(final Barcode barcode) {

        if (isQrCodeDetectionVolleyIsFree)
        {
            if (dialogInvalidQrCode!=null)
            {
                if (dialogInvalidQrCode.isShowing())
                {
                    dialogInvalidQrCode.dismiss();
                }
            }
            getMerchantsFromQrCode(barcode.displayValue);
        }

    }

    private void getMerchantsFromQrCode(String qRcode)
    {
        isQrCodeDetectionVolleyIsFree=false;
        System.out.println("iiiiiiiiiiiiiiiiiiii---------getMerchants----------"+qRcode);

        PayTo.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgressDialogGlobel(PayTo.this,"Please wait... ");

            }
        });
        HashMap<String,String> map=new HashMap<>();
        map.put("action","getMerchantFromQRCode");
        SharedPreferences sharedPreferences = getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        map.put("androidToken",sharedPreferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
        map.put("code",qRcode);
        CustomJsonReq jsonReq=new CustomJsonReq(Request.Method.POST, ALLCONSTANTS.app_post_url, map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("iiiiiiiiiiiiiiiiiiii---------response----------"+response);

                        dismissProgressDialogGlobel();
                        if (response.toString()!=null)
                        {
                            try {
                                if (response.getBoolean("status"))
                                {
                                    JSONObject jsonObject=response.getJSONObject("data");
                                    JSONObject jsonObject1=jsonObject.getJSONObject("merchant");
                                    //
                                    Bundle  bundle=getIntent().getExtras();
                                    Intent intent=new Intent(PayTo.this,ShowSelBranch.class);
                                    intent.putExtra("AMOUNT_TO_PAY",bundle.getString("AMOUNT_TO_PAY"));
                                    intent.putExtra("IS_FROM_CAMERA",true);
                                    ///// this two will be added is is from camera
                                    intent.putExtra("PASS_MERCHANT_NAME",jsonObject1.getString("name"));
                                    intent.putExtra("PASS_MERCHANT_ID",jsonObject1.getString("id"));
                                    /////
                                    intent.putExtra("PROMO_APPLIED",bundle.getString("PROMO_APPLIED"));
                                    startActivity(intent);

                                }else {
                                    isQrCodeDetectionVolleyIsFree=true;
                                    if (dialogInvalidQrCode!=null)
                                    {
                                        if (!dialogInvalidQrCode.isShowing())
                                        {
                                            dialogInvalidQrCode.show();
                                        }
                                    }else {
                                        invalidQrCodeDialog(PayTo.this,"QR code Error",response.getString("message"));
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            isQrCodeDetectionVolleyIsFree=true;
                            CommFunc.NoInternetPrompt(PayTo.this);
                        }

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialogGlobel();
                isQrCodeDetectionVolleyIsFree=true;
                CommFunc.NoInternetPrompt(PayTo.this);
            }
        });
        jsonReq.setTag(VOLLEY_TAG_getMerchantsFromQrCode);
        SingelTonVollyQueue.getInstance(PayTo.this).addToRequestQueue(jsonReq);
    }

    private void openShowSelBranchWithoutCamera()
    {
        Bundle  bundle=getIntent().getExtras();
        Intent intent=new Intent(PayTo.this,ShowSelBranch.class);
        intent.putExtra("AMOUNT_TO_PAY",bundle.getString("AMOUNT_TO_PAY"));
        intent.putExtra("IS_FROM_CAMERA",false);
        intent.putExtra("PROMO_APPLIED",bundle.getString("PROMO_APPLIED"));
        startActivity(intent);
    }
    private void createCameraSource(boolean autoFocus, boolean useFlash) {
        Context context = getApplicationContext();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(this);
        barcodeDetector.setProcessor(new MultiProcessor.Builder<>(barcodeFactory).build());

        if (!barcodeDetector.isOperational()) {
            // Note: The first time that an app using the barcode or face API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any barcodes
            // and/or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.
            Log.w(TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this,"Low storage in phone... please delete some contents.",
                        Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"Detector dependencies are not yet available.",
                        Toast.LENGTH_LONG).show();
            }
        }else {
            // Creates and starts the camera.  Note that this uses a higher resolution in comparison
            // to other detection examples to enable the barcode detector to detect small barcodes
            // at long distances.
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                    .setRequestedFps(24.0f);

            // make sure that auto focus is an available option
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                builder = builder.setFocusMode(
                        autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);

            }

            mCameraSource = builder
                    .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                    .build();
        }


    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }else {
            if (mCameraSource != null) {
                try {
                    mPreview.start(mCameraSource);
                } catch (IOException e) {
                    Log.e(TAG, "Unable to start camera source.", e);
                    mCameraSource.release();
                    mCameraSource = null;
                }
            }
        }


    }

    // Restarts the camera
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CommFunc.CameraCheckPermission(PayTo.this))
            {
                if (layoutCompatCameraEnablelayout.getVisibility()==View.VISIBLE)
                {
                    layoutCompatCameraEnablelayout.setVisibility(View.GONE);
                    payto_scan_qr_code_textview.setVisibility(View.VISIBLE);

                }
                if (mCameraSource==null) {
                    createCameraSource(autoFocus, useFlash);

                }
                startCameraSource();
            }else {
                if (layoutCompatCameraEnablelayout.getVisibility()==View.GONE)
                {
                    layoutCompatCameraEnablelayout.setVisibility(View.VISIBLE);
                    payto_scan_qr_code_textview.setVisibility(View.GONE);
                }
            }
        }else {
            startCameraSource();
        }

    }

    // Stops the camera
    @Override
    protected void onPause() {
        super.onPause();
        if (mPreview != null) {
            mPreview.stop();
        }
        if (dialogInvalidQrCode!=null)
        {
            if (dialogInvalidQrCode.isShowing())
            {
                dialogInvalidQrCode.dismiss();
            }
        }
        SingelTonVollyQueue.getInstance(PayTo.this).getRequestQueue().cancelAll(VOLLEY_TAG_getMerchantsFromQrCode);
        dismissProgressDialogGlobel();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        isQrCodeDetectionVolleyIsFree=true;
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    private   void invalidQrCodeDialog(Context context,String title,String subjuct)
    {
           dialogInvalidQrCode=new Dialog(context,R.style.comm_dialog);
        dialogInvalidQrCode.setCanceledOnTouchOutside(false);
        dialogInvalidQrCode.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInvalidQrCode.setContentView(R.layout.common_dialog);
        AppCompatTextView textViewTitle= (AppCompatTextView) dialogInvalidQrCode.findViewById(R.id.commom_dialog_title_id);
        AppCompatTextView textViewSubjuct= (AppCompatTextView) dialogInvalidQrCode.findViewById(R.id.commom_dialog_subjuct_id);
        AppCompatButton buttonSubmit= (AppCompatButton) dialogInvalidQrCode.findViewById(R.id.commom_dialog_button_id);
        textViewTitle.setText(title);
        textViewSubjuct.setText(subjuct);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInvalidQrCode.dismiss();
            }
        });
        dialogInvalidQrCode.show();

    }


}
