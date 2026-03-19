package entity;

public class ThongKeNhanVienDTO {

    private String tenNhanVien;
    private int doanhThu;

    public ThongKeNhanVienDTO() {
    }

    public ThongKeNhanVienDTO(String tenNhanVien, int doanhThu) {
        this.tenNhanVien = tenNhanVien;
        this.doanhThu = doanhThu;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public int getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(int doanhThu) {
        this.doanhThu = doanhThu;
    }
}