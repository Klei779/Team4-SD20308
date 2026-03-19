package entity;

public class NguoiDung {

    private int maNguoiDung;
    private String tenNguoiDung;
    private String email;
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;
    private boolean trangThai;
    private String hinhAnh;

    public NguoiDung() {
    }

    // constructor đầy đủ (SELECT)
    public NguoiDung(int maNguoiDung, String tenNguoiDung, String email,
                     String tenDangNhap, String matKhau,
                     String vaiTro, boolean trangThai, String hinhAnh) {

        this.maNguoiDung = maNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.email = email;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
    }

    // constructor INSERT
    public NguoiDung(String tenNguoiDung, String email,
                     String tenDangNhap, String matKhau,
                     String vaiTro, boolean trangThai, String hinhAnh) {

        this.tenNguoiDung = tenNguoiDung;
        this.email = email;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
    }

    // getters & setters

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}