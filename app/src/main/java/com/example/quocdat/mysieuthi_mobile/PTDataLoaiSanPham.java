package com.example.quocdat.mysieuthi_mobile;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by quocdat on 11/17/2015.
 */

public class PTDataLoaiSanPham {
    private ParseObject loaiSanPhamOj;
    public static ArrayList<DTLoaiSanPham> list;
    public PTDataLoaiSanPham(){

    }

    public void ThemLoaiSanPham(DTLoaiSanPham DTLSP){
        loaiSanPhamOj = new ParseObject("LoaiSanPham");
        loaiSanPhamOj.put("tenloai", DTLSP.tenloai);
        loaiSanPhamOj.put("motaloai", DTLSP.motaloai);
        loaiSanPhamOj.saveInBackground();
    }
}
