package filter;

import util.AuthUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*") // Áp dụng cho toàn bộ ứng dụng
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        // 1. Loại trừ các trang không cần đăng nhập
        if (uri.contains("/login") || uri.contains("/assets/") || uri.endsWith(".css")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Chặn nếu chưa đăng nhập
        if (!AuthUtil.isAuthenticated(req)) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 3. Phân quyền: Chỉ ADMIN mới vào được các trang /manager/*
        // Ví dụ: /QuanLyQuanNuoc/manager/nguyen-lieu
        if (uri.contains("/manager/")) {
            if (!AuthUtil.isManager(req)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập vùng Quản lý!");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}