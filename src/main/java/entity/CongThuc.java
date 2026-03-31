package entity;

import java.util.List;

public class CongThuc {
    private int maCongThuc;
    private String tenCongThuc;
    private boolean trangThai; // Thêm mới
    private List<CongThucChiTiet> chiTietList;

    public CongThuc() {
    }

    // Constructor đầy đủ cho việc lấy dữ liệu từ DB
    public CongThuc(int maCongThuc, String tenCongThuc, boolean trangThai) {
        this.maCongThuc = maCongThuc;
        this.tenCongThuc = tenCongThuc;
        this.trangThai = trangThai;
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

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}