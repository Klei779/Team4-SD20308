package entity;

public class HoaDonChiTiet {

    private int maHDCT;
    private int maHoaDon;
    private int maDoUong;
    private int soLuong;
    private int donGia;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int maHoaDon, int maDoUong, int soLuong, int donGia) {
        this.maHoaDon = maHoaDon;
        this.maDoUong = maDoUong;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public HoaDonChiTiet(int maHDCT, int maHoaDon, int maDoUong, int soLuong, int donGia) {
        this.maHDCT = maHDCT;
        this.maHoaDon = maHoaDon;
        this.maDoUong = maDoUong;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public int getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(int maHDCT) {
        this.maHDCT = maHDCT;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaDoUong() {
        return maDoUong;
    }

    public void setMaDoUong(int maDoUong) {
        this.maDoUong = maDoUong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
}