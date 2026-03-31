package controller;

import dao.*;
import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/khonguyenlieu")
public class KhoNguyenLieuServlet extends HttpServlet {

    NguyenLieuDAOImpl nguyenLieuDAO = new NguyenLieuDAOImpl();
    CongThucCTDAOImpl congThucCTDAO = new CongThucCTDAOImpl();
    LoaiNguyenLieuDAOImpl loaiDAO = new LoaiNguyenLieuDAOImpl();

    private static final int MAX_KHO_CAPACITY = 5000;

    private int parseIntSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int getTongTonKho() {
        List<NguyenLieu> list = nguyenLieuDAO.findAll();
        int total = 0;
        for (NguyenLieu nl : list) {
            total += nl.getSoLuongTon();
        }
        return total;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String maLoaiStr = request.getParameter("maLoai");
        List<NguyenLieu> list;

        if (maLoaiStr != null && !maLoaiStr.isEmpty()) {
            int maLoai = Integer.parseInt(maLoaiStr);

            if (maLoai == 0) {
                list = nguyenLieuDAO.findAll(); // Tất cả
            } else {
                list = nguyenLieuDAO.findByLoai(maLoai); // lọc theo loại
            }
        } else {
            list = nguyenLieuDAO.findAll(); // mặc định
        }
        request.setAttribute("selectedLoai", maLoaiStr);
        List<LoaiNguyenLieu> listLoai = loaiDAO.findall();

        int tongTonKho = 0;
        List<NguyenLieu> sapHet = new ArrayList<>();

        for (NguyenLieu nl : list) {
            tongTonKho += nl.getSoLuongTon();
            if (nl.getSoLuongTon() < nl.getSoLuongToiThieu()) {
                sapHet.add(nl);
            }
        }

        request.setAttribute("list", list);
        request.setAttribute("sapHet", sapHet);
        request.setAttribute("listLoai", listLoai);
        request.setAttribute("tongTonKho", tongTonKho); // Quan trọng để JSP hiển thị
        request.setAttribute("MAX_KHO", MAX_KHO_CAPACITY);

        request.getRequestDispatcher("khonguyenlieu.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        int tongHienTai = getTongTonKho();

        // 1. XỬ LÝ THÊM MỚI
        if ("add".equals(action)) {
            int soLuongMoi = parseIntSafe(request.getParameter("soLuong"));

            if (tongHienTai + soLuongMoi > MAX_KHO_CAPACITY) {
                session.setAttribute("error", "Lỗi: Kho không đủ chỗ! (Cần thêm: " + soLuongMoi + ", Hiện có: " + tongHienTai + ")");
            } else {
                NguyenLieu nl = new NguyenLieu();
                nl.setTenNguyenLieu(request.getParameter("ten"));
                nl.setSoLuongTon(soLuongMoi);
                nl.setDonVi(request.getParameter("donVi"));
                nl.setSoLuongToiThieu(parseIntSafe(request.getParameter("toiThieu")));
                nl.setMaLoaiNguyenLieu(parseIntSafe(request.getParameter("maLoai")));
                nl.setGhiChu(request.getParameter("ghiChu"));
                nguyenLieuDAO.insert(nl);
                session.setAttribute("message", "Thêm nguyên liệu thành công!");
            }
        }

        // 2. XỬ LÝ CẬP NHẬT (UPDATE)
        else if ("update".equals(action)) {
            int id = parseIntSafe(request.getParameter("id"));
            int soLuongCapNhat = parseIntSafe(request.getParameter("soLuong"));

            // 1. Lấy dữ liệu hiện tại của món này trong DB TRƯỚC khi update
            NguyenLieu nlCu = nguyenLieuDAO.findById(id);
            int soLuongCu = (nlCu != null) ? nlCu.getSoLuongTon() : 0;

            // 2. Tính toán sự thay đổi (Chênh lệch)
            int chenhLech = soLuongCapNhat - soLuongCu;

            // 3. Kiểm tra điều kiện tràn kho
            if (tongHienTai + chenhLech > MAX_KHO_CAPACITY) {
                session.setAttribute("error", "Không thể cập nhật! Số lượng tăng thêm (" + chenhLech + " đơn vị) sẽ làm tràn kho tổng.");
            } else {
                // Cho phép thực thi vì tổng sau khi sửa vẫn <= 5000 (hoặc nhỏ hơn mức cũ)
                NguyenLieu nl = new NguyenLieu();
                nl.setMaNguyenLieu(id);
                nl.setTenNguyenLieu(request.getParameter("ten"));
                nl.setSoLuongTon(soLuongCapNhat);
                nl.setDonVi(request.getParameter("donVi"));
                nl.setSoLuongToiThieu(parseIntSafe(request.getParameter("toiThieu")));
                nl.setMaLoaiNguyenLieu(parseIntSafe(request.getParameter("maLoai")));
                nl.setGhiChu(request.getParameter("ghiChu"));

                nguyenLieuDAO.update(nl);
                session.setAttribute("message", "Cập nhật thành công! Kho hiện tại: " + (tongHienTai + chenhLech) + "/" + MAX_KHO_CAPACITY);
            }
        }

        // 3. XỬ LÝ NHẬP KHO (NHẬP THÊM)
        else if ("nhap".equals(action)) {
            int id = parseIntSafe(request.getParameter("id"));
            int soLuongNhap = parseIntSafe(request.getParameter("soLuongNhap"));

            if (tongHienTai + soLuongNhap > MAX_KHO_CAPACITY) {
                session.setAttribute("error", "Không thể nhập! Kho sẽ bị tràn (Vượt quá sức chứa " + MAX_KHO_CAPACITY + ")");
            } else {
                nguyenLieuDAO.updateSoLuong(id, soLuongNhap);
                session.setAttribute("message", "Nhập kho " + soLuongNhap + " đơn vị thành công!");
            }
        }

        // 4. XỬ LÝ XÓA
        else if ("delete".equals(action)) {
            int id = parseIntSafe(request.getParameter("id"));
            if (congThucCTDAO.existsByNguyenLieu(id)) {
                session.setAttribute("error", "Không thể xóa nguyên liệu đang có trong công thức món!");
            } else {
                nguyenLieuDAO.delete(id);
                session.setAttribute("message", "Đã xóa nguyên liệu khỏi kho!");
            }
        }

        response.sendRedirect("khonguyenlieu");
    }
}