package filter;

import util.AuthUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        if (uri.contains("/login") || uri.contains("/assets/") || uri.contains("/home") || uri.endsWith(".css")) {
            chain.doFilter(request, response);
            return;
        }

        if (!AuthUtil.isAuthenticated(req)) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (uri.contains("/quanly")) {
            if (!AuthUtil.isManager(req)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập vùng Quản lý!");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}