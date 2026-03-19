package dao;

import entity.CongThuc;

import java.util.List;

public interface CongThucDAO {
    void insert(CongThuc ct);
    void update(CongThuc ct);
    void delete(CongThuc ct);
    List<CongThuc> findall();
    List<CongThuc> findByCongThuc(int maCongThuc);

}
