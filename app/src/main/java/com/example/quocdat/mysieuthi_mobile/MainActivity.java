package com.example.quocdat.mysieuthi_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.Parse;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView taoTaiKhoan, timMatKhau;
    EditText ID, PASS;
    Button LOG;
    PTDataTaiKhoan PTDTTK;
    ArrayList<DTTaiKhoan> listDTTK;
    CheckBox savepass;
    String prefname="my_data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        savepass = (CheckBox)findViewById(R.id.savePass);
        Parse.enableLocalDatastore(MainActivity.this);
        Parse.initialize(MainActivity.this, "kO1HfqwxnDCI4xcoDPLAPq7OgTW9HQPUymW8tli4", "hsDrKcaCsnIi2KJwocckRqfU6gKNmxGEGmdjmrqY");

        taoTaiKhoan_quenMatKhau();
        dangNhap();

    }

    public void dangNhap(){
        PTDTTK = new PTDataTaiKhoan();
        listDTTK = new ArrayList<>();
        listDTTK = PTDTTK.DanhSachTaiKhoan();

        ID = (EditText)findViewById(R.id.ID);
        PASS = (EditText)findViewById(R.id.PASS);
        LOG = (Button)findViewById(R.id.LOG);

        LOG.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                boolean testSaiTaiKhoan = true;
                if (ID.getText().toString().equals("") && PASS.getText().toString().equals("")) {
                    ID.setError("Không bỏ trống TÀI KHOẢN và MẬT KHẨU !");
                    PASS.setError("Không bỏ trống TÀI KHOẢN và MẬT KHẨU !");
                } else {
                    for (int i = 0; i < listDTTK.size(); i++) {
                        if (ID.getText().toString().equals(listDTTK.get(i).mataikhoan)) {
                            testSaiTaiKhoan = false;
                            if (PASS.getText().toString().equals(listDTTK.get(i).matkhau)) {
                                if (listDTTK.get(i).loaitaikhoan.equals("Tài khoản mua")) {
                                    Intent intent = new Intent(MainActivity.this, TaiKhoanMuaActivity.class);
                                    intent.putExtra("sdt",listDTTK.get(i).mataikhoan);
                                    intent.putExtra("ltk",listDTTK.get(i).loaitaikhoan);
                                    intent.putExtra("mk",listDTTK.get(i).matkhau);
                                    intent.putExtra("ttk",listDTTK.get(i).tentaikhoan);
                                    intent.putExtra("dc",listDTTK.get(i).diachi);
                                    intent.putExtra("sotien", listDTTK.get(i).sotien );
                                    intent.putExtra("danhgia", listDTTK.get(i).danhgia);
                                    startActivity(intent);
                                    overridePendingTransition(android.support.v7.appcompat.R.anim.abc_slide_in_top, android.support.v7.appcompat.R.anim.abc_slide_out_bottom);
                                } else {
                                    Intent intent = new Intent(MainActivity.this, TaiKhoanBanActivity.class);
                                    intent.putExtra("sdt",listDTTK.get(i).mataikhoan);
                                    intent.putExtra("ltk",listDTTK.get(i).loaitaikhoan);
                                    intent.putExtra("mk",listDTTK.get(i).matkhau);
                                    intent.putExtra("ttk",listDTTK.get(i).tentaikhoan);
                                    intent.putExtra("dc",listDTTK.get(i).diachi);
                                    intent.putExtra("sotien", listDTTK.get(i).sotien );
                                    intent.putExtra("danhgia", listDTTK.get(i).danhgia);
                                    startActivity(intent);
                                    overridePendingTransition(android.support.v7.appcompat.R.anim.abc_slide_in_top, android.support.v7.appcompat.R.anim.abc_slide_out_bottom);
                                }
                            } else {
                                PASS.setError("Sai mật khẩu rồi!");
                                PASS.requestFocus();
                            }
                        } else {
                        }
                    }
                    if (testSaiTaiKhoan == true) {
                        ID.setError("Sai tài khoản rồi!");
                        ID.requestFocus();
                    }
                }
            }
        });
    }
    public void taoTaiKhoan_quenMatKhau(){
        taoTaiKhoan = (TextView)findViewById(R.id.taoTaiKhoan);
        timMatKhau = (TextView)findViewById(R.id.timMatKhau);
        makeTextViewHyperlink(timMatKhau);
        makeTextViewHyperlink(taoTaiKhoan);
        taoTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, TaoTaiKhoanActivity.class);
                startActivity(i);
                overridePendingTransition(android.support.v7.appcompat.R.anim.abc_slide_in_top, android.support.v7.appcompat.R.anim.abc_slide_out_bottom);
            }
        });
        timMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, TimMatKhauActivity.class);
                startActivity(i);
                overridePendingTransition(android.support.v7.appcompat.R.anim.abc_slide_in_top, android.support.v7.appcompat.R.anim.abc_slide_out_bottom);
            }
        });
    }
    public void makeTextViewHyperlink(TextView tv) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(tv.getText());
        ssb.setSpan(new URLSpan("#"), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ssb, TextView.BufferType.SPANNABLE);
    }
    @Override
    protected void onPause() {
        super.onPause();
        savingPreferences();
    }
    @Override
    protected void onResume(){
        super.onResume();
        restoringPreferences();
        taoTaiKhoan_quenMatKhau();
        dangNhap();
    }
    public void savingPreferences()
    {
        SharedPreferences pre=getSharedPreferences(prefname, MODE_PRIVATE);
        SharedPreferences.Editor editor=pre.edit();
        boolean bchk=savepass.isChecked();
        if(!bchk)
        {
            editor.clear();
        }
        else
        {
            editor.putString("user", ID.getText().toString());
            editor.putString("pwd", PASS.getText().toString());
            editor.putBoolean("checked", bchk);
        }
        editor.commit();
    }
    public void restoringPreferences()
    {
        SharedPreferences pre=getSharedPreferences
                (prefname,MODE_PRIVATE);
        boolean bchk=pre.getBoolean("checked", false);
        if(bchk)
        {
            String user=pre.getString("user", "");
            String pwd=pre.getString("pwd", "");
            ID.setText(user);
            PASS.setText(pwd);
        }
        savepass.setChecked(bchk);
    }
}
