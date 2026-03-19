package entity;

public class ThongKeDTO {

    private int doanhThu;
    private int loiNhuan;
    private int soHoaDon;
    private int tongSoLuong;

    public ThongKeDTO() {
    }

    public ThongKeDTO(int doanhThu, int loiNhuan, int soHoaDon, int tongSoLuong) {
        this.doanhThu = doanhThu;
        this.loiNhuan = loiNhuan;
        this.soHoaDon = soHoaDon;
        this.tongSoLuong = tongSoLuong;
    }

    public int getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(int doanhThu) {
        this.doanhThu = doanhThu;
    }

    public int getLoiNhuan() {
        return loiNhuan;
    }

    public void setLoiNhuan(int loiNhuan) {
        this.loiNhuan = loiNhuan;
    }

    public int getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(int soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    public int getTongSoLuong() {
        return tongSoLuong;
    }

    public void setTongSoLuong(int tongSoLuong) {
        this.tongSoLuong = tongSoLuong;
    }
}