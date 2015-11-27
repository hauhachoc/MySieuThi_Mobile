package com.example.quocdat.mysieuthi_mobile;

import android.graphics.Bitmap;

import java.net.URI;

/*
 * Created by quocdat on 11/9/2015.
 */
public class DTSanPham {
    String tensanpham;
    String giasanpham;
    Bitmap hinhsanpham;
    String loaisanpham;
    String motasanpham;
    String mataikhoan;
    public DTSanPham(){
        this.loaisanpham = null;
        this.tensanpham = null;
        this.giasanpham = null;
        this.hinhsanpham = null;
        this.motasanpham = null;
        this.mataikhoan = null;
    }
    public DTSanPham( String tensanpham, String giasanpham, String loaisanpham, Bitmap hinhsanpham, String motasanpham, String mataikhoan) {
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.loaisanpham = loaisanpham;
        this.hinhsanpham = hinhsanpham;
        this.motasanpham = motasanpham;
        this.mataikhoan = mataikhoan;
    }
}
