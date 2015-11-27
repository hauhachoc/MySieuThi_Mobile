package com.example.quocdat.mysieuthi_mobile;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanBanActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    String imgDecodableString;
    Button chonanh, themsanpham, themloaisanpham, btdoimk, huyCapNhatLoaiSanPham, huyCapNhatSanPham;
    EditText tensp, giasp, motasp, tenlsp,motalsp;
    DTSanPham DTSP;
    DTLoaiSanPham DTLSP;
    Spinner loaisp;
    PTDataSanPham PTDSP;
    PTDataLoaiSanPham PTDLSP;
    PTDataTaiKhoan PTDTK;
    ListView listViewdanhSachLoaiSanPham, listViewdanhSachSanPham;
    ImageView imgView;
    ArrayList<DTLoaiSanPham> listDTLSP;
    ArrayList<DTSanPham> listDTSP;
    String[] loaiSpinner;
    TextView tvmatk, tvttk, tvdiachi,tvloaitk, tvsotien, tvdanhgia;
    String mataikhoan,matkhau,tentaikhoan, loaitaikhoan, diachi, sotien, danhgia;
    customAdapterSanPham adapterSanPham;
    customAdapterLoaiSanPham adapterLoaiSanPham;
    int checkData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan_ban);
        checkData = 0;
        new xyz().execute();
        khaiBao();
        chonAnh();
        ThemLoaiSanPham();
        ThemSanPham();
        loadTabsban();
        doiMatKhau();
    }

    public void khaiBao() {
        //Thuộc về tab thông tin
        btdoimk = (Button) findViewById(R.id.btnDoimk);
        tvmatk = (TextView) findViewById(R.id.tvmataikhoan);
        tvttk = (TextView) findViewById(R.id.tvtentaikhoan);
        tvloaitk= (TextView) findViewById(R.id.tvloaitk);
        tvdiachi= (TextView) findViewById(R.id.tvdiachi);
        tvsotien= (TextView) findViewById(R.id.tvsotien);
        tvdanhgia = (TextView) findViewById(R.id.tvdanhgia);
        PTDTK = new PTDataTaiKhoan();
        //Thuộc về tab sản phẩm
        imgView = (ImageView) findViewById(R.id.img);
        PTDSP = new PTDataSanPham(TaiKhoanBanActivity.this);
        loaisp = (Spinner)findViewById(R.id.loaisanpham);
        tensp = (EditText)findViewById(R.id.tensanpham);
        giasp = (EditText)findViewById(R.id.giasanpham);
        motasp = (EditText)findViewById(R.id.motasanpham);
        chonanh = (Button) findViewById(R.id.hinhanh);
        themsanpham = (Button)findViewById(R.id.themsanpham);
        huyCapNhatSanPham = (Button)findViewById(R.id.huy_capnhat_sanpham);
        listViewdanhSachSanPham = (ListView)findViewById(R.id.listViewDanhSachSanPham);
        registerForContextMenu(listViewdanhSachSanPham);
        //Thuộc về tab loại sản phẩm
        PTDLSP = new PTDataLoaiSanPham();
        tenlsp = (EditText)findViewById(R.id.tenLoai);
        motalsp = (EditText)findViewById(R.id.moTaLoai);
        themloaisanpham = (Button)findViewById(R.id.themLoai);
        huyCapNhatLoaiSanPham = (Button)findViewById(R.id.huy_capnhat_loaisanpham);
        listViewdanhSachLoaiSanPham = (ListView)findViewById(R.id.listViewDanhSachLoaiSanPham);
        registerForContextMenu(listViewdanhSachLoaiSanPham);
        //Lấy dữ liệu từ intent
        Intent intent = getIntent();
        mataikhoan = intent.getStringExtra("sdt");
        matkhau = intent.getStringExtra("mk");
        tentaikhoan = intent.getStringExtra("ttk");
        loaitaikhoan = intent.getStringExtra("ltk");
        diachi = intent.getStringExtra("dc");
        sotien = intent.getStringExtra("sotien");
        danhgia = intent.getStringExtra("danhgia");
        //Đổ dữ liệu vào tab thông tin
        tvmatk.setText(mataikhoan);
        tvttk.setText(tentaikhoan);
        tvloaitk.setText(loaitaikhoan);
        tvdiachi.setText(diachi);
        tvsotien.setText(sotien);
        tvdanhgia.setText(danhgia);
    }
    public void LoadData() {
        LoadDataLoaiSanPham();
        LoadDataSanPham();
    }
    public void LoadDataLoaiSanPham(){
        //Dữ liệu từ bảng loại sản phẩm trên parse về app
        listDTLSP = new ArrayList<>();
        ParseQuery<ParseObject> parseTK = ParseQuery.getQuery("LoaiSanPham");
        parseTK.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                listDTLSP.clear();
                if (e == null) {
                    for (ParseObject post : objects) {
                        DTLoaiSanPham DTLSP = new DTLoaiSanPham();
                        DTLSP.tenloai = post.getString("tenloai");
                        DTLSP.motaloai = post.getString("motaloai");
                        listDTLSP.add(DTLSP);
                    }
                    //Đổ vào listView bên tab loại sản phẩm
                    adapterLoaiSanPham = new customAdapterLoaiSanPham(TaiKhoanBanActivity.this, listDTLSP);
                    listViewdanhSachLoaiSanPham.setAdapter(adapterLoaiSanPham);
                    //Đổ vào spinner bên tab thêm sản phẩm
                    String hold = new String();
                    for (int i = 0; i < listDTLSP.size(); i++) {
                        hold += listDTLSP.get(i).tenloai + "-";
                    }
                    loaiSpinner = hold.split("-");
                    ArrayAdapter<String> ad = new ArrayAdapter<>(TaiKhoanBanActivity.this, android.R.layout.simple_list_item_1, loaiSpinner);
                    loaisp.setAdapter(ad);
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }
    public void LoadDataSanPham(){
        //Dữ liệu từ bảng sản phẩm load vào listView tab san pham
        listDTSP = new ArrayList<DTSanPham>();
        ParseQuery<ParseObject> parseSp = ParseQuery.getQuery("SanPham");
        parseSp.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                listDTSP.clear();
                if(e == null) {
                    for (ParseObject post : objects) {
                        final DTSanPham DTSP = new DTSanPham();
                        DTSP.tensanpham = post.getString("tensanpham");
                        DTSP.giasanpham = post.getString("giasanpham");
                        DTSP.loaisanpham = post.getString("loaisanpham");
                        DTSP.motasanpham = post.getString("motasanpham");
                        DTSP.mataikhoan = post.getString("mataikhoan");
                        ParseFile fileObject = (ParseFile) post.get("hinhsanpham");
                        fileObject.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                DTSP.hinhsanpham = BitmapFactory.decodeByteArray(data, 0, data.length);
                            }
                        });
                        listDTSP.add(DTSP);
                    }
                    //Load listView
                    Toast.makeText(TaiKhoanBanActivity.this,String.valueOf(listDTSP.size()),Toast.LENGTH_SHORT).show();
                    adapterSanPham = new customAdapterSanPham(TaiKhoanBanActivity.this,listDTSP);
                    listViewdanhSachSanPham.setAdapter(adapterSanPham);
                }else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }
    public void ThemLoaiSanPham(){
        themloaisanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DTLSP = new DTLoaiSanPham();
                DTLSP.tenloai = tenlsp.getText().toString();
                DTLSP.motaloai = motalsp.getText().toString();
                if (DTLSP.tenloai.equals("")) {
                    tenlsp.setError("Không được bỏ trống tên loại");
                    tenlsp.requestFocus();
                } else {
                    checkData = 3;
                    new xyz().execute();
                }
            }
        });
    }
    public void ThemSanPham(){
        themsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DTSP = new DTSanPham();
                DTSP.tensanpham = tensp.getText().toString();
                DTSP.giasanpham = giasp.getText().toString();
                DTSP.motasanpham = motasp.getText().toString();
                DTSP.hinhsanpham = BitmapFactory.decodeFile(imgDecodableString);
                DTSP.loaisanpham = loaisp.getSelectedItem().toString();
                DTSP.mataikhoan = mataikhoan;
                if(DTSP.tensanpham.equals("")||DTSP.giasanpham.equals("")||DTSP.hinhsanpham == null){
                    if(DTSP.tensanpham.equals("")){tensp.setError("Không bỏ trống những mục đánh dâu *");}
                    if(DTSP.giasanpham.equals("")){giasp.setError("Không bỏ trống những mục đánh dâu *");}
                    if(DTSP.hinhsanpham == null){chonanh.setError("Không bỏ trống những mục đánh dâu *");}
                }else {
                    try {
                        Double a = Double.parseDouble(DTSP.giasanpham);
                        checkData = 1;
                        new xyz().execute();
                    }catch (Exception e){
                        giasp.setError("Giá tiền phải là số");
                        giasp.requestFocus();
                    }
                }
            }
        });
    }
    public void chonAnh() {
        chonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "Bạn vẫn chưa chọn hình!",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
    public void doiMatKhau(){
        btdoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(TaiKhoanBanActivity.this);
                dialog.setContentView(R.layout.custom_adapter_dialog_doimatkhau);
                dialog.setTitle("Đổi mật khẩu");
                dialog.show();
                // set the custom dialog components - text, image and button
                final EditText mkcu = (EditText) dialog.findViewById(R.id.edtmkcu);
                final EditText mkmoi = (EditText) dialog.findViewById(R.id.edtmk1);
                final EditText nhaplaimkmoi = (EditText) dialog.findViewById(R.id.edtmk2);
                Button doimk = (Button) dialog.findViewById(R.id.btnXacNhan);
                Button huydoimk = (Button) dialog.findViewById(R.id.btnHuy);
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
                                    Toast.makeText(TaiKhoanBanActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
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

    public void loadTabsban() {
        //ánh xạ
        final TabHost tab = (TabHost) findViewById(R.id.tabHostban);
        //setup
        tab.setup();
        TabHost.TabSpec spec;
        //tab1
        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.tab_tkb_thongtin);
        spec.setIndicator("THÔNG TIN");
        tab.addTab(spec);
        //tab2
        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.tab_tkb_giaodich);
        spec.setIndicator("SẢM PHẨM");
        tab.addTab(spec);
        //tab3
        spec = tab.newTabSpec("t3");
        spec.setContent(R.id.tab_tkb_ruttien);
        spec.setIndicator("LOẠI SẢN PHẨM");
        tab.addTab(spec);
        //Tab mặc định
        tab.setCurrentTab(0);
        //Sự kiện bên dưới comment này dùng khi mà chuyển tab nha Luân
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String arg0) {
                String s = "";
                if (tab.getCurrentTab() == 0) {
                    //chuyển tab 1
                    tab.getCurrentView().setAnimation(inFromRightAnimation());
                } else {
                    if (tab.getCurrentTab() == 1) {
                        //chuyển tab 2
                        tab.getCurrentView().setAnimation(inFromRightAnimation());
                    } else {
                        //chuyển tab 3
                        tab.getCurrentView().setAnimation(inFromRightAnimation());
                    }
                }
            }
        });
    }
    class customAdapterLoaiSanPham extends ArrayAdapter<DTLoaiSanPham>{

        public customAdapterLoaiSanPham(TaiKhoanBanActivity context, ArrayList<DTLoaiSanPham> DTLSP) {
            super(context, R.layout.custom_adapter_loaisanpham, DTLSP);
            // TODO Auto-generated constructor stub
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Activity context = (Activity)getContext();
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.custom_adapter_loaisanpham, null, true);

            TextView txt = (TextView)convertView.findViewById(R.id.cus1);
            TextView txt1 = (TextView)convertView.findViewById(R.id.cus2);
            TextView txt2 = (TextView)convertView.findViewById(R.id.cus3);

            DTLoaiSanPham DTLSP = getItem(position);

            txt.setText(String.valueOf(position+1));
            txt1.setText(DTLSP.tenloai);
            txt2.setText(DTLSP.motaloai);

            return convertView;
        }
    }
    class customAdapterSanPham extends ArrayAdapter<DTSanPham>{

        public customAdapterSanPham(TaiKhoanBanActivity context, ArrayList<DTSanPham> DTSP) {
            super(context, R.layout.custom_adapter_sanpham, DTSP);
            // TODO Auto-generated constructor stub
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Activity context = (Activity)getContext();
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.custom_adapter_sanpham, null, true);
            TextView stt = (TextView)convertView.findViewById(R.id.sttsp);
            ImageView img = (ImageView)convertView.findViewById(R.id.imgsp);
            TextView txt = (TextView)convertView.findViewById(R.id.tvtensp);
            TextView txt1 = (TextView)convertView.findViewById(R.id.tvgiasp);
            TextView txt2 = (TextView)convertView.findViewById(R.id.tvloaisp);
            TextView txt3 = (TextView)convertView.findViewById(R.id.tvmotasp);

            DTSanPham DTSP = getItem(position);

            stt.setText(String.valueOf(position+1));
            img.setImageBitmap(DTSP.hinhsanpham);
            txt.setText(DTSP.tensanpham);
            txt1.setText(DTSP.giasanpham);
            txt2.setText(DTSP.loaisanpham);
            txt3.setText(DTSP.motasanpham);

            return convertView;
        }
    }

    private class xyz extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(TaiKhoanBanActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setTitle("Xử lý dữ liệu");
            this.dialog.setMessage("Đang tải ...");
            this.dialog.show();
        }


        @Override
        protected Void doInBackground(Void... arg0) {
            if(checkData == 0){
                LoadData();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(checkData==1) {
                ParseObject sanPhamOj = new ParseObject("SanPham");
                Bitmap bitmap = DTSP.hinhsanpham;
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();
                // Create the ParseFile
                ParseFile file = new ParseFile("sanpham.png", image);
                file.saveInBackground();
                sanPhamOj.put("tensanpham", DTSP.tensanpham);
                sanPhamOj.put("giasanpham", DTSP.giasanpham);
                sanPhamOj.put("loaisanpham", DTSP.loaisanpham);
                sanPhamOj.put("motasanpham", DTSP.motasanpham);
                sanPhamOj.put("mataikhoan", DTSP.mataikhoan);
                sanPhamOj.put("hinhsanpham", file);
                // Create the class and the columns
                sanPhamOj.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(TaiKhoanBanActivity.this, "Đã thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                            LoadDataSanPham();
                        }
                    }
                });
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(checkData==3) {
                ParseObject taiKhoanOj = new ParseObject("LoaiSanPham");
                taiKhoanOj.put("tenloai", DTLSP.tenloai);
                taiKhoanOj.put("motaloai", DTLSP.motaloai);
                taiKhoanOj.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(TaiKhoanBanActivity.this, "Đã thêm loại sản phẩm thành công", Toast.LENGTH_SHORT).show();
                            LoadDataLoaiSanPham();
                        }
                    }
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Void unused) {
            if (this.dialog.isShowing()) {
                //xóa trắng thêm loại sản phẩm
                tenlsp.setText(null);
                motalsp.setText(null);
                //xóa trắng thêm sản phẩm
                tensp.setText(null);
                giasp.setText(null);
                motasp.setText(null);
                imgView.setImageBitmap(null);
                //Tắt progressdialog
                this.dialog.dismiss();
            }
        }

    }
    public Animation inFromRightAnimation()
    {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(300);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listViewDanhSachSanPham) {
            getMenuInflater().inflate(R.menu.context_menu_sanpham, menu);
        }
        if (v.getId()==R.id.listViewDanhSachLoaiSanPham) {
            getMenuInflater().inflate(R.menu.context_menu_loaisanpham, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit_loaiSanPham:
                DTLSP = new DTLoaiSanPham();
                DTLSP = listDTLSP.get(info.position);
                tenlsp.setText(DTLSP.tenloai);
                motalsp.setText(DTLSP.motaloai);
                themloaisanpham.setText("Cập nhật loại sản phẩm");
                huyCapNhatLoaiSanPham.setVisibility(View.VISIBLE);
                huyCapNhatLoaiSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tenlsp.setText(null);
                        motalsp.setText(null);
                        themloaisanpham.setText("Thêm");
                        huyCapNhatLoaiSanPham.setVisibility(View.GONE);
                    }
                });
                return true;
            case R.id.delete_loaiSanPham:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Xem lại
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //Đồng ý
                                break;
                        }}};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Bạn có chắc chắn muốn xóa loại sản phẩm này không?").setPositiveButton("Xem lại", dialogClickListener).setNegativeButton("Đồng ý", dialogClickListener).show();
                return true;
            case R.id.edit_SanPham:
                DTSP = new DTSanPham();
                DTSP = listDTSP.get(info.position);
                tensp.setText(DTSP.tensanpham);
                giasp.setText(DTSP.giasanpham);
                motasp.setText(DTSP.motasanpham);
                imgView.setImageBitmap(DTSP.hinhsanpham);
                for(int i=0;i<loaisp.getAdapter().getCount();i++){
                    if(loaisp.getAdapter().getItem(i).toString().equals(DTSP.loaisanpham)){
                        loaisp.setSelection(i);
                    }
                }
                themsanpham.setText("Cập nhật sản phẩm");
                huyCapNhatSanPham.setVisibility(View.VISIBLE);
                huyCapNhatSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tensp.setText(null);
                        giasp.setText(null);
                        motasp.setText(null);
                        imgView.setImageBitmap(null);
                        themsanpham.setText("Thêm sản phẩm");
                        huyCapNhatSanPham.setVisibility(View.GONE);
                    }
                });
                return true;
            case R.id.delete_SanPham:
                DialogInterface.OnClickListener dialogClickListener1 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Xem lại
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //Đồng ý
                                break;
                        }}};
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không?").setPositiveButton("Xem lại", dialogClickListener1).setNegativeButton("Đồng ý", dialogClickListener1).show();
                return true;
            default:

        }
        return super.onContextItemSelected(item);
    }
}
