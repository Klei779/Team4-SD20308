package dao;

import entity.PhieuNhapKho;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PhieuNhapKhoDAO {

    // ====== INSERT (TRẢ ID) ======
    int insert(PhieuNhapKho pnk);

    // ====== UPDATE ======
    void updateTongTien(int maPN, int tongTien);

    // ====== FIND ======
    List<PhieuNhapKho> findAll();

    List<PhieuNhapKho> findByDate(Date from, Date to);

    List<PhieuNhapKho> findByNguoiDung(int maNguoiDung);

    List<PhieuNhapKho> findByNCC(int maNCC);

    // ====== LOAD COMBOBOX ======
    List<Map<String, Object>> getAllNCC();

    List<Map<String, Object>> getAllNguyenLieu();

    // ====== HIỂN THỊ ======
    String getTenNhanVien(int maNguoiDung);

    String getTenNCC(int maNCC);

}