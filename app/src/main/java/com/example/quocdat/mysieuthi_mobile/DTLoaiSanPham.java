package com.example.quocdat.mysieuthi_mobile;

/**
 * Created by quocdat on 11/17/2015.
 */
public class DTLoaiSanPham {
    String tenloai;
    String motaloai;
    public DTLoaiSanPham(){
        this.motaloai = null;
        this.tenloai = null;
    }
    public DTLoaiSanPham(String tenloai, String motaloai) {
        this.tenloai = tenloai;
        this.motaloai = motaloai;
    }
}
