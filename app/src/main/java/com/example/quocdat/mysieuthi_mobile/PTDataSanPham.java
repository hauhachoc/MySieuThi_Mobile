package com.example.quocdat.mysieuthi_mobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.util.Log;
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


/**
 * Created by quocdat on 11/9/2015.
 */
public class PTDataSanPham {
    private ArrayList<DTSanPham> list;
    private ParseObject sanPhamOj;
    private Context context;
    public PTDataSanPham(Context context){
        this.context = context;
    }


    public void ThemSanPham(DTSanPham DTSP){

    }
    public ArrayList<DTSanPham> DanhSachSanPham() {
        list = new ArrayList<DTSanPham>();

        return list;
    }

}
