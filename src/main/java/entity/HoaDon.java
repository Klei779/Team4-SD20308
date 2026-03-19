package entity;

import java.math.BigDecimal;
import java.util.Date;

public class HoaDon {

    private int maHoaDon;
    private int maNhanVien;
    private Date ngayTao;
    private String trangThai;
    private int tongTien;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, int maNhanVien, Date ngayTao, String trangThai, int tongTien) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}