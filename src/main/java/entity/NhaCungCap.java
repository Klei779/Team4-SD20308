package entity;

public class NhaCungCap {

    private int maNhaCungCap;
    private String tenNhaCungCap;
    private String dienThoai;
    private String diaChi;

    public NhaCungCap() {
    }

    // constructor SELECT
    public NhaCungCap(int maNhaCungCap, String tenNhaCungCap, String dienThoai, String diaChi) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
    }

    // constructor INSERT
    public NhaCungCap(String tenNhaCungCap, String dienThoai, String diaChi) {
        this.tenNhaCungCap = tenNhaCungCap;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
    }

    public int getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(int maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}