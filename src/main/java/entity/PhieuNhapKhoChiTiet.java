package entity;

import java.math.BigDecimal;
import java.sql.Date;

public class PhieuNhapKhoChiTiet {

    private int maPhieuNhapKhoCT;
    private int maPhieuNhapKho;
    private int maNguyenLieu;
    private int soLuong;
    private Date ngayHetHan;
    private int donGiaNhap;

    public PhieuNhapKhoChiTiet() {
    }

    // constructor SELECT
    public PhieuNhapKhoChiTiet(int maPhieuNhapKhoCT, int maPhieuNhapKho,
                               int maNguyenLieu, int soLuong,
                               Date ngayHetHan, int donGiaNhap) {

        this.maPhieuNhapKhoCT = maPhieuNhapKhoCT;
        this.maPhieuNhapKho = maPhieuNhapKho;
        this.maNguyenLieu = maNguyenLieu;
        this.soLuong = soLuong;
        this.ngayHetHan = ngayHetHan;
        this.donGiaNhap = donGiaNhap;
    }

    // constructor INSERT
    public PhieuNhapKhoChiTiet(int maPhieuNhapKho, int maNguyenLieu,
                               int soLuong, Date ngayHetHan,
                               int donGiaNhap) {

        this.maPhieuNhapKho = maPhieuNhapKho;
        this.maNguyenLieu = maNguyenLieu;
        this.soLuong = soLuong;
        this.ngayHetHan = ngayHetHan;
        this.donGiaNhap = donGiaNhap;
    }

    public int getMaPhieuNhapKhoCT() {
        return maPhieuNhapKhoCT;
    }

    public void setMaPhieuNhapKhoCT(int maPhieuNhapKhoCT) {
        this.maPhieuNhapKhoCT = maPhieuNhapKhoCT;
    }

    public int getMaPhieuNhapKho() {
        return maPhieuNhapKho;
    }

    public void setMaPhieuNhapKho(int maPhieuNhapKho) {
        this.maPhieuNhapKho = maPhieuNhapKho;
    }

    public int getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(int maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public int getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(int donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }
}