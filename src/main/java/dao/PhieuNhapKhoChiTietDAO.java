package dao;

import entity.PhieuNhapKhoChiTiet;
import java.util.List;

public interface PhieuNhapKhoChiTietDAO {

    void insert(PhieuNhapKhoChiTiet ct);

    List<PhieuNhapKhoChiTiet> findAll();

    List<PhieuNhapKhoChiTiet> findByPhieuNhapKho(int maPhieuNhapKho);

    String getTenNguyenLieu(int maNguyenLieu);
}