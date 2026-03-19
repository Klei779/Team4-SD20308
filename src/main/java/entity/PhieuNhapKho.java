package entity;

import java.sql.Timestamp;

public class PhieuNhapKho {

    private int maPhieuNhapKho;
    private int maNguoiDung;
    private int maNCC;
    private Timestamp ngayNhapKho;
    private int tongTien;
    private String ghiChu;

    public PhieuNhapKho() {
    }

    // constructor SELECT
    public PhieuNhapKho(int maPhieuNhapKho, int maNguoiDung, int maNCC,
                        Timestamp ngayNhapKho, int tongTien, String ghiChu) {

        this.maPhieuNhapKho = maPhieuNhapKho;
        this.maNguoiDung = maNguoiDung;
        this.maNCC = maNCC;
        this.ngayNhapKho = ngayNhapKho;
        this.tongTien = tongTien;
        this.ghiChu = ghiChu;
    }

    // constructor INSERT
    public PhieuNhapKho(int maNguoiDung, int maNCC, int tongTien, String ghiChu) {
        this.maNguoiDung = maNguoiDung;
        this.maNCC = maNCC;
        this.tongTien = tongTien;
        this.ghiChu = ghiChu;
    }

    public int getMaPhieuNhapKho() {
        return maPhieuNhapKho;
    }

    public void setMaPhieuNhapKho(int maPhieuNhapKho) {
        this.maPhieuNhapKho = maPhieuNhapKho;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public Timestamp getNgayNhapKho() {
        return ngayNhapKho;
    }

    public void setNgayNhapKho(Timestamp ngayNhapKho) {
        this.ngayNhapKho = ngayNhapKho;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}