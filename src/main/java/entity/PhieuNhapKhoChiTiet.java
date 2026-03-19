package entity;

import java.util.Date;

public class PhieuNhapKhoChiTiet {

    private String maPhieuNhapKhoCT;
    private String maPhieuNhapKho;
    private String maNguyenLieu;
    private Float soLuong;
    private Date ngayHetHan;

    public PhieuNhapKhoChiTiet() {
    }

    public PhieuNhapKhoChiTiet(String maPhieuNhapKhoCT, String maPhieuNhapKho,
                               String maNguyenLieu, Float soLuong, Date ngayHetHan) {
        this.maPhieuNhapKhoCT = maPhieuNhapKhoCT;
        this.maPhieuNhapKho = maPhieuNhapKho;
        this.maNguyenLieu = maNguyenLieu;
        this.soLuong = soLuong;
        this.ngayHetHan = ngayHetHan;
    }

    public String getMaPhieuNhapKhoCT() {
        return maPhieuNhapKhoCT;
    }

    public void setMaPhieuNhapKhoCT(String maPhieuNhapKhoCT) {
        this.maPhieuNhapKhoCT = maPhieuNhapKhoCT;
    }

    public String getMaPhieuNhapKho() {
        return maPhieuNhapKho;
    }

    public void setMaPhieuNhapKho(String maPhieuNhapKho) {
        this.maPhieuNhapKho = maPhieuNhapKho;
    }

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public Float getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Float soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }
}