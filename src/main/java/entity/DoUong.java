package entity;

import java.math.BigDecimal;

public class DoUong {

    private int maDoUong;
    private int maLoai;
    private int maCongThuc;
    private String tenDoUong;
    private int giaTien;
    private BigDecimal giaVon;
    private String moTa;
    private String hinhAnh;
    private BigDecimal khuyenMai;
    private boolean trangThai;

    public DoUong() {
    }

    // Constructor dùng khi INSERT (không có ID)
    public DoUong(int maLoai, int maCongThuc, String tenDoUong,
                  int giaTien, BigDecimal giaVon,
                  String moTa, String hinhAnh,
                  BigDecimal khuyenMai, boolean trangThai) {

        this.maLoai = maLoai;
        this.maCongThuc = maCongThuc;
        this.tenDoUong = tenDoUong;
        this.giaTien = giaTien;
        this.giaVon = giaVon;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.khuyenMai = khuyenMai;
        this.trangThai = trangThai;
    }

    // Getter & Setter

    public int getMaDoUong() {
        return maDoUong;
    }

    public void setMaDoUong(int maDoUong) {
        this.maDoUong = maDoUong;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
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

    public BigDecimal getGiaVon() {
        return giaVon;
    }

    public void setGiaVon(BigDecimal giaVon) {
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

    public BigDecimal getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(BigDecimal khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

}