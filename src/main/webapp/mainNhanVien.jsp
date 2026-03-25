<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Poly Coffee - Hệ Thống Quản Lý</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>
        /* =========================================================
           1. LAYOUT CHÍNH (SIDEBAR & MAIN)
           ========================================================= */
        html, body {
            height: 100%;
            margin: 0;
            overflow: hidden;
            background-color: #0d0d0d;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .wrapper {
            display: flex;
            height: 100vh;
            width: 100vw;
        }

        .sidebar {
            background-color: #161616;
            width: 260px;
            flex-shrink: 0;
            border-right: 1px solid rgba(255, 255, 255, 0.05);
            display: flex;
            flex-direction: column;
            padding: 20px 15px;
        }

        .main-content {
            flex-grow: 1;
            height: 100%;
            background-color: #0d0d0d;
        }

        iframe {
            width: 100%;
            height: 100%;
            border: none;
        }

        /* =========================================================
           2. SIDEBAR COMPONENTS (NAV LINKS)
           ========================================================= */
        .user-box {
            background-color: rgba(255, 255, 255, 0.03);
            border-radius: 15px;
            padding: 15px;
            border: 1px solid rgba(255, 255, 255, 0.05);
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }

        .nav-link {
            color: rgba(255, 255, 255, 0.5);
            padding: 12px 15px;
            margin-bottom: 5px;
            border-radius: 10px;
            text-decoration: none;
            display: flex;
            align-items: center;
            transition: all 0.3s ease;
            cursor: pointer;
            font-size: 14px;
        }

        .nav-link:hover:not(.active) {
            background-color: rgba(255, 255, 255, 0.05);
            color: #fff;
        }

        .nav-link.active {
            background-color: rgba(255, 193, 7, 0.1) !important;
            color: #ffc107 !important;
            font-weight: 600;
        }

        .menu-label {
            font-size: 11px;
            color: rgba(255, 255, 255, 0.25);
            margin: 20px 0 10px 10px;
            text-transform: uppercase;
            letter-spacing: 1px;
            font-weight: 700;
        }

        /* =========================================================
           3. MODAL HỒ SƠ CÁ NHÂN (THEO ẢNH MẪU)
           ========================================================= */
        .modal-content {
            background-color: #1a1a1a;
            border: 1px solid #333;
            color: white;
            border-radius: 25px !important; /* Bo tròn khung lớn */
            overflow: hidden;
            box-shadow: 0 15px 50px rgba(0, 0, 0, 0.5);
        }

        /* Tabs vuông góc - phẳng */
        .google-tab-wrapper .nav-pills {
            background-color: rgba(0, 0, 0, 0.2);
            border-radius: 0;
        }

        .google-tab-wrapper .nav-link {
            border-radius: 0 !important; /* Triệt tiêu bo tròn cho tab */
            padding: 15px;
            margin: 0;
            color: rgba(255, 255, 255, 0.4) !important;
            font-weight: 600;
            text-transform: none;
        }

        .google-tab-wrapper .nav-link.active {
            background-color: #ffc107 !important; /* Màu vàng từ ảnh */
            color: #000 !important;
        }

        /* Avatar bo tròn góc */
        .avatar-preview {
            width: 80px;
            height: 80px;
            background-color: #111;
            border: 1px solid #333;
            border-radius: 20px; /* Bo tròn avatar */
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 15px;
        }

        /* Input & Form */
        .form-control {
            background-color: #0d0d0d !important;
            border: 1px solid #333 !important;
            color: white !important;
            border-radius: 10px !important;
            padding: 12px 15px;
            font-size: 14px;
        }

        .form-control:focus {
            border-color: #ffc107 !important;
            box-shadow: none;
        }

        /* Nút bấm bo tròn */
        .btn-warning {
            background-color: #ffc107;
            border: none;
            color: #000;
            font-weight: 700;
            border-radius: 12px !important;
            padding: 12px;
            transition: 0.3s;
        }

        .btn-warning:hover {
            background-color: #e5ac00;
            transform: translateY(-1px);
        }

        .logout-link {
            color: #ff5e5e !important;
            font-weight: 600;
        }
    </style>
</head>

<body>

