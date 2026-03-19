package entity;

import java.math.BigDecimal;
import java.util.Date;

public class PhieuNhapKho {

    private String maPhieuNhapKho;
    private Date ngayNhap;
    private BigDecimal tongTien;
    private String maNguoiDung;
    private String moTa;
    private String maNhaCungCap;
    private String trangThai;

    public PhieuNhapKho() {
    }

    public PhieuNhapKho(String maPhieuNhapKho, Date ngayNhap, BigDecimal tongTien,
                        String maNguoiDung, String moTa, String maNhaCungCap, String trangThai) {
        this.maPhieuNhapKho = maPhieuNhapKho;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
        this.maNguoiDung = maNguoiDung;
        this.moTa = moTa;
        this.maNhaCungCap = maNhaCungCap;
        this.trangThai = trangThai;
    }

    public String getMaPhieuNhapKho() {
        return maPhieuNhapKho;
    }

    public void setMaPhieuNhapKho(String maPhieuNhapKho) {
        this.maPhieuNhapKho = maPhieuNhapKho;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
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

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}