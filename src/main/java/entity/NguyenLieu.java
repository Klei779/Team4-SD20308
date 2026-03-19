package entity;

import java.util.Date;

public class NguyenLieu {

    private String maNguyenLieu;
    private String maLoaiNguyenLieu;
    private String tenNguyenLieu;
    private float soLuongTon;
    private String donVi;
    private Date ngayHetHan;
    private String moTa;

    public NguyenLieu() {
    }

    public NguyenLieu(String maNguyenLieu, String maLoaiNguyenLieu, String tenNguyenLieu, float soLuongTon, String donVi, Date ngayHetHan, String moTa) {
        this.maNguyenLieu = maNguyenLieu;
        this.maLoaiNguyenLieu = maLoaiNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.soLuongTon = soLuongTon;
        this.donVi = donVi;
        this.ngayHetHan = ngayHetHan;
        this.moTa = moTa;
    }

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public String getMaLoaiNguyenLieu() {
        return maLoaiNguyenLieu;
    }

    public void setMaLoaiNguyenLieu(String maLoaiNguyenLieu) {
        this.maLoaiNguyenLieu = maLoaiNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public float getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(float soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}