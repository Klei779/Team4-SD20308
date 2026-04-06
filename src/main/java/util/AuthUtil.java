package util;

import entity.NguoiDung;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {
    private static final String SESSION_USER = "user";

    public static void setUser(HttpServletRequest request, NguoiDung user) {
        request.getSession().setAttribute(SESSION_USER, user);
    }

    public static NguoiDung getUser(HttpServletRequest request) {
        return (NguoiDung) request.getSession().getAttribute(SESSION_USER);
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        return getUser(request) != null;
    }

    public static boolean isManager(HttpServletRequest request) {
        NguoiDung user = getUser(request);
        return user != null && "ADMIN".equalsIgnoreCase(user.getVaiTro());
    }

    public static void clear(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}