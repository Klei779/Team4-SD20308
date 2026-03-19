package entity;

public class CongThucChiTiet {

    private String maCTCT;
    private String maCongThuc;
    private String maNguyenLieu;
    private Float dinhLuong;

    public CongThucChiTiet() {
    }

    public CongThucChiTiet(String maCTCT, String maCongThuc, String maNguyenLieu, Float dinhLuong) {
        this.maCTCT = maCTCT;
        this.maCongThuc = maCongThuc;
        this.maNguyenLieu = maNguyenLieu;
        this.dinhLuong = dinhLuong;
    }

    public String getMaCTCT() {
        return maCTCT;
    }

    public void setMaCTCT(String maCTCT) {
        this.maCTCT = maCTCT;
    }

    public String getMaCongThuc() {
        return maCongThuc;
    }

    public void setMaCongThuc(String maCongThuc) {
        this.maCongThuc = maCongThuc;
    }

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public Float getDinhLuong() {
        return dinhLuong;
    }

    public void setDinhLuong(Float dinhLuong) {
        this.dinhLuong = dinhLuong;
    }
}