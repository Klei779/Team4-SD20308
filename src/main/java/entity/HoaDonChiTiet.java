package entity;

public class HoaDonChiTiet {

    private String maHDCT;
    private String maHoaDon;
    private String maDoUong;
    private int soLuong;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String maHDCT, String maHoaDon, String maDoUong, int soLuong) {
        this.maHDCT = maHDCT;
        this.maHoaDon = maHoaDon;
        this.maDoUong = maDoUong;
        this.soLuong = soLuong;
    }

    public String getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(String maHDCT) {
        this.maHDCT = maHDCT;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaDoUong() {
        return maDoUong;
    }

    public void setMaDoUong(String maDoUong) {
        this.maDoUong = maDoUong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}