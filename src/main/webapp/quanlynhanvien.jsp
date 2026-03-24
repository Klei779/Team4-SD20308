<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Nhân Viên - Dark Theme</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* --- TỔNG THỂ NỀN ĐEN --- */
        body {
            background-color: #0c0c0c !important; /* Đen tuyền */
            color: #ffffff !important;
            padding: 25px;
            font-family: 'Segoe UI', sans-serif;
        }

        /* --- THANH TÌM KIẾM --- */
        .search-area {
            background-color: #1a1a1a !important;
            border: 1px solid #333 !important;
            border-radius: 12px;
            padding: 15px;
            margin-bottom: 20px;
        }
        .form-control, .form-select {
            background-color: #262626 !important;
            border: 1px solid #444 !important;
            color: white !important;
            border-radius: 8px !important;
        }

        /* --- BẢNG MÀU ĐEN (FIX LỖI MÀU TRẮNG) --- */
        .table-container {
            background-color: #1a1a1a !important; /* Buộc container màu đen */
            border: 1px solid #333;
            border-radius: 12px;
            overflow: hidden;
        }

        /* Ép bảng Bootstrap về màu đen */
        .table {
            background-color: #1a1a1a !important;
            color: #ffffff !important;
            margin-bottom: 0 !important;
            border-color: #333 !important;
        }

        /* Tiêu đề bảng */
        .table thead th {
            background-color: #262626 !important; /* Xám đen đậm cho header */
            color: #aaaaaa !important;
            font-size: 11px;
            text-transform: uppercase;
            padding: 15px;
            border-bottom: 1px solid #444 !important;
        }

        /* Các dòng trong bảng */
        .table tbody tr {
            background-color: #1a1a1a !important; /* Ép từng dòng màu đen */
            border-bottom: 1px solid #333 !important;
        }

        .table tbody td {
            padding: 15px;
            color: #dddddd !important;
            vertical-align: middle;
        }

        /* Hiệu ứng hover cho dòng */
        .table-hover tbody tr:hover {
            background-color: #252525 !important;
        }

        /* --- NÚT BẤM & AVATAR --- */
        .btn-warning-custom {
            background-color: #ffc107 !important;
            color: #000 !important;
            font-weight: bold;
            border: none;
            border-radius: 8px;
        }

        .avatar-square {
            width: 38px; height: 38px;
            background: #333; color: #ffc107;
            border: 1px solid #444;
            display: flex; align-items: center; justify-content: center;
            border-radius: 8px; font-weight: bold;
        }

        .badge-role {
            font-size: 10px; padding: 3px 8px; border-radius: 6px;
            border: 1px solid #ffc107; color: #ffc107; background: transparent;
        }

        .status-active { color: #2ecc71; font-size: 12px; }

        /* --- MODAL --- */
        .modal-content {
            background-color: #1a1a1a !important;
            color: white;
            border: 1px solid #444;
            border-radius: 15px;
        }
    </style>
</head>
<body>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h4 class="fw-bold m-0 text-white text-uppercase">QUẢN LÝ NHÂN VIÊN</h4>
        <small class="text-secondary">Hệ thống Poly Coffee</small>
    </div>
    <button class="btn btn-warning-custom px-4" onclick="showAddModal()">
        <i class="fas fa-plus me-2"></i> THÊM MỚI
    </button>
</div>

<div class="search-area">
    <form action="quanlynhanvien" method="GET" class="row g-2 m-0">
        <div class="col-md-5">
            <input type="text" name="txtSearch" class="form-control" placeholder="Tìm theo tên hoặc ID..." value="${param.txtSearch}">
        </div>
        <div class="col-md-3">
            <select name="role" class="form-select" onchange="this.form.submit()">
                <option value="">Tất cả chức vụ</option>
                <option value="ADMIN" ${param.role == 'ADMIN' ? 'selected' : ''}>Quản lý</option>
                <option value="STAFF" ${param.role == 'STAFF' ? 'selected' : ''}>Nhân viên</option>
            </select>
        </div>
        <div class="col-md-3">
            <select name="status" class="form-select" onchange="this.form.submit()">
                <option value="">Trạng thái</option>
                <option value="1" ${param.status == '1' ? 'selected' : ''}>Hoạt động</option>
                <option value="0" ${param.status == '0' ? 'selected' : ''}>Bị khóa</option>
            </select>
        </div>
        <div class="col-md-1">
            <button type="submit" class="btn btn-warning-custom w-100"><i class="fas fa-search"></i></button>
        </div>
    </form>
</div>

<div class="table-container shadow-lg">
    <table class="table table-dark table-hover">
        <thead>
        <tr>
            <th class="ps-4">Mã</th>
            <th>Nhân viên</th>
            <th>Vai trò</th>
            <th>Liên hệ</th>
            <th>Trạng thái</th>
            <th class="text-center">Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="nd" items="${dsNhanVien}">
            <tr>
                <td class="ps-4 text-secondary">#${nd.maNguoiDung}</td>
                <td>
                    <div class="d-flex align-items-center">
                        <div class="avatar-square me-3">${nd.tenNguoiDung.charAt(0)}</div>
                        <div>
                            <div class="fw-bold text-white">${nd.tenNguoiDung}</div>
                            <div class="small text-secondary" style="font-size: 10px;">ID: ${nd.tenDangNhap}</div>
                        </div>
                    </div>
                </td>
                <td><span class="badge-role">${nd.vaiTro}</span></td>
                <td class="small">${nd.email}</td>
                <td>
                        <span class="status-active">
                            <i class="fas fa-circle me-1" style="font-size: 8px;"></i>
                            ${nd.trangThai ? 'Hoạt động' : 'Bị khóa'}
                        </span>
                </td>
                <td class="text-center">
                    <button class="btn btn-link text-info p-1 me-2" onclick="showEditModal('${nd.maNguoiDung}', '${nd.tenNguoiDung}', '${nd.tenDangNhap}', '${nd.matKhau}', '${nd.email}', '${nd.vaiTro}', '${nd.trangThai ? 1 : 0}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-link text-danger p-1" onclick="confirmDelete('${nd.maNguoiDung}', '${nd.tenNguoiDung}')">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="modal fade" id="modalNhanVien" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form action="quanlynhanvien" method="POST">
                <input type="hidden" name="action" value="save">
                <input type="hidden" name="maNguoiDung" id="mMa">
                <div class="modal-header border-0">
                    <h5 class="modal-title fw-bold text-warning">THÔNG TIN TÀI KHOẢN</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4 pt-0">
                    <label class="small text-secondary mb-1">Họ và tên</label>
                    <input type="text" name="tenNguoiDung" id="mTen" class="form-control mb-3" required>
                    <div class="row g-2 mb-3">
                        <div class="col-md-6">
                            <label class="small text-secondary mb-1">Tên đăng nhập</label>
                            <input type="text" name="tenDangNhap" id="mUser" class="form-control" required>
                        </div>
                        <div class="col-md-6">
                            <label class="small text-secondary mb-1">Mật khẩu</label>
                            <input type="password" name="matKhau" id="mPass" class="form-control" required>
                        </div>
                    </div>
                    <label class="small text-secondary mb-1">Email</label>
                    <input type="email" name="email" id="mEmail" class="form-control mb-3" required>
                    <div class="row g-2">
                        <div class="col-md-6">
                            <label class="small text-secondary mb-1">Vai trò</label>
                            <select name="vaiTro" id="mVaiTro" class="form-select">
                                <option value="STAFF">Nhân viên</option>
                                <option value="ADMIN">Quản lý</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="small text-secondary mb-1">Trạng thái</label>
                            <select name="trangThai" id="mTrangThai" class="form-select">
                                <option value="1">Hoạt động</option>
                                <option value="0">Bị khóa</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer border-0">
                    <button type="submit" class="btn btn-warning-custom w-100 py-2">XÁC NHẬN LƯU</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const modalObj = new bootstrap.Modal(document.getElementById('modalNhanVien'));
    function showAddModal() {
        document.getElementById('mMa').value = "";
        document.getElementById('mTen').value = "";
        document.getElementById('mUser').value = "";
        document.getElementById('mPass').value = "";
        document.getElementById('mEmail').value = "";
        modalObj.show();
    }
    function showEditModal(ma, ten, user, pass, email, vaitro, trangthai) {
        document.getElementById('mMa').value = ma;
        document.getElementById('mTen').value = ten;
        document.getElementById('mUser').value = user;
        document.getElementById('mPass').value = pass;
        document.getElementById('mEmail').value = email;
        document.getElementById('mVaiTro').value = vaitro;
        document.getElementById('mTrangThai').value = trangthai;
        modalObj.show();
    }
    function confirmDelete(ma, ten) {
        if(confirm("Xác nhận xóa nhân viên: " + ten + "?")) {
            const f = document.createElement('form');
            f.method='POST'; f.action='quanlynhanvien';
            f.innerHTML = `<input type="hidden" name="action" value="delete"><input type="hidden" name="maNguoiDung" value="${ma}">`;
            document.body.appendChild(f); f.submit();
        }
    }
</script>
</body>
</html>