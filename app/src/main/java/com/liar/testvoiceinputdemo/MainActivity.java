package com.liar.testvoiceinputdemo;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechUtility;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    private Toast mToast;
    private final int URL_REQUEST_CODE = 0X001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();

        Button button=(Button) findViewById(R.id.button);
        TextView tv=(TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TestVoiceActivity.start(MainActivity.this);
                TestUtilsActivity.start(MainActivity.this);
            }
        });


    }

    private void requestPermissions(){
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permission!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},0x0010);
                }

                if(permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},0x0010);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void mscUninit() {
        if (SpeechUtility.getUtility()!= null) {
            SpeechUtility.getUtility().destroy();
            try{
                new Thread().sleep(40);
            }catch (InterruptedException e) {
                Log.w(TAG,"msc uninit failed"+e.toString());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (URL_REQUEST_CODE == requestCode) {
            Log.d(TAG,"onActivityResult>>");
            try{
                SharedPreferences pref = getSharedPreferences(SetYysrConfig.PREFER_NAME, MODE_PRIVATE);
                String server_url = pref.getString("url_preference","");
                String domain = pref.getString("url_edit","");
                Log.d(TAG,"onActivityResult>>domain = "+domain);
                if (!TextUtils.isEmpty(domain)) {
                    server_url = "http://"+domain+"/msp.do";
                }
                Log.d(TAG,"onActivityResult>>server_url = "+server_url);
                mscUninit();
                new Thread().sleep(40);
                //mscInit(server_url);
            }catch (Exception e) {
                showTip("reset url failed");
            }
        }

    }


    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
}
