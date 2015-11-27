package com.example.quocdat.mysieuthi_mobile;

/**
 * Created by quocdat on 11/9/2015.
 */
public class DTTaiKhoan {
    String mataikhoan;
    String matkhau;
    String tentaikhoan;
    String sotien;
    String diachi;
    String loaitaikhoan;
    String danhgia;

    public DTTaiKhoan() {
        this.mataikhoan = null;
        this.matkhau = null;
        this.tentaikhoan = null;
        this.sotien = null;
        this.diachi = null;
        this.loaitaikhoan = null;
        this.danhgia = null;
    }
    public DTTaiKhoan(String mataikhoan, String matkhau, String tentaikhoan, String sotien, String diachi, String loaitaikhoan, String danhgia) {
        this.mataikhoan = mataikhoan;
        this.matkhau = matkhau;
        this.tentaikhoan = tentaikhoan;
        this.sotien = sotien;
        this.diachi = diachi;
        this.loaitaikhoan = loaitaikhoan;
        this.danhgia = danhgia;
    }
}
