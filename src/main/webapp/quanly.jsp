<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Dashboard</title>

  <style>
    body {
      margin:0;
      font-family:Arial;
      background:#0f1115;
      color:white;
      display:flex;
      height: 100vh;
      overflow: hidden;
    }

    /* ===== SIDEBAR ===== */
    .sidebar {
      width:260px;
      background:#1c1f26;
      height:100%;
      padding:20px;
      box-sizing:border-box;
    }

    /* Logo */
    .logo {
      display:flex;
      align-items:center;
      gap:10px;
      margin-bottom:20px;
    }

    .logo-box {
      width:40px;
      height:40px;
      background:orange;
      border-radius:8px;
    }

    .logo-text {
      font-weight:bold;
    }

    /* User */
    .user {
      background:#2a2e38;
      padding:12px;
      border-radius:10px;
      margin-bottom:20px;
    }

    .user-name {
      font-weight:bold;
    }

    .user-role {
      color:orange;
      font-size:13px;
    }

    /* Menu item */
    .menu-item {
      display:flex;
      align-items:center;
      gap:10px;
      padding:10px;
      border-radius:8px;
      cursor:pointer;
      margin-top:5px;
      text-decoration:none;
      color:white;
    }

    .menu-item:hover {
      background:#2a2e38;
    }

    .active {
      background:#3a2a10;
      color:orange;
    }

    /* Logout */
    .logout {
      margin-top:30px;
      padding:10px;
      background:#2a2e38;
      border-radius:8px;
      cursor:pointer;
      text-decoration:none;
      color:white;
      display:block;
      text-align:center;
    }

    /* ===== CONTENT ===== */
    .content {
      flex:1;
      height:100%;
      display:flex;
      flex-direction:column;
    }

    iframe {
      flex:1;
      border:none;
      width:100%;
    }
  </style>

  <script>
    function setActiveMenu(el) {
      // Xóa class active tất cả menu
      const items = document.querySelectorAll('.menu-item');
      items.forEach(i => i.classList.remove('active'));
      // Thêm active cho menu đang click
      el.classList.add('active');
    }
  </script>

</head>
<body>

<!-- SIDEBAR -->
<div class="sidebar">

  <!-- Logo -->
  <div class="logo">
    <div class="logo-box"></div>
    <div>
      <div class="logo-text">PolyCafe</div>
      <div style="font-size:12px;color:#aaa;">v2.4.1</div>
    </div>
  </div>

  <!-- User -->
  <div  class="user">
   <a href="${ctx}/dashboard" class="menu-item active" target="mainFrame" onclick="setActiveMenu(this)">
     <div class="row">
    <div class="user-name col-md-12">Do Nguyen Long</div>
    <div class="user-role col-md-12">Quản lý</div>
     </div>
   </a>
  </div>

  <!-- Menu -->
  <a href="${ctx}/dashboard" class="menu-item active" target="mainFrame" onclick="setActiveMenu(this)">Dashboard</a>
  <a href="${ctx}/nhanvien/quanlydouong" class="menu-item" target="mainFrame" onclick="setActiveMenu(this)">Do Uong</a>
  <a href="${ctx}/loaidouong" class="menu-item" target="mainFrame" onclick="setActiveMenu(this)">Danh mục</a>
  <a href="${ctx}/khonguyenlieu" class="menu-item" target="mainFrame" onclick="setActiveMenu(this)">Kho nguyên liệu</a>
  <a href="${ctx}/hoadon" class="menu-item" target="mainFrame" onclick="setActiveMenu(this)">Hóa đơn</a>
  <a href="${ctx}/nhaphang" class="menu-item" target="mainFrame" onclick="setActiveMenu(this)">Nhập hàng</a>
  <a href="${ctx}/nhanvien/quanlynhanvien" class="menu-item" target="mainFrame" onclick="setActiveMenu(this)">Nhân viên</a>
  <a href="${ctx}/thongke" class="menu-item" target="mainFrame" onclick="setActiveMenu(this)">Báo cáo</a>

  <a href="${ctx}/home" class="logout">Đăng xuất</a>
</div>

<!-- CONTENT -->
<div class="content">
  <iframe name="mainFrame" src="${ctx}/dashboard"></iframe>
</div>

</body>
</html>