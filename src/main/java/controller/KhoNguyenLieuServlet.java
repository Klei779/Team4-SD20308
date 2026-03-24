package controller;

import dao.CongThucCTDAOImpl;
import dao.CongThucDAOImpl;
import dao.LoaiNguyenLieuDAOImpl;
import dao.NguyenLieuDAOImpl;
import entity.CongThuc;
import entity.CongThucChiTiet;
import entity.LoaiNguyenLieu;
import entity.NguyenLieu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/khonguyenlieu")
public class KhoNguyenLieuServlet extends HttpServlet {

    private NguyenLieuDAOImpl nguyenLieuDAO = new NguyenLieuDAOImpl();
    private CongThucCTDAOImpl congThucCTDAO = new CongThucCTDAOImpl();
    private LoaiNguyenLieuDAOImpl loaiDAO = new LoaiNguyenLieuDAOImpl();
    private CongThucDAOImpl congThucDAO = new CongThucDAOImpl();

    private int parseIntSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        // --- XỬ LÝ AJAX: TRẢ VỀ HTML CHO MODAL CHI TIẾT CÔNG THỨC ---
        if ("getListCTCT".equals(action)) {
            int maCT = parseIntSafe(request.getParameter("maCongThuc"));

            // Lấy dữ liệu cần thiết để so khớp trong subListCongThuc.jsp
            List<CongThucChiTiet> listSub = congThucCTDAO.findByCongThuc(maCT);
            List<NguyenLieu> listNL = nguyenLieuDAO.findAll();
            List<LoaiNguyenLieu> listLoai = loaiDAO.findall();

            request.setAttribute("listSub", listSub);
            request.setAttribute("listNL", listNL);
            request.setAttribute("listLoai", listLoai);

            // Chuyển hướng tới file JSP phụ (Fragment)
            request.getRequestDispatcher("subListCongThuc.jsp").forward(request, response);
            return;
        }

        // --- HIỂN THỊ DỮ LIỆU TRANG CHÍNH ---
        List<NguyenLieu> listNL = nguyenLieuDAO.findAll();
        List<CongThuc> listCT = congThucDAO.findActive(); // Chỉ lấy món đang kinh doanh
        List<LoaiNguyenLieu> listLoai = loaiDAO.findall();

        request.setAttribute("list", listNL);
        request.setAttribute("listLoai", listLoai);
        request.setAttribute("listCongThuc", listCT);

        request.getRequestDispatcher("khonguyenlieu.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if (action == null) {
            response.sendRedirect("khonguyenlieu");
            return;
        }

        try {
            switch (action) {
                // --- QUẢN LÝ KHO ---
                case "add":
                    NguyenLieu nl = new NguyenLieu();
                    nl.setTenNguyenLieu(request.getParameter("ten"));
                    nl.setSoLuongTon(parseIntSafe(request.getParameter("soLuong")));
                    nl.setDonVi(request.getParameter("donVi"));
                    nl.setSoLuongToiThieu(parseIntSafe(request.getParameter("toiThieu")));
                    nl.setMaLoaiNguyenLieu(parseIntSafe(request.getParameter("maLoai")));
                    nguyenLieuDAO.insert(nl);
                    session.setAttribute("message", "Đã thêm nguyên liệu mới!");
                    break;

                case "nhap":
                    int idNl = parseIntSafe(request.getParameter("id"));
                    int slNhap = parseIntSafe(request.getParameter("soLuongNhap"));
                    nguyenLieuDAO.updateSoLuong(idNl, slNhap);
                    session.setAttribute("message", "Đã cập nhật số lượng tồn kho!");
                    break;

                // --- QUẢN LÝ DANH MỤC CÔNG THỨC ---
                case "addCT":
                    String tenCT = request.getParameter("tenCongThuc");
                    CongThuc ct = new CongThuc();
                    ct.setTenCongThuc(tenCT);
                    ct.setTrangThai(true);
                    congThucDAO.insert(ct);
                    session.setAttribute("message", "Đã tạo món: " + tenCT);
                    break;

                case "updateName":
                    int maCTUpdate = parseIntSafe(request.getParameter("maCongThuc"));
                    String tenMoi = request.getParameter("tenMoi");
                    CongThuc ctEdit = new CongThuc();
                    ctEdit.setMaCongThuc(maCTUpdate);
                    ctEdit.setTenCongThuc(tenMoi);
                    congThucDAO.update(ctEdit);
                    session.setAttribute("message", "Đã đổi tên món!");
                    break;

                case "softDelete":
                    congThucDAO.softDelete(parseIntSafe(request.getParameter("maCongThuc")));
                    session.setAttribute("message", "Đã ẩn món khỏi danh sách!");
                    break;

                case "resetCT":
                    congThucDAO.resetData(parseIntSafe(request.getParameter("maCongThuc")));
                    session.setAttribute("message", "Đã xóa sạch định lượng món!");
                    break;

                // --- CHI TIẾT ĐỊNH LƯỢNG (XỬ LÝ QUA FETCH/AJAX) ---
                case "addCTCT":
                    try {
                        CongThucChiTiet itemAdd = new CongThucChiTiet();
                        itemAdd.setMaCongThuc(parseIntSafe(request.getParameter("maCongThuc")));
                        itemAdd.setMaNguyenLieu(parseIntSafe(request.getParameter("maNguyenLieu")));
                        itemAdd.setDinhLuong(parseIntSafe(request.getParameter("dinhLuong")));

                        congThucCTDAO.insert(itemAdd);

                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().write("Success"); // Trả về text để tránh lỗi fetch
                    } catch (Exception e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                    return; // Kết thúc xử lý POST cho Ajax

                case "deleteCTCT":
                    congThucCTDAO.delete(parseIntSafe(request.getParameter("idCTCT")));
                    response.setStatus(200);
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
        }

        response.sendRedirect("khonguyenlieu");
    }
}