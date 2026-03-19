package dao;

import entity.LoaiNguyenLieu;

import java.util.List;

public interface LoaiNguyenLieuDAO {

        void insert(LoaiNguyenLieu lnl);
        void update(LoaiNguyenLieu lnl);
        void delete(LoaiNguyenLieu lnl);
        List<LoaiNguyenLieu> findall();
        List<LoaiNguyenLieu> findByLoai(int maLoai);



}
