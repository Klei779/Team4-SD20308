package dao;

import entity.NhaCungCap;

import java.util.List;

public interface NhaCungCapDAO {
    void insert(NhaCungCap ncc);
    void update(NhaCungCap ncc);
    void delete(NhaCungCap ncc);
    List<NhaCungCap> findAll();
    List<NhaCungCap> findByName(String tenNhaCungCap);

}
