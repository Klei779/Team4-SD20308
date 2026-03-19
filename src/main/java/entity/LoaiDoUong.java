package entity;

public class LoaiDoUong {

    private int maLoai;
    private String tenLoai;

    public LoaiDoUong() {
    }

    public LoaiDoUong(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public LoaiDoUong(String tenLoai) {
        setTenLoai(tenLoai);
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}