<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

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
      height: 100%; margin: 0; overflow: hidden;
      background-color: #0d0d0d;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .wrapper { display: flex; height: 100vh; width: 100vw; }

    .sidebar {
      background-color: #161616; width: 260px; flex-shrink: 0;
      border-right: 1px solid rgba(255, 255, 255, 0.05);
      display: flex; flex-direction: column; padding: 20px 15px;
    }

    .main-content { flex-grow: 1; height: 100%; background-color: #0d0d0d; }
    iframe { width: 100%; height: 100%; border: none; }

    /* SIDEBAR COMPONENTS */
    .user-box {
      background-color: rgba(255, 255, 255, 0.03); border-radius: 15px;
      padding: 15px; border: 1px solid rgba(255, 255, 255, 0.05);
      display: flex; align-items: center; margin-bottom: 20px;
    }

    .nav-link {
      color: rgba(255, 255, 255, 0.5); padding: 10px 15px; margin-bottom: 2px;
      border-radius: 10px; text-decoration: none; display: flex;
      align-items: center; transition: all 0.3s ease; cursor: pointer; font-size: 14px;
    }

    .nav-link i { width: 20px; text-align: center; margin-right: 12px; }

    .nav-link:hover:not(.active) { background-color: rgba(255, 255, 255, 0.05); color: #fff; }

    .nav-link.active {
      background-color: rgba(255, 193, 7, 0.1) !important;
      color: #ffc107 !important; font-weight: 600;
    }

    .menu-label {
      font-size: 10px; color: rgba(255, 255, 255, 0.2);
      margin: 15px 0 8px 10px; text-transform: uppercase;
      letter-spacing: 1.5px; font-weight: 800;
    }

    /* MODAL & TABS */
    .modal-content {
      background-color: #1a1a1a; border: 1px solid #333; color: white;
      border-radius: 25px !important; overflow: hidden;
    }

    .google-tab-wrapper .nav-link {
      border-radius: 0 !important; color: rgba(255, 255, 255, 0.4) !important;
    }

    .google-tab-wrapper .nav-link.active { background-color: #ffc107 !important; color: #000 !important; }

    .form-control {
      background-color: #0d0d0d !important; border: 1px solid #333 !important;
      color: white !important; border-radius: 10px !important;
    }

    .logout-link { color: #ff5e5e !important; font-weight: 600; }

    /* Custom scrollbar cho menu */
    .menu-container::-webkit-scrollbar { width: 4px; }
    .menu-container::-webkit-scrollbar-thumb { background: rgba(255,255,255,0.1); border-radius: 10px; }
  </style>
</head>

<body>

<div class="wrapper">
  <div class="sidebar">
    <div class="text-center mb-4 pt-2">
      <h4 class="fw-bold text-white mb-0">POLY <span class="text-warning">CAFE</span></h4>
      <span style="font-size: 10px; color: #444;">PHIÊN BẢN 2.4.1</span>
    </div>

    <div class="user-box">
      <div class="bg-warning rounded-circle d-flex align-items-center justify-content-center flex-shrink-0 shadow"
           style="width: 42px; height: 42px;">
        <i class="fas fa-user-tie text-dark"></i>
      </div>
      <div class="ms-3 overflow-hidden">
        <div class="fw-bold text-white text-truncate" style="font-size: 14px;">
          <c:out value="${sessionScope.user.tenNguoiDung}" default="Admin System"/>
        </div>
        <div class="text-warning" style="font-size: 11px; font-weight: 600;">
          ${sessionScope.user.vaiTro}
        </div>
      </div>
    </div>

    <div class="menu-container" style="flex-grow: 1; overflow-y: auto;">
      <div class="nav flex-column">

        <span class="menu-label">Tổng quan</span>
        <a href="${ctx}/dashboard" target="mainFrame" class="nav-link active">
          <i class="fas fa-chart-line"></i> Dashboard
        </a>

        <span class="menu-label">Thực đơn</span>
        <a href="${ctx}/taohoadon" target="mainFrame" class="nav-link">
          <i class="fas fa-plus-circle"></i> Tạo hóa đơn
        </a>
        <a href="${ctx}/hoadon" target="mainFrame" class="nav-link">
          <i class="fas fa-file-invoice-dollar"></i> Quản lý hóa đơn
        </a>

          <span class="menu-label">Quản lý kho & Đồ uống</span>
          <a href="${ctx}/nhanvien/quanlydouong" target="mainFrame" class="nav-link">
            <i class="fas fa-coffee"></i> Đồ uống
          </a>
          <a href="${ctx}/loaidouong" target="mainFrame" class="nav-link">
            <i class="fas fa-th-list"></i> Danh mục
          </a>
          <a href="${ctx}/khonguyenlieu" target="mainFrame" class="nav-link">
            <i class="fas fa-archive"></i> Kho nguyên liệu
          </a>
          <a href="${ctx}/nhaphang" target="mainFrame" class="nav-link">
            <i class="fas fa-truck-loading"></i> Nhập hàng
          </a>

          <span class="menu-label">Nhân sự & Báo cáo</span>
          <a href="${ctx}/nhanvien/quanlynhanvien" target="mainFrame" class="nav-link">
            <i class="fas fa-users-cog"></i> Nhân viên
          </a>
          <a href="${ctx}/thongke" target="mainFrame" class="nav-link">
            <i class="fas fa-chart-bar"></i> Báo cáo thống kê
          </a>
        <span class="menu-label">Cá nhân</span>
        <a class="nav-link" data-bs-toggle="modal" data-bs-target="#modalHoSo">
          <i class="fas fa-user-circle"></i> Hồ sơ cá nhân
        </a>
      </div>
    </div>

    <div class="logout-section mt-auto">
      <hr class="text-secondary opacity-10 my-3">
      <a href="${ctx}/home" class="nav-link logout-link">
        <i class="fas fa-power-off"></i> Đăng xuất hệ thống
      </a>
    </div>
  </div>

  <div class="main-content">
    <iframe name="mainFrame" id="mainFrame" src="${ctx}/dashboard"></iframe>
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
            <li class="nav-item">
              <button class="nav-link active" id="pills-info-tab" data-bs-toggle="pill" data-bs-target="#tab-info" type="button" role="tab">Thông tin chung</button>
            </li>
            <li class="nav-item">
              <button class="nav-link" id="pills-password-tab" data-bs-toggle="pill" data-bs-target="#tab-password" type="button" role="tab">Đổi mật khẩu</button>
            </li>
          </ul>
        </div>
        <div class="tab-content p-4" id="pills-tabContent">
          <div class="tab-pane fade show active" id="tab-info" role="tabpanel">
            <div class="avatar-preview mb-4 text-center">
              <div class="bg-dark rounded-4 d-inline-block p-4 border border-secondary">
                <i class="fas fa-user-shield fa-2x text-warning"></i>
              </div>
            </div>
            <div class="mb-3">
              <label class="small text-secondary mb-2">Tên người dùng</label>
              <input type="text" class="form-control" value="${sessionScope.user.tenNguoiDung}" readonly>
            </div>
            <div class="mb-3">
              <label class="small text-secondary mb-2">Email</label>
              <input type="text" class="form-control" value="${sessionScope.user.email}" readonly>
            </div>
          </div>
          <div class="tab-pane fade" id="tab-password" role="tabpanel">
            <form action="${ctx}/DoiMatKhauServlet" method="POST">
              <div class="mb-3">
                <label class="small text-secondary mb-2">Mật khẩu cũ</label>
                <input type="password" name="oldPass" class="form-control" required>
              </div>
              <div class="mb-3">
                <label class="small text-secondary mb-2">Mật khẩu mới</label>
                <input type="password" name="newPass" class="form-control" required>
              </div>
              <button type="submit" class="btn btn-warning w-100 fw-bold mt-2">CẬP NHẬT</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Logic Active Menu
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