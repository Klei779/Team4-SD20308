package dao;

import entity.NgayDTO;
import entity.ThongKeDTO;
import entity.ThongKeDoUongDTO;
import entity.ThongKeNhanVienDTO;

import java.util.Date;
import java.util.List;

public interface ThongKeDAO {

    ThongKeDTO getThongKe(Date fromDate, Date toDate);

    List<ThongKeDoUongDTO> getTopDoUong(Date fromDate, Date toDate);
    List<ThongKeNhanVienDTO> getDoanhThuNhanVien(Date fromDate, Date toDate);
    List<NgayDTO> getDoanhThuTheoNgay(Date from, Date to);
}