<div class="wrapper">
    <div class="sidebar">
        <div class="text-center mb-4 pt-2">
            <h4 class="fw-bold text-white mb-0">POLY <span class="text-warning">COFFEE</span></h4>
        </div>

        <div class="user-box">
            <div class="bg-warning rounded-circle d-flex align-items-center justify-content-center flex-shrink-0"
                 style="width: 42px; height: 42px;">
                <i class="fas fa-user-tie text-dark"></i>
            </div>
            <div class="ms-3 overflow-hidden">
                <div class="fw-bold text-white text-truncate" style="font-size: 14px;">
                    <c:out value="${sessionScope.user.tenNguoiDung}" default="User"/>
                </div>
                <div class="text-secondary" style="font-size: 11px;">
                    <i class="fas fa-circle text-success me-1" style="font-size: 8px;"></i> Đang hoạt động
                </div>
            </div>
        </div>

        <div class="menu-container" style="flex-grow: 1; overflow-y: auto;">
            <div class="nav flex-column">
                <span class="menu-label">Hệ thống</span>
                <a href="taohoadon" target="mainFrame" class="nav-link active">
                    <i class="fas fa-user-friends me-3"></i> Tạo hóa đơn
                </a>
                <a href="hoadoncuatoi" target="mainFrame" class="nav-link">
                    <i class="fas fa-box-open me-3"></i> Hóa đơn của tôi
                </a>

                <span class="menu-label">Tài khoản</span>
                <a class="nav-link" data-bs-toggle="modal" data-bs-target="#modalHoSo">
                    <i class="fas fa-user-circle me-3"></i> Hồ sơ cá nhân
                </a>
            </div>
        </div>

        <div class="logout-section mt-auto">
            <hr class="text-secondary opacity-10 my-3">
            <a href="${ctx}/PolyCoffe/home" class="nav-link logout-link">
                <i class="fas fa-power-off"></i> Đăng xuất hệ thống
            </a>
        </div>
    </div>

    <div class="main-content">
        <iframe name="mainFrame" id="mainFrame" src="taohoadon"></iframe>
    </div>
</div>

<div class="modal fade" id="modalHoSo" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header border-0 px-4 pt-4 pb-0">
                <h6 class="modal-title fw-bold text-warning">HỒ SƠ CÁ NHÂN</h6>
                <button type="button" class="btn-close btn-close-white shadow-none" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body p-0">
                <div class="google-tab-wrapper mt-3">
                    <ul class="nav nav-pills nav-justified" id="pills-tab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="pills-info-tab" data-bs-toggle="pill"
                                    data-bs-target="#tab-info" type="button" role="tab" aria-selected="true">
                                Thông tin chung
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="pills-password-tab" data-bs-toggle="pill"
                                    data-bs-target="#tab-password" type="button" role="tab" aria-selected="false">
                                Đổi mật khẩu
                            </button>
                        </li>
                    </ul>
                </div>

                <div class="tab-content p-4" id="pills-tabContent">

                    <div class="tab-pane fade show active" id="tab-info" role="tabpanel" aria-labelledby="pills-info-tab">
                        <div class="avatar-preview">
                            <i class="fas fa-user-shield fa-2x text-warning"></i>
                        </div>
                        <div class="mb-3">
                            <label class="small text-secondary mb-2">Tên người dùng</label>
                            <input type="text" class="form-control" value="${sessionScope.user.tenNguoiDung}" readonly>
                        </div>
                        <div class="mb-3">
                            <label class="small text-secondary mb-2">Email đăng nhập</label>
                            <input type="email" class="form-control" value="${sessionScope.user.email}" readonly>
                        </div>
                        <div class="mb-3">
                            <label class="small text-secondary mb-2">Vai trò hệ thống</label>
                            <input type="text" class="form-control text-warning fw-bold"
                                   value="${sessionScope.user.vaiTro}" readonly>
                        </div>
                    </div>

                    <div class="tab-pane fade" id="tab-password" role="tabpanel" aria-labelledby="pills-password-tab">
                        <form action="${pageContext.request.contextPath}/DoiMatKhauServlet" method="POST">
                            <div class="mb-3">
                                <label class="small text-secondary mb-2">Mật khẩu hiện tại</label>
                                <input type="password" name="oldPass" class="form-control" placeholder="••••••••" required>
                            </div>
                            <div class="mb-3">
                                <label class="small text-secondary mb-2">Mật khẩu mới</label>
                                <input type="password" name="newPass" class="form-control"
                                       placeholder="Tối thiểu 6 ký tự" required>
                            </div>
                            <div class="mb-4 text-end">
                                <a href="#" class="text-warning small text-decoration-none">Quên mật khẩu?</a>
                            </div>
                            <button type="submit" class="btn btn-warning w-100 shadow-none">CẬP NHẬT MẬT KHẨU</button>
                        </form>
                    </div>
                </div>
            </div>

            <div class="modal-footer border-0 px-4 pb-4 pt-0">
                <button type="button" class="btn btn-sm text-secondary border-0 me-auto" data-bs-dismiss="modal">Hủy bỏ</button>
                <button type="button" class="btn btn-warning px-4">Lưu thông tin</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Logic đổi trạng thái Active cho Sidebar
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.addEventListener('click', function () {
            if (this.getAttribute('target') === 'mainFrame') {
                navLinks.forEach(item => item.classList.remove('active'));
                this.classList.add('active');
            }
        });
    });
</script>
</body>
</html>