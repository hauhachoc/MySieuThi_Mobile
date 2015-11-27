package com.example.quocdat.mysieuthi_mobile;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TimMatKhauActivity extends Activity {
    Button btnclick;
    private EditText sdt;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timmatkhau);
        btnclick = (Button) findViewById(R.id.button1);
        btnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guiSMS();
            }
        });
    }
    public void guiSMS(){
        try {
            String phoneNum = "+84";
            sdt = (EditText) findViewById(R.id.editText1);
            pass = (EditText) findViewById(R.id.editText2);
            String s1 = sdt.getText().toString();
            String s2 = pass.getText().toString();
            phoneNum = phoneNum + s1;
            if (s1.length() > 0 && s2.length() > 0) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNum, null, s2, null, null);
                Toast.makeText(getApplicationContext(), "Đã gửi password mới thành công tới " + "\n" + "Số điện thoại : " + phoneNum, Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getBaseContext(), "Số điện thoại và mật khẩu mới không được bỏ trống.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
