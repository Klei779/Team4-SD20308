package entity;

public class CongThucChiTiet {

    private int maCTCT;
    private int maCongThuc;
    private int maNguyenLieu;
    private int dinhLuong;

    public CongThucChiTiet() {
    }

    public CongThucChiTiet(int maCTCT, int maCongThuc, int maNguyenLieu, int dinhLuong) {
        this.maCTCT = maCTCT;
        this.maCongThuc = maCongThuc;
        this.maNguyenLieu = maNguyenLieu;
        this.dinhLuong = dinhLuong;
    }

    public CongThucChiTiet(int maCongThuc, int maNguyenLieu, int dinhLuong) {
        this.maCongThuc = maCongThuc;
        this.maNguyenLieu = maNguyenLieu;
        this.dinhLuong = dinhLuong;
    }

    public int getMaCTCT() {
        return maCTCT;
    }

    public void setMaCTCT(int maCTCT) {
        this.maCTCT = maCTCT;
    }

    public int getMaCongThuc() {
        return maCongThuc;
    }

    public void setMaCongThuc(int maCongThuc) {
        this.maCongThuc = maCongThuc;
    }

    public int getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(int maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public int getDinhLuong() {
        return dinhLuong;
    }

    public void setDinhLuong(int dinhLuong) {
        this.dinhLuong = dinhLuong;
    }
}