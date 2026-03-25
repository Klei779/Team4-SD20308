<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Poly Coffee - Nhân Viên</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>
        /* =========================================================
           1. LAYOUT & ANIMATION (ĐỒNG BỘ QUẢN LÝ)
           ========================================================= */
        html, body {
            height: 100%; margin: 0; overflow: hidden;
            background-color: #0d0d0d;
            font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
        }

        @keyframes slideIn {
            from { transform: translateX(-20px); opacity: 0; }
            to { transform: translateX(0); opacity: 1; }
        }

        .wrapper { display: flex; height: 100vh; width: 100vw; }

        .sidebar {
            background-color: #161616;
            width: 260px; flex-shrink: 0;
            border-right: 1px solid rgba(255, 255, 255, 0.05);
            display: flex; flex-direction: column;
            padding: 20px 15px;
            animation: slideIn 0.4s ease-out;
        }

        .main-content {
            flex-grow: 1; height: 100%;
            background-color: #0d0d0d;
            position: relative;
        }

        iframe { width: 100%; height: 100%; border: none; }

        /* =========================================================
           2. SIDEBAR COMPONENTS (STYLE MỚI)
           ========================================================= */
        .user-box {
            background: rgba(255, 255, 255, 0.03);
            border-radius: 15px; padding: 12px;
            border: 1px solid rgba(255, 255, 255, 0.08);
            display: flex; align-items: center; margin-bottom: 20px;
            transition: 0.3s;
        }
        .user-box:hover { background: rgba(255, 193, 7, 0.05); border-color: #ffc107; }

        /* Search Menu */
        .search-menu {
            background: rgba(255, 255, 255, 0.05);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 10px; padding: 8px 12px;
            color: white; font-size: 13px; width: 100%; margin-bottom: 15px;
        }
        .search-menu:focus { outline: none; border-color: #ffc107; background: rgba(255, 255, 255, 0.08); }

        .nav-link {
            color: rgba(255, 255, 255, 0.5);
            padding: 10px 15px; margin-bottom: 4px;
            border-radius: 12px; text-decoration: none;
            display: flex; align-items: center;
            transition: all 0.3s ease; font-size: 14px;
        }

        .nav-link i { width: 25px; font-size: 1.1rem; margin-right: 10px; }

        .nav-link:hover:not(.active) {
            background-color: rgba(255, 255, 255, 0.05);
            color: #fff; transform: translateX(5px);
        }

        .nav-link.active {
            background-color: rgba(255, 193, 7, 0.12) !important;
            color: #ffc107 !important; font-weight: 600;
        }

        .menu-label {
            font-size: 10px; color: rgba(255, 255, 255, 0.2);
            margin: 15px 0 8px 10px; text-transform: uppercase;
            letter-spacing: 1.5px; font-weight: 800;
        }

        /* =========================================================
           3. MODAL HỒ SƠ (THỪA HƯỞNG TỪ QUẢN LÝ)
           ========================================================= */
        .modal-content {
            background-color: #1a1a1a; border: 1px solid #333;
            color: white; border-radius: 25px !important;
        }

        .google-tab-wrapper .nav-link {
            border-radius: 0 !important;
            color: rgba(255, 255, 255, 0.4) !important;
        }

        .google-tab-wrapper .nav-link.active {
            color: #ffc107 !important;
            border-bottom: 2px solid #ffc107 !important;
            background: transparent !important;
        }

        .form-control {
            background-color: #0d0d0d !important;
            border: 1px solid #333 !important;
            color: white !important;
            border-radius: 10px !important;
        }

        /* Tinh chỉnh nút menu ngắn lại và gọn hơn */
        #sidebarMenu .nav-link {
            width: 90%;            /* Nút không chiếm hết 100% chiều ngang sidebar */
            margin-left: auto;     /* Căn giữa nút */
            margin-right: auto;
            padding: 8px 15px;
            margin-bottom: 5px;
            border-radius: 12px;   /* Bo tròn nhẹ */
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            border-left: 3px solid transparent; /* Tạo đường kẻ ẩn bên trái */
        }

    </style>
</head>

<body>

<div class="wrapper">
    <div class="sidebar">
        <div class="text-center mb-4">
            <h4 class="fw-bold text-white mb-0">POLY <span class="text-warning">COFFEE</span></h4>
            <div id="digital-clock" class="text-secondary" style="font-size: 11px; letter-spacing: 1px;">00:00:00</div>
        </div>

        <div class="user-box">
            <div class="bg-warning rounded-circle d-flex align-items-center justify-content-center shadow"
                 style="width: 42px; height: 42px;">
                <c:choose>
                    <c:when test="${not empty sessionScope.user.hinhAnh}">
                        <img src="${ctx}/uploads/${sessionScope.user.hinhAnh}" class="rounded-circle"
                             style="width:100%; height:100%; object-fit:cover;">
                    </c:when>
                    <c:otherwise><i class="fas fa-user text-dark"></i></c:otherwise>
                </c:choose>
            </div>
            <div class="ms-3 overflow-hidden">
                <div class="fw-bold text-white text-truncate" style="font-size: 14px;">
                    <c:out value="${sessionScope.user.tenNguoiDung}" default="Nhân viên"/>
                </div>
                <div class="text-secondary" style="font-size: 11px;">
                    <i class="fas fa-circle text-success me-1" style="font-size: 8px;"></i> Online
                </div>
            </div>
        </div>

        <input type="text" id="menuSearch" class="search-menu" placeholder="Tìm nhanh..." onkeyup="filterMenu()">

        <div class="menu-container" style="flex-grow: 1; overflow-y: auto;" id="sidebarMenu">
            <div class="nav flex-column">
                <span class="menu-label">Hệ thống</span>
                <a href="nhanvien/taohoadon" target="mainFrame" class="nav-link active">
                    <i class="fas fa-plus-circle"></i> Tạo hóa đơn
                </a>
                <a href="hoadoncuatoi" target="mainFrame" class="nav-link">
                    <i class="fas fa-history"></i> Hóa đơn của tôi
                </a>

                <span class="menu-label">Cá nhân</span>
                <a class="nav-link" data-bs-toggle="modal" data-bs-target="#modalHoSo">
                    <i class="fas fa-user-circle"></i> Hồ sơ & Bảo mật
                </a>
            </div>
        </div>

        <div class="logout-section mt-auto pt-3 border-top border-secondary border-opacity-10">
            <a href="${ctx}/home" class="nav-link text-danger fw-bold">
                <i class="fas fa-power-off"></i> Đăng xuất
            </a>
        </div>
    </div>

    <div class="main-content">
        <iframe name="mainFrame" id="mainFrame" src="nhanvien/taohoadon"></iframe>
    </div>
</div>

<div class="modal fade" id="modalHoSo" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header border-0 px-4 pt-4 pb-0">
                <h6 class="modal-title fw-bold text-warning">HỒ SƠ CỦA TÔI</h6>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body p-0">
                <div class="google-tab-wrapper mt-3">
                    <ul class="nav nav-pills nav-justified">
                        <li class="nav-item">
                            <button class="nav-link active" data-bs-toggle="pill" data-bs-target="#tab-info">Thông tin
                            </button>
                        </li>
                        <li class="nav-item">
                            <button class="nav-link" data-bs-toggle="pill" data-bs-target="#tab-password">Đổi mật khẩu
                            </button>
                        </li>
                    </ul>
                </div>

                <div class="tab-content p-4">
                    <div class="tab-pane fade show active" id="tab-info">
                        <form action="${ctx}/CapNhatHoSoServlet" method="POST" enctype="multipart/form-data">

                            <div class="text-center mb-4">
                                <div class="avatar-wrapper mx-auto" onclick="document.getElementById('uploadAvatar').click()"
                                     style="width: 120px; height: 120px; cursor: pointer; position: relative;">

                                    <div class="avatar-display shadow-sm" id="avatarPreview"
                                         style="width: 100%; height: 100%; border-radius: 20px; overflow: hidden; border: 3px solid #ffc107;">
                                        <c:choose>
                                            <c:when test="${not empty sessionScope.user.hinhAnh}">
                                                <img src="${ctx}/uploads/${sessionScope.user.hinhAnh}" style="width: 100%; height: 100%; object-fit: cover;">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${ctx}/assets/img/default-avatar.png" style="width: 100%; height: 100%; object-fit: cover;">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="avatar-overlay" style="position: absolute; bottom: 5px; right: 5px; background: #ffc107; border-radius: 50%; width: 30px; height: 30px; display: flex; align-items: center; justify-content: center; color: black; border: 2px solid #1a1a1a;">
                                        <i class="fas fa-camera" style="font-size: 14px;"></i>
                                    </div>
                                </div>

                                <input type="file" id="uploadAvatar" name="hinhAnh" hidden accept="image/*" onchange="previewImage(this)">
                                <p class="small text-secondary mt-2">Nhấn vào ảnh để thay đổi</p>
                            </div>

                            <div class="mb-3">
                                <label class="small text-secondary mb-1">Họ và tên</label>
                                <input type="text" name="tenNguoiDung" class="form-control" value="${sessionScope.user.tenNguoiDung}">
                            </div>
                            <div class="mb-3">
                                <label class="small text-secondary mb-1">Tên đăng nhập (ID)</label>
                                <input type="text" class="form-control" value="${sessionScope.user.tenDangNhap}" readonly style="opacity: 0.6;">
                            </div>
                            <div class="mb-3">
                                <label class="small text-secondary mb-1">Email</label>
                                <input type="email" name="email" class="form-control" value="${sessionScope.user.email}">
                            </div>

                            <button type="submit" class="btn btn-warning w-100 fw-bold py-2 mt-2">LƯU THÔNG TIN</button>
                        </form>
                    </div>

                    <div class="tab-pane fade" id="tab-password">
                        <form action="${ctx}/DoiMatKhauServlet" method="POST">
                            <div class="mb-3">
                                <label class="small text-secondary mb-1">Mật khẩu cũ</label>
                                <input type="password" name="oldPass" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="small text-secondary mb-1">Mật khẩu mới</label>
                                <input type="password" name="newPass" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-warning w-100 fw-bold py-2 mt-2">CẬP NHẬT</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // 1. Đồng hồ
    setInterval(() => {
        document.getElementById('digital-clock').innerText = new Date().toLocaleTimeString('vi-VN');
    }, 1000);

    // 2. Tìm nhanh
    function filterMenu() {
        let val = document.getElementById('menuSearch').value.toLowerCase();
        document.querySelectorAll('#sidebarMenu .nav-link').forEach(link => {
            link.style.display = link.innerText.toLowerCase().includes(val) ? "flex" : "none";
        });
    }

    // 3. Xử lý Active state
    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', function() {
            if (this.getAttribute('target') === 'mainFrame') {
                document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
                this.classList.add('active');
            }
        });
    });
</script>
</body>
</html>