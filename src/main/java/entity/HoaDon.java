package entity;

import java.sql.Timestamp;

public class HoaDon {

    private int maHoaDon;
    private int maNguoiDung;
    private Timestamp ngayTao;
    private boolean trangThai;
    private int tongTien;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, int maNguoiDung, Timestamp ngayTao, boolean trangThai, int tongTien) {
        this.maHoaDon = maHoaDon;
        this.maNguoiDung = maNguoiDung;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    // constructor insert
    public HoaDon(int maNguoiDung, boolean trangThai, int tongTien) {
        this.maNguoiDung = maNguoiDung;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public Timestamp getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}