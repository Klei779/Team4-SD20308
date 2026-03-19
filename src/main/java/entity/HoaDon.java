package entity;

import java.math.BigDecimal;
import java.util.Date;

public class HoaDon {

    private String maHoaDon;
    private String maNhanVien;
    private Date ngayTao;
    private String trangThai;
    private BigDecimal tongTien;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String maNhanVien, Date ngayTao, String trangThai, BigDecimal tongTien) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
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

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }
}