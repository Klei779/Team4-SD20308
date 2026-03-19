package entity;

public class Drink {
    String maDoUong;
    String maLoaiDoUong;
String tenDoUong;
int giaTien;
int giaVon;
String moTa;
String maCongThuc;
String hinhAnh;
float khuyenmai;
boolean trangthai;

public Drink(){}

    public Drink(String maDoUong, String maLoaiDoUong, String tenDoUong, int giaTien, int giaVon, String moTa, String maCongThuc, String hinhAnh, float khuyenmai, boolean trangthai, float khuyenMai) {
        this.maDoUong = maDoUong;
        this.maLoaiDoUong = maLoaiDoUong;
        this.tenDoUong = tenDoUong;
        this.giaTien = giaTien;
        this.giaVon = giaVon;
        this.moTa = moTa;
        this.maCongThuc = maCongThuc;
        this.hinhAnh = hinhAnh;
        this.khuyenmai = khuyenmai;
        this.trangthai = trangthai;
        this.khuyenMai = khuyenMai;
    }

    public float getKhuyenmai() {
        return khuyenmai;
    }

    public void setKhuyenmai(float khuyenmai) {
        this.khuyenmai = khuyenmai;
    }

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    float khuyenMai;


    public String getMaDoUong() {
        return maDoUong;
    }

    public void setMaDoUong(String maDoUong) {
        this.maDoUong = maDoUong;
    }

    public String getMaLoaiDoUong() {
        return maLoaiDoUong;
    }

    public void setMaLoaiDoUong(String maLoaiDoUong) {
        this.maLoaiDoUong = maLoaiDoUong;
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

    public String getMaCongThuc() {
        return maCongThuc;
    }

    public void setMaCongThuc(String maCongThuc) {
        this.maCongThuc = maCongThuc;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public float getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(float khuyenMai) {
        this.khuyenMai = khuyenMai;
    }
}
