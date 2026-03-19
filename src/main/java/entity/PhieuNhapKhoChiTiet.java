package entity;

import java.util.Date;

public class PhieuNhapKhoChiTiet {

    private int maPhieuNhapKhoCT;
    private int maPhieuNhapKho;
    private int maNguyenLieu;
    private int soLuong;
    private Date ngayHetHan;

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
}