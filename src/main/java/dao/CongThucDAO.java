package dao;

import entity.CongThuc;
import java.util.List;

public interface CongThucDAO {
    void insert(CongThuc ct);
    void update(CongThuc ct);           // Sửa tên
    void softDelete(int maCongThuc);    // Ẩn kinh doanh (Xóa mềm)
    void resetData(int maCongThuc);     // Xóa sạch lịch sử (Xóa vĩnh viễn)
    List<CongThuc> findall();           // Lấy tất cả (bao gồm cả ẩn)
    List<CongThuc> findActive();        // Chỉ lấy những cái đang hiện
    List<CongThuc> findByCongThuc(int maCongThuc);
}