package com.liar.testvoiceinputdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by LiarShi on 2019/5/8.
 */
public class TestUtilsActivity extends AppCompatActivity {


    private EditText mResultText;
    private Button button;
    private VoiceInputUtils mVoice;

    public static void start(Context context) {
        Intent starter = new Intent(context, TestUtilsActivity.class);
        context.startActivity(starter);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_utils);

        // 初始化识别无UI识别对象
        mVoice=new VoiceInputUtils();
        mVoice.onInit(this);
        mResultText = (EditText) findViewById(R.id.tv);
        button=(Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVoice.onStart();
            }
        });
        mVoice.setListener(new VoiceInputUtils.Listener() {
            @Override
            public void onInputStr(String text) {
                if(text==null){
                    mResultText.setText(null);
                }else {
                    mResultText.setText(text);
                }

            }

            @Override
            public void onInputLength(int length) {
                mResultText.setSelection(length);
            }
        });
    }







    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVoice.onDestroy();

    }

//    public void tz(){
//        // 停止听写
//        mIat.stopListening();
//        showTip("停止听写");
//
//        // 取消听写
//        mIat.cancel();
//        showTip("取消听写");
//    }
}
