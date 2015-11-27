package com.example.quocdat.mysieuthi_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class TaoTaiKhoanActivity extends AppCompatActivity {
    PTDataTaiKhoan PTDTTK;
    ArrayList<DTTaiKhoan> listDTTK;
    RadioGroup loaiTaiKhoan;
    DTTaiKhoan DTTK;
    EditText mataikhoan, matkhau, nhaplaimatkhau, tentaikhoan, diachi;
    Button dangki;
    CheckBox dieukhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_tai_khoan);

        khaibao();
        batLoiTaoTaiKhoan();

    }
    public void khaibao(){
        dangki = (Button)findViewById(R.id.dangki);
        loaiTaiKhoan = (RadioGroup)findViewById(R.id.loaiTaiKhoan);
        mataikhoan = (EditText)findViewById(R.id.mataikhoan);
        matkhau = (EditText)findViewById(R.id.matkhau);
        nhaplaimatkhau = (EditText)findViewById(R.id.nhaplaimatkhau);
        tentaikhoan = (EditText)findViewById(R.id.tentaikhoan);
        diachi = (EditText)findViewById(R.id.diachi);
        dieukhoan = (CheckBox)findViewById(R.id.dieukhoan);
        dieukhoan.setChecked(false);
        dangki.setEnabled(false);
        dieukhoan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked == true) {
                    dangki.setEnabled(true);
                } else {
                    dangki.setEnabled(false);
                }
            }
        });
    }
    public void batLoiTaoTaiKhoan(){
        PTDTTK = new PTDataTaiKhoan();
        listDTTK = new ArrayList<>();
        listDTTK = PTDTTK.DanhSachTaiKhoan();
        dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mataikhoan.getText().toString().equals("") || matkhau.getText().toString().equals("") || nhaplaimatkhau.getText().toString().equals("") || tentaikhoan.getText().toString().equals("") || diachi.getText().toString().equals("")) {
                    if(mataikhoan.getText().toString().equals("")){mataikhoan.setError("Không được bỏ trống!");}
                    if(matkhau.getText().toString().equals("")){matkhau.setError("Không được bỏ trống!");}
                    if(nhaplaimatkhau.getText().toString().equals("")){nhaplaimatkhau.setError("Không được bỏ trống!");}
                    if(tentaikhoan.getText().toString().equals("")){tentaikhoan.setError("Không được bỏ trống!");}
                    if(diachi.getText().toString().equals("")){diachi.setError("Không được bỏ trống!");}
                } else {
                    if (mataikhoan.getText().toString().length() <= 9 || mataikhoan.getText().toString().length() >= 13) {
                        mataikhoan.setError("Số điện thoại từ 9 đến 13 số");
                        mataikhoan.requestFocus();
                    } else {
                        if (matkhau.getText().toString().equals(nhaplaimatkhau.getText().toString())) {
                            try {
                                int a = Integer.parseInt(mataikhoan.getText().toString());
                                boolean testTK=true;
                                for(int i=0;i<listDTTK.size();i++) {
                                    if(listDTTK.get(i).mataikhoan.equals(mataikhoan.getText().toString())) {
                                        testTK=false;
                                        break;
                                    }
                                }
                                if(testTK==true){
                                    taoTaiKhoan();
                                }else {
                                    mataikhoan.setError("Tài khoản này đã tồn tại trong hệ thông!");
                                    mataikhoan.requestFocus();
                                }
                            }catch (NumberFormatException E){
                                mataikhoan.setError("Tài khoản phải là số điện thoại!");
                                mataikhoan.requestFocus();
                            }
                        } else {
                            matkhau.setError("Mật khẩu không trùng khớp!");
                            matkhau.requestFocus();
                            nhaplaimatkhau.setError("Mật khẩu không trùng khớp!");
                        }
                    }
                }
            }
        });
    }
    public void taoTaiKhoan(){
        DTTK = new DTTaiKhoan();

        switch (loaiTaiKhoan.getCheckedRadioButtonId()) {
            case R.id.radioButton2:
                DTTK.loaitaikhoan = "Tài khoản bán";
                break;
            default:
                DTTK.loaitaikhoan = "Tài khoản mua";
                break;
        }

        DTTK.mataikhoan = mataikhoan.getText().toString();
        DTTK.matkhau = matkhau.getText().toString();
        DTTK.tentaikhoan = tentaikhoan.getText().toString();
        DTTK.diachi = diachi.getText().toString();
        DTTK.sotien = "0";
        DTTK.danhgia = "0";
        //Xác nhận xong mới cho tạo
        xacThuc();
        finish();
    }
    public void xacThuc(){
        int min = 1000;
        int max = 9999;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        String pin = String.valueOf(i1);
        Toast.makeText(TaoTaiKhoanActivity.this, "PIN : " + pin, Toast.LENGTH_SHORT).show();
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mataikhoan.getText().toString(), null, pin, null, null);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"SMS failed, please try again.",
                    Toast.LENGTH_LONG).show();
        }

        Intent i = new Intent(TaoTaiKhoanActivity.this, ChungThucTK_Activity.class);
        i.putExtra("sdt",DTTK.mataikhoan);
        i.putExtra("ltk",DTTK.loaitaikhoan);
        i.putExtra("mk",DTTK.matkhau);
        i.putExtra("ttk",DTTK.tentaikhoan);
        i.putExtra("dc",DTTK.diachi);
        i.putExtra("pin", pin);
        startActivity(i);
        finish();
    }
}
