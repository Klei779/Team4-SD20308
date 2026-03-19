package dao;

import entity.PhieuNhapKho;
import java.util.Date;
import java.util.List;

public interface PhieuNhapKhoDAO {

    void insert(PhieuNhapKho pnk);

    List<PhieuNhapKho> findAll();

    List<PhieuNhapKho> findByDate(Date from, Date to);

    List<PhieuNhapKho> findByNguoiDung(int maNguoiDung);

    List<PhieuNhapKho> findByNCC(int maNCC);
}