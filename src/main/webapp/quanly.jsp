<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Poly Coffee - Hệ Thống Quản Lý</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>
        /* LAYOUT CHÍNH */
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
            transition: all 0.3s ease;
        }

        .main-content {
            flex-grow: 1;
            height: 100%;
            background-color: #0d0d0d;
            position: relative;
        }

        iframe {
            width: 100%;
            height: 100%;
            border: none;
        }

        /* LOADING SPINNER CHO IFRAME */
        .loader {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            display: none;
            z-index: 10;
        }

        /* SIDEBAR COMPONENTS */
        .user-box {
            background-color: rgba(255, 255, 255, 0.03);
            border-radius: 15px;
            padding: 15px;
            border: 1px solid rgba(255, 255, 255, 0.05);
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }

        /* SEARCH BOX */
        .search-menu {
            background: rgba(255, 255, 255, 0.05);
            border: none;
            border-radius: 10px;
            padding: 8px 12px;
            color: white;
            font-size: 13px;
            width: 100%;
            margin-bottom: 15px;
        }

        .nav-link {
            color: rgba(255, 255, 255, 0.5);
            padding: 10px 15px;
            margin-bottom: 2px;
            border-radius: 10px;
            text-decoration: none;
            display: flex;
            align-items: center;
            transition: all 0.3s;
            font-size: 14px;
        }

        .nav-link i {
            width: 25px;
            font-size: 1.1rem;
        }

        .nav-link:hover:not(.active) {
            background-color: rgba(255, 255, 255, 0.05);
            color: #fff;
            transform: translateX(5px);
        }

        .nav-link.active {
            background-color: rgba(255, 193, 7, 0.1) !important;
            color: #ffc107 !important;
            font-weight: 600;
        }

        /* MODAL CUSTOM */
        .modal-content {
            background-color: #1a1a1a;
            border: 1px solid #333;
            color: white;
            border-radius: 25px !important;
        }

        .form-control {
            background-color: #0d0d0d !important;
            border: 1px solid #333 !important;
            color: white !important;
            border-radius: 10px !important;
        }

        .google-tab-wrapper .nav-link {
            border-radius: 0 !important;
            color: rgba(255, 255, 255, 0.4) !important;
            background: transparent !important;
        }

        .google-tab-wrapper .nav-link.active {
            color: #ffc107 !important;
            border-bottom: 2px solid #ffc107 !important;
        }

        .menu-label {
            font-size: 10px;
            color: rgba(255, 255, 255, 0.2);
            margin: 15px 0 8px 10px;
            text-transform: uppercase;
            letter-spacing: 1.5px;
            font-weight: 800;
        }

        /* AVATAR UPLOAD */
        .avatar-wrapper {
            position: relative;
            width: 100px;
            height: 100px;
            margin: 0 auto 15px;
            cursor: pointer;
        }

        .avatar-display {
            width: 100%;
            height: 100%;
            border-radius: 20px;
            overflow: hidden;
            border: 2px dashed #444;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #222;
        }

        .avatar-display img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        /* Thu hẹp khoảng cách các mục menu */
        #sidebarMenu .nav-link {
            padding: 6px 12px; /* Giảm từ 10px xuống 6px để các nút sát nhau hơn */
            margin-bottom: 1px; /* Khoảng cách giữa các nút */
            font-size: 13px; /* Chữ nhỏ hơn một chút cho tinh tế */
        }

        /* Thu nhỏ các icon để cân đối */
        #sidebarMenu .nav-link i {
            width: 22px; /* Thu hẹp chiều rộng khung chứa icon */
            font-size: 1rem; /* Thu nhỏ icon */
            margin-right: 8px; /* Giảm khoảng cách giữa icon và chữ */
        }

        /* Làm gọn các tiêu đề nhóm (Tổng quan, Quản lý kho...) */
        .menu-label {
            font-size: 9px; /* Chữ nhỏ lại */
            margin: 10px 0 4px 10px; /* Giảm margin trên và dưới của nhãn */
            letter-spacing: 1px; /* Giảm khoảng cách chữ của nhãn */
        }

        /* Tinh chỉnh thanh cuộn để không chiếm diện tích */
        .menu-container::-webkit-scrollbar {
            width: 3px; /* Thanh cuộn cực mảnh */
        }
    </style>
</head>

<body>

