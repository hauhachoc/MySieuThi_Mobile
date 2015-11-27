package com.example.quocdat.mysieuthi_mobile;

/**
 * Created by quocdat on 11/9/2015.
 */
import android.util.Log;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quocdat on 11/9/2015.
 */
public class PTDataTaiKhoan {
    private ParseObject taiKhoanOj;
    private ArrayList<DTTaiKhoan> list;

    public  PTDataTaiKhoan(){

    }

    public void TaoTaiKhoan(DTTaiKhoan DTTK){
        taiKhoanOj = new ParseObject("TaiKhoan");
        taiKhoanOj.put("mataikhoan", DTTK.mataikhoan);
        taiKhoanOj.put("matkhau", DTTK.matkhau);
        taiKhoanOj.put("tentaikhoan", DTTK.tentaikhoan);
        taiKhoanOj.put("sotien", DTTK.sotien);
        taiKhoanOj.put("diachi", DTTK.diachi);
        taiKhoanOj.put("loaitaikhoan", DTTK.loaitaikhoan);
        taiKhoanOj.put("danhgia", DTTK.danhgia);
        taiKhoanOj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
    }
    public void DoiMatKhau(final String mataikhoan, final String passNew){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TaiKhoan");
        query.whereEqualTo("mataikhoan", mataikhoan);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    object.put("matkhau", passNew);
                    object.saveInBackground();
                }
            }
        });
    }
    public ArrayList<DTTaiKhoan> DanhSachTaiKhoan() {
        list = new ArrayList<DTTaiKhoan>();
        ParseQuery<ParseObject> parseTK = ParseQuery.getQuery("TaiKhoan");
        parseTK.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                list.clear();
                if(e == null) {
                    for (ParseObject post : objects) {
                        DTTaiKhoan DTTK = new DTTaiKhoan();
                        DTTK.mataikhoan = post.getString("mataikhoan");
                        DTTK.matkhau = post.getString("matkhau");
                        DTTK.tentaikhoan = post.getString("tentaikhoan");
                        DTTK.sotien = post.getString("sotien");
                        DTTK.diachi = post.getString("diachi");
                        DTTK.loaitaikhoan = post.getString("loaitaikhoan");
                        DTTK.danhgia = post.getString("danhgia");
                        list.add(DTTK);
                    }
                }else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }});
        return list;
    }
}