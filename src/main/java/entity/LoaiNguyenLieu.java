package entity;

public class LoaiNguyenLieu {

    private String maLoaiNguyenLieu;
    private String tenLoaiNguyenLieu;

    public LoaiNguyenLieu() {
    }

    public LoaiNguyenLieu(String maLoaiNguyenLieu, String tenLoaiNguyenLieu) {
        this.maLoaiNguyenLieu = maLoaiNguyenLieu;
        this.tenLoaiNguyenLieu = tenLoaiNguyenLieu;
    }

    public String getMaLoaiNguyenLieu() {
        return maLoaiNguyenLieu;
    }

    public void setMaLoaiNguyenLieu(String maLoaiNguyenLieu) {
        this.maLoaiNguyenLieu = maLoaiNguyenLieu;
    }

    public String getTenLoaiNguyenLieu() {
        return tenLoaiNguyenLieu;
    }

    public void setTenLoaiNguyenLieu(String tenLoaiNguyenLieu) {
        this.tenLoaiNguyenLieu = tenLoaiNguyenLieu;
    }
}
