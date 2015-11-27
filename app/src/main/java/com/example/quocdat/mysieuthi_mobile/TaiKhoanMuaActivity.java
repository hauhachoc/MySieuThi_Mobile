package com.example.quocdat.mysieuthi_mobile;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class TaiKhoanMuaActivity extends AppCompatActivity {
    DTTaiKhoan DTTK;
    PTDataTaiKhoan PTDTK;
    private Button btdoimk, naptien;
    private TextView tvmatk, tvttk, tvdiachi,tvloaitk, tvsotien;
    String mataikhoan;
    String matkhau;
    String tentaikhoan;
    String loaitaikhoan;
    String diachi;
    String sotien;
    String danhgia;
    private int sotien1, menhgia;
    private String sotien2;
    private RadioGroup grgia;
    private RadioButton radio50, radio100, radio500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan_mua);
        khaiBao();
        loadTabsmua();
        doiMatKhau();
        napTien();
    }
    public void napTien(){
        naptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sotien1 = Integer.parseInt(sotien);
                int selectedId = grgia.getCheckedRadioButtonId();
                radio50=(RadioButton)findViewById(selectedId);
                radio100=(RadioButton)findViewById(selectedId);
                radio500=(RadioButton)findViewById(selectedId);
                switch (grgia.getCheckedRadioButtonId()) {
                    case R.id.radio100:
                        menhgia = 100000;
                        sotien1 += menhgia;
                        sotien2 = String.valueOf(sotien1);
                        saveTien();
                        break;
                    case R.id.radio500:
                        menhgia = 500000;
                        sotien1 += menhgia;
                        sotien2 = String.valueOf(sotien1);
                        saveTien();
                        break;
                    default:
                        menhgia = 50000;
                        sotien1 += menhgia;
                        sotien2 = String.valueOf(sotien1);
                        saveTien();
                        break;
                }
            }
        });
    }
    public void saveTien(){
        sotien = sotien2;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TaiKhoan");
        query.whereEqualTo("mataikhoan", mataikhoan);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    object.put("sotien", sotien);
                    object.saveInBackground();
                }
            }
        });
        Toast.makeText(TaiKhoanMuaActivity.this,"Bạn đã nạp thêm : "+ menhgia +"\n"+"Tổng số tiền :"+sotien,Toast.LENGTH_SHORT).show();
        tvsotien.setText(sotien);
    }
    public void khaiBao(){
        PTDTK = new PTDataTaiKhoan();
        //Thuộc về tab thông tin tài khoản mua
        btdoimk = (Button) findViewById(R.id.btnDoimk);
        tvmatk = (TextView) findViewById(R.id.tvmataikhoan);
        tvttk = (TextView) findViewById(R.id.tvtentaikhoan);
        tvloaitk= (TextView) findViewById(R.id.tvloaitk);
        tvdiachi= (TextView) findViewById(R.id.tvdiachi);
        tvsotien= (TextView) findViewById(R.id.tvsotien);
        //tab nạp tiền
        grgia = (RadioGroup) findViewById(R.id.grNapTien);
        naptien = (Button) findViewById(R.id.btnNap);
        //Lấy dữ liệu từ intent
        Intent intent = getIntent();
        mataikhoan = intent.getStringExtra("sdt");
        matkhau = intent.getStringExtra("mk");
        tentaikhoan = intent.getStringExtra("ttk");
        loaitaikhoan = intent.getStringExtra("ltk");
        diachi = intent.getStringExtra("dc");
        sotien = intent.getStringExtra("sotien");
        danhgia = intent.getStringExtra("danhgia");
        //Đổ dữ liệu vào tab thông tin tài khoản mua
        tvmatk.setText(mataikhoan);
        tvttk.setText(tentaikhoan);
        tvloaitk.setText(loaitaikhoan);
        tvdiachi.setText(diachi);
        tvsotien.setText(sotien);
    }
    public void doiMatKhau(){
        btdoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(TaiKhoanMuaActivity.this);
                dialog.setContentView(R.layout.custom_adapter_dialog_doimatkhau);
                dialog.setTitle("Đổi mật khẩu");
                dialog.show();
                // set the custom dialog components - text, image and button
                final EditText mkcu = (EditText)dialog.findViewById(R.id.edtmkcu);
                final EditText mkmoi = (EditText)dialog.findViewById(R.id.edtmk1);
                final EditText nhaplaimkmoi = (EditText)dialog.findViewById(R.id.edtmk2);
                Button doimk = (Button)dialog.findViewById(R.id.btnXacNhan);
                Button huydoimk = (Button)dialog.findViewById(R.id.btnHuy);
                huydoimk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                doimk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mkcu.getText().toString().equals("") || mkmoi.getText().toString().equals("") || nhaplaimkmoi.getText().toString().equals("")) {
                            if(mkcu.getText().toString().equals("")){mkcu.setError("Không được bỏ trống!");}
                            if(mkmoi.getText().toString().equals("")){mkmoi.setError("Không được bỏ trống!");}
                            if(nhaplaimkmoi.getText().toString().equals("")){nhaplaimkmoi.setError("Không được bỏ trống!");}
                        } else {
                            if (matkhau.equals(mkcu.getText().toString())) {
                                if (mkmoi.getText().toString().equals(nhaplaimkmoi.getText().toString())) {
                                    PTDTK.DoiMatKhau(mataikhoan, mkmoi.getText().toString());
                                    dialog.dismiss();
                                    Toast.makeText(TaiKhoanMuaActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                } else {
                                    mkmoi.setError("Mật khẩu mới và nhập lại phải trùng nhau!");
                                    mkmoi.requestFocus();
                                    nhaplaimkmoi.setError("Mật khẩu mới và nhập lại phải trùng nhau!");
                                }
                            } else {
                                mkcu.setError("Nhập sai mật khẩu!");
                                mkcu.requestFocus();
                            }
                        }
                    }
                });
            }
        });
    }
    public void loadTabsmua()
    {
        //ánh xạ
        final TabHost tab = (TabHost) findViewById(R.id.tabHostmua);
        //setup
        tab.setup();
        TabHost.TabSpec spec;
        //tab1
        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.tab_tkm_thongtin);
        spec.setIndicator("THÔNG TIN TÀI KHOẢN");
        tab.addTab(spec);
        //tab2
        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.tab_tkm_giaodich);
        spec.setIndicator("GIAO DỊCH - MUA BÁN");
        tab.addTab(spec);
        //tab3
        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.tab_tkm_naptien);
        spec.setIndicator("NẠP TIỀN");
        tab.addTab(spec);
        //Tab mặc định
        tab.setCurrentTab(0);
        //Sự kiện bên dưới comment này dùng khi mà chuyển tab nha Luân
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String arg0) {
                String s ="";
                if(tab.getCurrentTab()==0){
                    //chuyển tab 1
                }else{
                    if(tab.getCurrentTab()==1){
                        //chuyển tab 2
                    }else {
                        //chuyển tab 3
                    }
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
