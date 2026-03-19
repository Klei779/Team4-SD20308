package entity;

public class NguyenLieu {

    private int maNguyenLieu;
    private int maLoaiNguyenLieu;
    private String tenNguyenLieu;
    private int soLuongTon;
    private String donVi;
    private int soLuongToiThieu;
    private String ghiChu;

    public NguyenLieu() {
    }

    // constructor SELECT
    public NguyenLieu(int maNguyenLieu, int maLoaiNguyenLieu, String tenNguyenLieu,
                      int soLuongTon, String donVi,
                      int soLuongToiThieu, String ghiChu) {

        this.maNguyenLieu = maNguyenLieu;
        this.maLoaiNguyenLieu = maLoaiNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.soLuongTon = soLuongTon;
        this.donVi = donVi;
        this.soLuongToiThieu = soLuongToiThieu;
        this.ghiChu = ghiChu;
    }

    // constructor INSERT
    public NguyenLieu(int maLoaiNguyenLieu, String tenNguyenLieu,
                      int soLuongTon, String donVi,
                      int soLuongToiThieu, String ghiChu) {

        this.maLoaiNguyenLieu = maLoaiNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.soLuongTon = soLuongTon;
        this.donVi = donVi;
        this.soLuongToiThieu = soLuongToiThieu;
        this.ghiChu = ghiChu;
    }

    public int getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(int maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public int getMaLoaiNguyenLieu() {
        return maLoaiNguyenLieu;
    }

    public void setMaLoaiNguyenLieu(int maLoaiNguyenLieu) {
        this.maLoaiNguyenLieu = maLoaiNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public int getSoLuongToiThieu() {
        return soLuongToiThieu;
    }

    public void setSoLuongToiThieu(int soLuongToiThieu) {
        this.soLuongToiThieu = soLuongToiThieu;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}