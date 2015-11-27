package com.example.quocdat.mysieuthi_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChungThucTK_Activity extends Activity {
    TextView tvpin;
    EditText edtpin;
    Button btnXacnhan;
    DTTaiKhoan DTTK;
    PTDataTaiKhoan PTTK;
    int a = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chung_thuc_tk_);
        final Intent intent = getIntent();
        final String s1 = intent.getStringExtra("pin");
        PTTK = new PTDataTaiKhoan();
        DTTK = new DTTaiKhoan();
        DTTK.mataikhoan = intent.getStringExtra("sdt");
        DTTK.loaitaikhoan = intent.getStringExtra("ltk");
        DTTK.matkhau = intent.getStringExtra("mk");
        DTTK.tentaikhoan = intent.getStringExtra("ttk");
        DTTK.diachi = intent.getStringExtra("dc");
        DTTK.sotien = "0";
        DTTK.danhgia = "0";

        tvpin = (TextView) findViewById(R.id.tvpin);
        edtpin = (EditText) findViewById(R.id.edtPin);
        final String vlpin = edtpin.getText().toString();
        tvpin.setText(s1);
        Toast.makeText(getApplicationContext(), "Đã gửi mã PIN " + s1 + "\n" +"Số điện thoại : "+ DTTK.mataikhoan, Toast.LENGTH_SHORT).show();

        btnXacnhan = (Button) findViewById(R.id.buttonOK);
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (edtpin.getText().toString().equals(s1)) {
                        Toast.makeText(ChungThucTK_Activity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        PTTK.TaoTaiKhoan(DTTK);
                        finish();
                    }
                        else {
                        a++;
                        if(a==1) {
                            Toast.makeText(ChungThucTK_Activity.this, "PIN sai", Toast.LENGTH_SHORT).show();
                        }else {
                            if (a==2){
                                Toast.makeText(ChungThucTK_Activity.this, "PIN sai!!! bạn chỉ còn 1 lần nhập!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ChungThucTK_Activity.this, "Bạn đã nhập PIN 3 lần sai! Hủy xác thực", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        }
                    }
            }
        });
    }
}
