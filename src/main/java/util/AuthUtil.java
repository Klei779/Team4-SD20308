package util;

import entity.NguoiDung;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {
    private static final String SESSION_USER = "user";

    public static void setUser(HttpServletRequest request, NguoiDung user) {
        request.getSession().setAttribute(SESSION_USER, user);
    }

    // Lấy object NguoiDung từ session
    public static NguoiDung getUser(HttpServletRequest request) {
        return (NguoiDung) request.getSession().getAttribute(SESSION_USER);
    }

    // Kiểm tra xem đã đăng nhập chưa
    public static boolean isAuthenticated(HttpServletRequest request) {
        return getUser(request) != null;
    }

    // Kiểm tra quyền ADMIN (Quản lý) dựa trên cột vaiTro trong CSDL
    public static boolean isManager(HttpServletRequest request) {
        NguoiDung user = getUser(request);
        // Khớp với giá trị "ADMIN" bạn đã INSERT vào CSDL
        return user != null && "ADMIN".equalsIgnoreCase(user.getVaiTro());
    }

    // Đăng xuất
    public static void clear(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}