<div class="wrapper">
    <div class="sidebar">
        <div class="text-center mb-4">
            <h4 class="fw-bold text-white mb-0">POLY <span class="text-warning">CAFE</span></h4>
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
                    <c:otherwise><i class="fas fa-user-tie text-dark"></i></c:otherwise>
                </c:choose>
            </div>
            <div class="ms-3 overflow-hidden">
                <div class="fw-bold text-white text-truncate" style="font-size: 14px;">
                    ${sessionScope.user.tenNguoiDung != null ? sessionScope.user.tenNguoiDung : 'Admin System'}
                </div>
                <div class="text-warning" style="font-size: 11px; font-weight: 600;">${sessionScope.user.vaiTro}</div>
            </div>
        </div>

        <input type="text" id="menuSearch" class="search-menu" placeholder="Tìm nhanh chức năng..."
               onkeyup="filterMenu()">

        <div class="menu-container" style="flex-grow: 1; overflow-y: auto;" id="sidebarMenu">
            <div class="nav flex-column">
                <span class="menu-label">Hệ thống</span>
                <a href="${ctx}/dashboard" target="mainFrame" class="nav-link active"><i class="fas fa-chart-line"></i>Dashboard</a>
                <a href="${ctx}/quanly/quanlydouong" target="mainFrame" class="nav-link"><i class="fas fa-coffee"></i>Đồ uống</a>
                <a href="${ctx}/quanly/loaidouong" target="mainFrame" class="nav-link"><i class="fas fa-th-list"></i>Danh mục</a>
                <a href="${ctx}/quanly/quanlynhacungcap" target="mainFrame" class="nav-link"><i class="fas fa-truck-moving"></i>Nhà cung cấp</a>

                <span class="menu-label">Nhân sự & Báo cáo</span>
                <a href="${ctx}/quanly/quanlynhanvien" target="mainFrame" class="nav-link"><i class="fas fa-users-cog"></i>Nhân viên</a>
                <a href="${ctx}/hoadoncuatoi" target="mainFrame" class="nav-link"><i class="fas fa-file-invoice"></i>Hóa đơn</a>
                <a href="${ctx}/quanly/thongke" target="mainFrame" class="nav-link"><i class="fas fa-chart-bar"></i>Báo cáo</a>

                <span class="menu-label">Cá nhân</span>
                <a class="nav-link" data-bs-toggle="modal" data-bs-target="#modalHoSo"><i class="fas fa-user-circle"></i>Hồ sơ</a>
            </div>
        </div>

        <div class="logout-section mt-auto">
            <a href="${ctx}/home" class="nav-link text-danger fw-bold"><i class="fas fa-power-off"></i> Đăng xuất</a>
        </div>
    </div>

    <div class="main-content">
        <div class="loader text-warning" id="iframeLoader">
            <div class="spinner-border" role="status"></div>
        </div>
        <iframe name="mainFrame" id="mainFrame" src="${ctx}/dashboard" onload="hideLoader()"></iframe>
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
                                <div class="avatar-wrapper mx-auto"
                                     onclick="document.getElementById('uploadAvatar').click()"
                                     style="width: 120px; height: 120px; cursor: pointer; position: relative;">

                                    <div class="avatar-display shadow-sm" id="avatarPreview"
                                         style="width: 100%; height: 100%; border-radius: 20px; overflow: hidden; border: 3px solid #ffc107;">
                                        <c:choose>
                                            <c:when test="${not empty sessionScope.user.hinhAnh}">
                                                <img src="${ctx}/uploads/${sessionScope.user.hinhAnh}"
                                                     style="width: 100%; height: 100%; object-fit: cover;">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${ctx}/assets/img/default-avatar.png"
                                                     style="width: 100%; height: 100%; object-fit: cover;">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="avatar-overlay"
                                         style="position: absolute; bottom: 5px; right: 5px; background: #ffc107; border-radius: 50%; width: 30px; height: 30px; display: flex; align-items: center; justify-content: center; color: black; border: 2px solid #1a1a1a;">
                                        <i class="fas fa-camera" style="font-size: 14px;"></i>
                                    </div>
                                </div>

                                <input type="file" id="uploadAvatar" name="hinhAnh" hidden accept="image/*"
                                       onchange="previewImage(this)">
                                <p class="small text-secondary mt-2">Nhấn vào ảnh để thay đổi</p>
                            </div>

                            <div class="mb-3">
                                <label class="small text-secondary mb-1">Họ và tên</label>
                                <input type="text" name="tenNguoiDung" class="form-control"
                                       value="${sessionScope.user.tenNguoiDung}">
                            </div>
                            <div class="mb-3">
                                <label class="small text-secondary mb-1">Tên đăng nhập (ID)</label>
                                <input type="text" class="form-control" value="${sessionScope.user.tenDangNhap}"
                                       readonly style="opacity: 0.6;">
                            </div>
                            <div class="mb-3">
                                <label class="small text-secondary mb-1">Email</label>
                                <input type="email" name="email" class="form-control"
                                       value="${sessionScope.user.email}">
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
    function previewImage(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function (e) {
                // Thay thế nội dung trong avatarPreview bằng ảnh mới chọn
                const previewContainer = document.getElementById('avatarPreview');
                previewContainer.innerHTML = `<img src="${e.target.result}" style="width: 100%; height: 100%; object-fit: cover;">`;
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    // 1. Đồng hồ số
    function updateClock() {
        const now = new Date();
        const timeStr = now.toLocaleTimeString('vi-VN');
        document.getElementById('digital-clock').innerText = timeStr;
    }

    setInterval(updateClock, 1000);

    // 2. Tìm kiếm Menu nhanh
    function filterMenu() {
        let input = document.getElementById('menuSearch').value.toLowerCase();
        let links = document.querySelectorAll('#sidebarMenu .nav-link');
        links.forEach(link => {
            let text = link.innerText.toLowerCase();
            link.style.display = text.includes(input) ? "flex" : "none";
        });
    }

    // 3. Xử lý Loader cho Iframe
    function showLoader() {
        document.getElementById('iframeLoader').style.display = 'block';
    }

    function hideLoader() {
        document.getElementById('iframeLoader').style.display = 'none';
    }

    // 4. Preview ảnh
    function previewImage(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = e => document.getElementById('avatarDisplay').innerHTML = `<img src="${e.target.result}">`;
            reader.readAsDataURL(input.files[0]);
        }
    }

    // 5. Logic Active & Loader
    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', function () {
            if (this.getAttribute('target') === 'mainFrame') {
                showLoader();
                document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
                this.classList.add('active');
            }
        });
    });

    function validatePassword() {
        const pass = document.getElementById('newPass').value;
        const confirm = document.getElementById('confirmPass').value;
        if (pass !== confirm) {
            document.getElementById('passError').style.display = 'block';
            return false;
        }
        return true;
    }

    window.onload = function () {
        const msg = new URLSearchParams(window.location.search).get('msg');
        if (msg === 'success') alert("Thực hiện thành công!");
        if (msg === 'error') alert("Lỗi: Kiểm tra lại mật khẩu cũ!");
    };
</script>
</body>
</html>