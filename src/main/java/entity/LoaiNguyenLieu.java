package entity;

public class LoaiNguyenLieu {

    private int maLoaiNguyenLieu;
    private String tenLoaiNguyenLieu;

    public LoaiNguyenLieu() {
    }

    // constructor đầy đủ (dùng khi SELECT)
    public LoaiNguyenLieu(int maLoaiNguyenLieu, String tenLoaiNguyenLieu) {
        this.maLoaiNguyenLieu = maLoaiNguyenLieu;
        this.tenLoaiNguyenLieu = tenLoaiNguyenLieu;
    }

    // constructor dùng khi INSERT
    public LoaiNguyenLieu(String tenLoaiNguyenLieu) {
        this.tenLoaiNguyenLieu = tenLoaiNguyenLieu;
    }

    public int getMaLoaiNguyenLieu() {
        return maLoaiNguyenLieu;
    }

    public void setMaLoaiNguyenLieu(int maLoaiNguyenLieu) {
        this.maLoaiNguyenLieu = maLoaiNguyenLieu;
    }

    public String getTenLoaiNguyenLieu() {
        return tenLoaiNguyenLieu;
    }

    public void setTenLoaiNguyenLieu(String tenLoaiNguyenLieu) {
        this.tenLoaiNguyenLieu = tenLoaiNguyenLieu;
    }
}