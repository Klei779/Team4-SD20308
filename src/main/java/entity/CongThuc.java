package entity;

import java.util.List;

public class CongThuc {

    private int maCongThuc;
    private String tenCongThuc;

    private List<CongThucChiTiet> chiTietList;

    public CongThuc() {
    }

    public CongThuc(int maCongThuc, String tenCongThuc) {
        this.maCongThuc = maCongThuc;
        this.tenCongThuc = tenCongThuc;
    }

    public CongThuc(String tenCongThuc) {
        this.tenCongThuc = tenCongThuc;
    }

    public int getMaCongThuc() {
        return maCongThuc;
    }

    public void setMaCongThuc(int maCongThuc) {
        this.maCongThuc = maCongThuc;
    }

    public String getTenCongThuc() {
        return tenCongThuc;
    }

    public void setTenCongThuc(String tenCongThuc) {
        this.tenCongThuc = tenCongThuc;
    }
}