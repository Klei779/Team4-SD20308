package entity;

import java.math.BigDecimal;

public class DoUong {
    int maDoUong;
    int maLoaiDoUong;
    int maCongThuc;
    String tenDoUong;
    int giaTien;
    int giaVon;
    String moTa;
    String hinhAnh;
    BigDecimal khuyenmai;
    boolean trangthai;

    public int getMaDoUong() {
        return maDoUong;
    }

    public void setMaDoUong(int maDoUong) {
        this.maDoUong = maDoUong;
    }

    public int getMaLoaiDoUong() {
        return maLoaiDoUong;
    }

    public void setMaLoaiDoUong(int maLoaiDoUong) {
        this.maLoaiDoUong = maLoaiDoUong;
    }

    public int getMaCongThuc() {
        return maCongThuc;
    }

    public void setMaCongThuc(int maCongThuc) {
        this.maCongThuc = maCongThuc;
    }

    public String getTenDoUong() {
        return tenDoUong;
    }

    public void setTenDoUong(String tenDoUong) {
        this.tenDoUong = tenDoUong;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getGiaVon() {
        return giaVon;
    }

    public void setGiaVon(int giaVon) {
        this.giaVon = giaVon;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public BigDecimal getKhuyenmai() {
        return khuyenmai;
    }

    public void setKhuyenmai(BigDecimal khuyenmai) {
        this.khuyenmai = khuyenmai;
    }

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }
}