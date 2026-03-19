package entity;

public class CongThuc {

    private String maCongThuc;
    private String tenCongThuc;

    public CongThuc() {
    }

    public CongThuc(String maCongThuc, String tenCongThuc) {
        this.maCongThuc = maCongThuc;
        this.tenCongThuc = tenCongThuc;
    }

    public String getMaCongThuc() {
        return maCongThuc;
    }

    public void setMaCongThuc(String maCongThuc) {
        this.maCongThuc = maCongThuc;
    }

    public String getTenCongThuc() {
        return tenCongThuc;
    }

    public void setTenCongThuc(String tenCongThuc) {
        this.tenCongThuc = tenCongThuc;
    }
}