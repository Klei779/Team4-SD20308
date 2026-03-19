package entity;

import java.math.BigDecimal;
import java.util.Date;

public class PhieuNhapKho {

    private int maPhieuNhapKho;
    private Date ngayNhap;
    private int tongTien;
    private String maNguoiDung;
    private String moTa;
    private int maNhaCungCap;
    private String trangThai;

    public PhieuNhapKho() {
    }

    public PhieuNhapKho(int maPhieuNhapKho, Date ngayNhap, int tongTien,
                        String maNguoiDung, String moTa, int maNhaCungCap, String trangThai) {
        this.maPhieuNhapKho = maPhieuNhapKho;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
        this.maNguoiDung = maNguoiDung;
        this.moTa = moTa;
        this.maNhaCungCap = maNhaCungCap;
        this.trangThai = trangThai;
    }

    public int getMaPhieuNhapKho() {
        return maPhieuNhapKho;
    }

    public void setMaPhieuNhapKho(int maPhieuNhapKho) {
        this.maPhieuNhapKho = maPhieuNhapKho;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int  getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(int maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}