package dao;

import entity.LoaiDoUong;
import java.util.List;

public interface LoaiDoUongDAO {

    boolean insert(LoaiDoUong ldu) throws Exception;

    boolean update(LoaiDoUong ldu);

    boolean delete(int maLoai);

    List<LoaiDoUong> selectAll();

    List<LoaiDoUong> selectByName(String tenLoai);

    LoaiDoUong selectById(int maLoai);
}