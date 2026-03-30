<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Poly Coffee - Đăng nhập</title>
    <!-- Bootstrap 5 & FontAwesome -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>
        body {
            background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)),
            url('https://images.unsplash.com/photo-1509042239860-f550ce710b93?q=80&w=1887&auto=format&fit=crop') no-repeat center center fixed;
            background-size: cover;
            height: 100vh;
            display: flex; align-items: center; justify-content: center;
            margin: 0; font-family: 'Segoe UI', sans-serif;
        }

        .login-card {
            background: rgba(25, 25, 25, 0.9);
            backdrop-filter: blur(15px);
            padding: 40px;
            border-radius: 25px;
            border: 1px solid rgba(255, 193, 7, 0.2);
            width: 400px;
            box-shadow: 0 25px 50px rgba(0,0,0,0.6);
        }

        .brand-logo {
            font-size: 2.2rem;
            font-weight: 900;
            color: #ffc107;
            text-align: center;
            margin-bottom: 5px;
        }

        .form-label { color: #888; font-size: 0.9rem; margin-bottom: 5px; display: block; }

        .form-control {
            background: rgba(255, 255, 255, 0.05) !important;
            border: 1px solid #444 !important;
            color: white !important;
            padding: 12px;
            border-radius: 10px;
        }

        .form-control:focus {
            border-color: #ffc107 !important;
            box-shadow: none !important;
        }

        .btn-warning {
            background: #ffc107;
            border: none;
            padding: 12px;
            font-weight: 800;
            border-radius: 12px;
            margin-top: 10px;
            transition: 0.3s;
        }

        .btn-warning:hover {
            background: #e5ad06;
            transform: translateY(-2px);
        }

        /* Vùng hiển thị lỗi */
        .error-alert {
            background: rgba(255, 82, 82, 0.15);
            border: 1px solid #ff5252;
            color: #ff8a8a;
            padding: 12px;
            border-radius: 10px;
            font-size: 0.85rem;
            margin-bottom: 20px;
            text-align: center;
        }

        /* Vùng hiển thị thông báo thành công */
        .success-alert {
            background: rgba(40, 167, 69, 0.15);
            border: 1px solid #28a745;
            color: #75ce8a;
            padding: 12px;
            border-radius: 10px;
            font-size: 0.85rem;
            margin-bottom: 20px;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="login-card">
    <div class="brand-logo">
        <i class="fas fa-mug-hot"></i> POLY COFFEE
    </div>
    <p class="text-center text-secondary small mb-4">Hệ thống quản lý thức uống</p>

    <!-- HIỂN THỊ THÔNG BÁO LỖI -->
    <c:if test="${not empty error}">
        <div class="error-alert">
            <i class="fas fa-exclamation-circle me-2"></i> ${error}
        </div>
    </c:if>

    <!-- HIỂN THỊ THÔNG BÁO THÀNH CÔNG (Gửi mail thành công) -->
    <c:if test="${not empty message}">
        <div class="success-alert">
            <i class="fas fa-check-circle me-2"></i> ${message}
        </div>
    </c:if>

    <!-- FORM ĐĂNG NHẬP -->
    <form action="${ctx}/login" method="post">
        <div class="mb-3">
            <label class="form-label">Tên đăng nhập</label>
            <input type="text" name="username" class="form-control"
                   placeholder="Nhập tài khoản"
                   value="${not empty cookieUser ? cookieUser : oldUser}" required>
        </div>

        <div class="mb-4">
            <label class="form-label">Mật khẩu</label>
            <input type="password" name="password" class="form-control"
                   placeholder="••••••••"
                   value="${cookiePass}" required>
        </div>

        <div class="d-flex justify-content-between align-items-center mb-4">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="remember" id="remember"
                ${not empty cookieUser ? 'checked' : ''}>
                <label class="form-check-label text-secondary small" for="remember">
                    Ghi nhớ tài khoản
                </label>
            </div>
            <a href="#" class="text-warning small text-decoration-none" data-bs-toggle="modal" data-bs-target="#forgotPasswordModal">
                Quên mật khẩu?
            </a>
        </div>

        <button type="submit" class="btn btn-warning w-100">
            ĐĂNG NHẬP
        </button>
    </form>

    <div class="text-center mt-4">
        <a href="${ctx}/home" class="text-secondary small text-decoration-none">
            <i class="fas fa-chevron-left me-1"></i> Quay lại trang chủ
        </a>
    </div>
</div>

<!-- MODAL QUÊN MẬT KHẨU -->
<div class="modal fade" id="forgotPasswordModal" tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content bg-dark text-white border-warning" style="border-radius: 20px;">
            <form action="${ctx}/forgot-password" method="post">
                <div class="modal-header border-secondary">
                    <h5 class="modal-title" id="modalTitle">Khôi phục mật khẩu</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p class="small text-secondary mb-3">
                        Vui lòng nhập Email đã đăng ký. Hệ thống sẽ tạo mật khẩu mới và gửi trực tiếp vào hộp thư của bạn.
                    </p>
                    <div class="mb-3">
                        <label class="form-label">Email đăng ký</label>
                        <input type="email" name="email" class="form-control"
                               placeholder="example@gmail.com" required>
                    </div>
                </div>
                <div class="modal-footer border-secondary">
                    <button type="button" class="btn btn-outline-secondary btn-sm" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-warning btn-sm px-4">Xác nhận gửi</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>